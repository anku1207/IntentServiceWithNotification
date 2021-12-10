
package `in`.cbslgroup.ucobank.ui.bottomsheets

import `in`.cbslgroup.ucobank.adapters.ServicesAdapter
import `in`.cbslgroup.ucobank.interfaces.CommonMethods
import `in`.cbslgroup.ucobank.util.Constants
import `in`.cbslgroup.ucobank.util.Utility
import `in`.cbslgroup.ucobank.util.processBarDialog
import `in`.cbslgroup.ucobank.util.showShortToast
import `in`.cbslgroup.ucobank.viewmodels.AppointmentViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roysoft.documentmanagementsystem.network.Resource


class ServicesDialogFragment(var branchId: String) : BottomSheetDialogFragment(), CommonMethods {

    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var rvMain: RecyclerView
    lateinit var btnCancel: Button
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}

    companion object {
        fun newInstance(branchId: String): ServicesDialogFragment {
            return ServicesDialogFragment(branchId)
        }
    }

    lateinit var onServiceItemClickListener1: OnServiceItemClickListener
    interface OnServiceItemClickListener {
        fun getServiceDetails(serviceName: String, serviceId: String)
        fun isError(error: Boolean)
    }


    @Nullable
    override fun onCreateView(inflater: LayoutInflater,@Nullable container: ViewGroup?,@Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheet_services, container, false)
        //init views
        this.init(view)
        // get the views and attach the listener
        return view
    }

    override fun initViewModelsAndDataBinding(view: View) {
        appointmentViewModel =ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
    }

    override fun initObservers() {

        appointmentViewModel.getServices(branchId).observe(requireActivity(), {

            when (it.status) {

                Resource.Status.SUCCESS -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    if (it.data != null && it.data.isError == false) {
                            val adapter = ServicesAdapter(it.data.serviceList!!)
                            adapter.onServiceItemClickListener =
                                object : ServicesAdapter.OnServiceItemClickListener {
                                    override fun getServiceDetails(serviceName: String,serviceId: String,) {
                                        onServiceItemClickListener1.getServiceDetails(serviceName,serviceId)
                                        onServiceItemClickListener1.isError(false)
                                        dismiss()
                                    }
                                }
                            rvMain.adapter = adapter
                    }else {
                        it.data?.let {
                            it.message?.let { it1 -> requireContext().showShortToast(it1) }
                        }?: run {
                            requireContext().showShortToast(Constants.SOMETHINGWRONG)
                        }
                        dismiss()
                    }
                }
                Resource.Status.ERROR -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    onServiceItemClickListener1.isError(true)
                    it.message?.let { it1 -> requireContext().showShortToast(it1) }
                    dismiss()
                }
                Resource.Status.LOADING -> {
                    processDialog.show()
                }
            }
        })
    }

    override fun initViews(view: View) {
        rvMain = view.findViewById(R.id.rv_services)
    }

    override fun initRecyclerViews(view: View) {
        rvMain.apply {
            layoutManager = LinearLayoutManager(requireActivity())
        }

    }

    override fun initListeners() {

    }

    override fun getIntentData() {

    }


}