package `in`.cbslgroup.ucobank.ui.bottomsheets


import `in`.cbslgroup.ucobank.adapters.NearestBranchAdapter
import `in`.cbslgroup.ucobank.adapters.TimeSlotAdapter
import `in`.cbslgroup.ucobank.model.TimeSlot
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AppointmentViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roysoft.documentmanagementsystem.network.Resource


class TimeSlotDialogFragment : BottomSheetDialogFragment() {
    var  branchId :Int?=null
    var  selectDate :String?=null
    lateinit var slotRecyclerView: RecyclerView
    var isGetTimeSolt : Boolean  =true

    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}

    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var onTimeSlotClickListner: OnItemClickListner
    interface OnItemClickListner {
        fun onTimeSlotClicked(slot: String)
    }

    companion object {
        const val ARG_PARAM1 :String ="BranchId"
        const val ARG_PARAM2 :String ="SelectDate"

        fun newInstance(): TimeSlotDialogFragment {
            val fragment= TimeSlotDialogFragment()
            return fragment
        }

        fun newInstance(branchId :String , selectDate:String): TimeSlotDialogFragment {
            val fragment= TimeSlotDialogFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, branchId)
            args.putString(ARG_PARAM2, selectDate)
            fragment.arguments=args
            return fragment
        }
    }


    @Nullable
    override fun onCreateView(inflater: LayoutInflater,@Nullable container: ViewGroup?,@Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheet_time_slot, container, false)
        appointmentViewModel = ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)

        arguments?.let {
            isGetTimeSolt=true
            branchId= it.getString(ARG_PARAM1)!!.toInt()
            selectDate=it.getString(ARG_PARAM2)
        }?: run {
            isGetTimeSolt=false
        }
        slotRecyclerView= view.findViewById(R.id.rv_time_slot)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isGetTimeSolt){
            val appointmentDate :String? =Utility.convertDate2String(Utility.StringToDateWithLenient(selectDate!!,"dd-MM-yyyy"),"yyyy-MM-dd")
            appointmentViewModel.getSlotByBranchWise(branchId!!,appointmentDate!!).observe(requireActivity(), {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        Utility.dismissDialog(requireActivity(), processDialog)
                        if (it.data != null && it.data.isError == false) {
                            slotRecyclerView.apply {
                                layoutManager = GridLayoutManager(requireActivity(), 3)
                                adapter = TimeSlotAdapter(it.data.Slot!!).apply {
                                    //initialization interface object and override method
                                    onItemClickListner = object : OnItemClickListner,
                                        TimeSlotAdapter.OnItemClickListner {
                                        override fun onTimeSlotClicked(slot: String) {
                                            onTimeSlotClickListner.onTimeSlotClicked(slot)
                                            dismiss()
                                        }
                                    }
                                }
                            }
                        } else {
                            it.data?.let {
                                it.Message?.let { it1 -> requireContext().showShortToast(it1) }
                            } ?: run {
                                requireContext().showShortToast(Constants.SOMETHINGWRONG)
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        Utility.dismissDialog(requireActivity(), processDialog)
                        it.message?.let { it1 -> requireContext().showShortToast(it1) }
                    }
                    Resource.Status.LOADING -> {
                        processDialog.show()
                    }
                }
            })
        }else{
            slotRecyclerView.apply {
                layoutManager = GridLayoutManager(requireActivity(), 3)
                adapter = TimeSlotAdapter(TimeUtils.dummyList).apply {
                    //initialization interface object and override method
                    onItemClickListner = object : OnItemClickListner,
                        TimeSlotAdapter.OnItemClickListner {
                        override fun onTimeSlotClicked(slot: String) {
                            onTimeSlotClickListner.onTimeSlotClicked(slot)
                            dismiss()
                        }
                    }
                }
            }
        }

    }
}