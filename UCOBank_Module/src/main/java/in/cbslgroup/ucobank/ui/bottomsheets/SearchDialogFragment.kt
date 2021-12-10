package `in`.cbslgroup.ucobank.ui.bottomsheets


import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.SearchViewModel
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.BottomsheetFilterSearchLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class SearchDialogFragment : BottomSheetDialogFragment() {
    lateinit var searchViewModel: SearchViewModel
    var branchId: String? = null
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false, "Connecting")}
    lateinit var binding: BottomsheetFilterSearchLayoutBinding

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottomsheet_filter_search_layout,
            container,
            false
        )
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSearchFliterSearch.setOnClickListener {
            searchAppointment(){
                dismiss()
            }
        }

        val etBranch: TextInputEditText = view.findViewById(R.id.tie_search_filter_branch)
        etBranch.setOnClickListener {
            //remove edittext error
            setErrorMessageEmpty()

            val bs = BranchDialogFragment.newInstance()
            bs.show(requireActivity().supportFragmentManager, "bottomsheet_branch")
            bs.onBranchItemClickListener = object : BranchDialogFragment.OnBranchItemClickListener {
                override fun getBranchDetails(branchName: String, branchId: String) {
                    etBranch.setText(branchName)
                    this@SearchDialogFragment.branchId=branchId
                }
            }
        }

        val etTimeSlot: TextInputEditText = view.findViewById(R.id.tie_search_filter_time_slot)
        etTimeSlot.setOnClickListener {
            //remove edittext error
            setErrorMessageEmpty()

            val bs = TimeSlotDialogFragment.newInstance()
            bs.show(requireActivity().supportFragmentManager, "bottomsheet_time_slot")
            bs.onTimeSlotClickListner = object : TimeSlotDialogFragment.OnItemClickListner {
                override fun onTimeSlotClicked(slot: String) {
                    etTimeSlot.setText(slot)
                }
            }
        }

        val etTime: TextInputEditText = view.findViewById(R.id.tie_search_filter_date)
        etTime.setOnClickListener {
            //remove edittext error
            setErrorMessageEmpty()

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                etTime.setText("" + dayOfMonth + "-" + (month + 1) + "-" + year)
            }, year, month, day)
            dpd.show()
        }
    }





    private fun searchAppointment(function: () -> Unit) {
        if(checkEmptyFiled()){
            dismiss()
            val clickdate : String? =Utility.convertDate2String(
                Utility.StringToDateWithLenient(
                    binding.tieSearchFilterDate.text.toString(),
                    "dd-MM-yyyy"
                ), "yyyy-MM-dd"
            )
            val timeSlot  = binding.tieSearchFilterTimeSlot.text.toString()/*.split(":")*/



            val requestData :String =SessionManager(requireActivity()).userDetails[SessionManager.KEY_CONSTOMER_ID] + "|" +  branchId + "|" + clickdate + "|" + timeSlot
            Log.w("request", requestData.toString())
            val directions = SearchDialogFragmentDirections.actionSearchDialogFragmentToSearchFragment(
                requestData,
                "filter"
            )
            findNavController().navigate(directions)
        }
    }
    fun checkEmptyFiled() : Boolean{
        var isAllFieldFilled=true
        try {
            setErrorMessageEmpty()
            if(binding.tieSearchFilterBranch.text.isNullOrEmpty() || branchId.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilSearchFilterBranch.error = "Please Select Bank"
            }
            if(binding.tieSearchFilterDate.text.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilSearchFilterDate.error = "Please Select Service"
            }
            if(binding.tieSearchFilterTimeSlot.text.isNullOrEmpty()){
                isAllFieldFilled = false
                binding.tilSearchFilterTimeSlot.error = "Please Select Customer Type"
            }

        }catch (exp: Exception){
            exp.message?.let { requireContext().showShortToast(it) }
        }
        return isAllFieldFilled
    }
    fun setErrorMessageEmpty(){
        binding.tieSearchFilterBranch.error = null
        binding.tilSearchFilterBranch.isErrorEnabled = false

        binding.tieSearchFilterDate.error = null
        binding.tilSearchFilterDate.isErrorEnabled = false

        binding.tieSearchFilterTimeSlot.error = null
        binding.tilSearchFilterTimeSlot.isErrorEnabled = false

    }

}