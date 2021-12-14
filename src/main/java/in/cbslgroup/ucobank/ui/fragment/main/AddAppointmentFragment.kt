package `in`.cbslgroup.ucobank.ui.fragment.main

import `in`.cbslgroup.ucobank.customdialog.MyDialog
import `in`.cbslgroup.ucobank.ui.bottomsheets.BranchDialogFragment
import `in`.cbslgroup.ucobank.ui.bottomsheets.CustomerTypeFragment
import `in`.cbslgroup.ucobank.ui.bottomsheets.ServicesDialogFragment
import `in`.cbslgroup.ucobank.ui.bottomsheets.TimeSlotDialogFragment
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AppointmentViewModel
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentAddAppointmentBinding
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception
import java.util.*


class AddAppointmentFragment : Fragment() {
    lateinit var binding: FragmentAddAppointmentBinding
    var branchId: String? = null
    var serviceId: String? = null
    var kioskId :String? =null
    lateinit var appointmentViewModel: AppointmentViewModel
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
     }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_add_appointment,container,false)
        appointmentViewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.tieAddAptSelectBank.setOnClickListener {
            //set empty time slot if user click select bank and date
            binding.tieAddAptTimeSlot.text=null
            //set empty error
            setErrorMessageEmpty()


            val bs = BranchDialogFragment.newInstance()
            bs.show(requireActivity().supportFragmentManager, "bottomsheet_branch")
            bs.onBranchItemClickListener = object : BranchDialogFragment.OnBranchItemClickListener {
                override fun getBranchDetails(branchName: String, branchId: String) {
                    binding.tieAddAptSelectBank.setText(branchName)
                    //saving the branch id
                    this@AddAppointmentFragment.branchId = branchId
                    getKioskIdbyBranch(branchId)
                }
            }
        }

        binding.tieAddAptSelectServices.setOnClickListener {
            //set empty error
            setErrorMessageEmpty()

            if(!branchId.isNullOrEmpty()){
                val bs = ServicesDialogFragment.newInstance(branchId!!)
                bs.show(requireActivity().supportFragmentManager, "bottomsheet_services")
                bs.onServiceItemClickListener1 =
                    object : ServicesDialogFragment.OnServiceItemClickListener {
                        override fun getServiceDetails(serviceName: String, serviceId: String) {
                            binding.tieAddAptSelectServices.setText(serviceName)
                            this@AddAppointmentFragment.serviceId=serviceId
                        }
                        override fun isError(error: Boolean) {
                            if (error) {
                                binding.tieAddAptSelectServices.isClickable = false
                                binding.tieAddAptSelectServices.setText("No Services Found")
                            } else {
                                binding.tieAddAptSelectServices.isClickable = true
                               // binding.tieAddAptSelectServices.setText("")
                            }
                        }
                    }
            } else{
                requireContext().showShortToast("No Branch Selected")
            }
        }

        //select customer type
        binding.tieAddAptCustomerType.setOnClickListener {
            //set empty error
            setErrorMessageEmpty()
            val bs = CustomerTypeFragment.newInstance()
            bs.show(requireActivity().supportFragmentManager, "bottomsheet_customer_type")
            bs.onTimeSlotClickListner = object : CustomerTypeFragment.OnItemClickListner {
                override fun onCustomerTypeClicked(customerType: String) {
                    binding.tieAddAptCustomerType.setText(customerType)
                }
            }
        }

        binding.tieAddAptSelectDate.setOnClickListener {
            //set empty time slot if user click select bank and date
            binding.tieAddAptTimeSlot.text=null
            //set empty error
            setErrorMessageEmpty()
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                binding.tieAddAptSelectDate.setText(("%02d".format(dayOfMonth))+ "-" + ("%02d".format((monthOfYear+1))) + "-" + ("%02d".format(year)))
            }, year, month, day)
            //set not set past date in calender
            dpd.datePicker.minDate = c.getTimeInMillis();
            dpd.show()
        }


        binding.tieAddAptTimeSlot.setOnClickListener {
            //set empty error
            setErrorMessageEmpty()
            if(!branchId.isNullOrEmpty() && !binding.tieAddAptSelectDate.text.toString().isNullOrEmpty()){
                val bs = TimeSlotDialogFragment.newInstance(branchId!!,binding.tieAddAptSelectDate.text.toString())
                bs.show(requireActivity().supportFragmentManager, "bottomsheet_time_slot")
                bs.onTimeSlotClickListner = object : TimeSlotDialogFragment.OnItemClickListner {
                    override fun onTimeSlotClicked(slot: String) {
                        binding.tieAddAptTimeSlot.setText(slot)
                    }
                }
            }else{
                requireContext().showShortToast("Branch and Date is required")
            }
        }
        binding.addAppointmentSubmit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val jsonData=checkEmptyFiled()
                if(jsonData!=null){
                    val request = jsonData.toString().toRequestBody("application/json".toMediaTypeOrNull());
                    appointmentViewModel.saveAppointment(request).observe(requireActivity(), {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                Utility.dismissDialog(requireActivity(),processDialog)
                                if (it.data != null && it.data.isError==false) {
                                    MyDialog.responseDialogCbsl(requireContext(),"Success",it.data.Message,false,Utility.getResponseWishStyleForTxnDialog(requireContext(),true)){
                                        //fragment back one step back
                                        findNavController().navigateUp()
                                    }
                                }else{
                                    it.data?.let {
                                        MyDialog.responseDialogCbsl(requireContext(),"Fail",it.Message,false,Utility.getResponseWishStyleForTxnDialog(requireContext(),false)){
                                        }
                                    }?: run {
                                        requireContext().showShortToast(Constants.SOMETHINGWRONG)
                                    }
                                }
                            }
                            Resource.Status.ERROR -> {
                                Utility.dismissDialog(requireActivity(),processDialog)
                                it.message?.let { it1 -> requireContext().showShortToast(it1) }
                            }
                            Resource.Status.LOADING -> {
                                processDialog.show()
                            }
                        }
                    })
                }
            }
        })
        binding.btnAddAppointmentCancel.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun getKioskIdbyBranch(branchId: String) {
        appointmentViewModel.getKioskIdbyBranch(branchId).observe(requireActivity(), {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    if(it.data!=null){
                        kioskId=it.data
                    }
                }
                Resource.Status.ERROR -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    it.message?.let { it1 -> requireContext().showShortToast(it1) }
                }
                Resource.Status.LOADING -> {
                    processDialog.show()
                }
            }
        })
    }

    fun checkEmptyFiled() :JSONObject?{
        var isAllFieldFilled=true
        val jsonData=JSONObject()
        try {
            setErrorMessageEmpty()
            if(binding.tieAddAptSelectBank.text.isNullOrEmpty() || branchId.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilAddAptSelectBank.error = "Please Select Bank"
            }else{
                jsonData.put("BranchId",branchId?.toInt())

                //json request add appointment number
                jsonData.put("AppointmentNumber",branchId+""+Utility.getRandomNumberString())
            }
            if(binding.tieAddAptSelectServices.text.isNullOrEmpty() || serviceId.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilAddAptSelectServices.error = "Please Select Service"
            }else{
                jsonData.put("ServiceId",serviceId?.toInt())
            }
            if(binding.tieAddAptCustomerType.text.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilAddAptSelectCustomerType.error = "Please Select Customer Type"
            }else{
                jsonData.put("CustomerType",binding.tieAddAptCustomerType.text)
            }
            if(binding.tieAddAptSelectDate.text.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilAddAptSelectDate.error = "Please Enter Date"
            }else{
                val appointmentDate :String? =Utility.convertDate2String(Utility.StringToDateWithLenient(binding.tieAddAptSelectDate.text.toString(),"dd-MM-yyyy"),"yyyy-MM-dd")
                jsonData.put("Appointmentdatetime",appointmentDate)
            }
            if(binding.tieAddAptTimeSlot.text.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilAddAptTimeSlot.error = "Please Select Time Slot"
            }else{
                jsonData.put("Slot",binding.tieAddAptTimeSlot.text)
            }
            if(kioskId.isNullOrEmpty()){
                isAllFieldFilled = false
                requireContext().showShortToast(Constants.SOMETHINGWRONG)
            }else{
                jsonData.put("KioskId",kioskId!!.toInt())
            }
            if(SessionManager(requireActivity()).userDetails[SessionManager.KEY_CONSTOMER_ID].isNullOrEmpty()){
                isAllFieldFilled = false
                requireContext().showShortToast(Constants.SOMETHINGWRONG)
            }else{
                jsonData.put("CustomerId",SessionManager(requireActivity()).userDetails[SessionManager.KEY_CONSTOMER_ID]!!.toInt())
            }
            jsonData.put("url", ApiUrl.BANK_BASE_URL +"Appointment/NewAppointment")
        }catch (exp:Exception){
            exp.message?.let { requireContext().showShortToast(it) }
        }
        return if (!isAllFieldFilled) null else jsonData
    }
    fun setErrorMessageEmpty(){
        binding.tieAddAptSelectBank.error = null
        binding.tilAddAptSelectBank.isErrorEnabled = false

        binding.tieAddAptSelectServices.error = null
        binding.tilAddAptSelectServices.isErrorEnabled = false

        binding.tieAddAptCustomerType.error = null
        binding.tilAddAptSelectCustomerType.isErrorEnabled = false

        binding.tieAddAptSelectDate.error = null
        binding.tilAddAptSelectDate.isErrorEnabled = false

        binding.tieAddAptTimeSlot.error = null
        binding.tilAddAptTimeSlot.isErrorEnabled = false
    }

}