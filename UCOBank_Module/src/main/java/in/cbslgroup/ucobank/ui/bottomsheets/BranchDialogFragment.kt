package `in`.cbslgroup.ucobank.ui.bottomsheets

import `in`.cbslgroup.ucobank.adapters.NearestBranchAdapter
import `in`.cbslgroup.ucobank.interfaces.CommonMethods
import `in`.cbslgroup.ucobank.model.response.appointment.BranchListItem
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AppointmentViewModel
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roysoft.documentmanagementsystem.network.Resource
import java.util.*
import kotlin.collections.ArrayList


class BranchDialogFragment : BottomSheetDialogFragment(), CommonMethods {
    lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var rvMain: RecyclerView
    lateinit var btnCancel: Button
    lateinit var nearestBranchAAdapter:  NearestBranchAdapter
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false, "Connecting")}

    val displayedList: ArrayList<BranchListItem> = ArrayList()
    lateinit var simpleBranch :SearchView

    companion object {
        fun newInstance(): BranchDialogFragment {
            return BranchDialogFragment()
        }
    }

    lateinit var onBranchItemClickListener: OnBranchItemClickListener

    interface OnBranchItemClickListener {
        fun getBranchDetails(branchName: String, branchId: String)
    }


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.alert_dialog_nearest_places, container, false)
        //tag?.let { requireContext().showShortToast(it) }
        //init views
        this.init(view)
        // get the views and attach the listener
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simpleBranch=view.findViewById(R.id.simpleBranch)

        simpleBranch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // collapse the view ?
                //menu.findItem(R.id.menu_search).collapseActionView();
                Log.e("queryText", query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // search goes here !!
                if (newText.isEmpty()) {
                    filter(null)
                } else {
                    filter(newText.toString())
                }
                return false
            }
        })


    }

    override fun initViewModelsAndDataBinding(view: View) {
        appointmentViewModel =ViewModelProvider(requireActivity()).get(AppointmentViewModel::class.java)
    }

    override fun initObservers() {
        val lat = Session.getSessionByKey(requireActivity(), Session.CACHE_LOCATION_LAT)!!.toDouble()
        val lon=Session.getSessionByKey(requireActivity(), Session.CACHE_LOCATION_LON)!!.toDouble()
        Log.d("locationPrint", lat.toString() + "==" + lon)
        appointmentViewModel.getBranch(lat, lon).observe(requireActivity(), {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Utility.dismissDialog(requireActivity(), processDialog)
                    if (it.data != null && it.data.isError == false) {
                        if(displayedList.isEmpty()) displayedList.addAll(it.data.branchList!!)
                        nearestBranchAAdapter = NearestBranchAdapter(displayedList)
                        nearestBranchAAdapter.onBranchItemClickListener =
                                object : NearestBranchAdapter.OnBranchItemClickListener {
                                    override fun getBranchDetails(branchName: String, branchId: String) {
                                        onBranchItemClickListener.getBranchDetails(branchName, branchId)
                                        dismiss()
                                    }
                                }
                        rvMain.adapter = nearestBranchAAdapter
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

    }

    override fun initViews(view: View) {
        btnCancel = view.findViewById(R.id.btn_alert_places_cancel)
        rvMain = view.findViewById(R.id.rv_alert_places)
    }

    override fun initRecyclerViews(view: View) {
        rvMain.apply {
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun initListeners() {
      btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getIntentData() {
    }

    fun filter(text: String?) {
        val temp: ArrayList<BranchListItem> = ArrayList()
        if(text.isNullOrEmpty()){
            temp.addAll(displayedList)
        }else{
            for (d in displayedList) {
                if (d.branchName?.toLowerCase(Locale.ROOT)?.contains(text?.toLowerCase(Locale.ROOT).toString()) == true) {
                    temp.add(d)
                }
            }
        }
        //update recyclerview
        nearestBranchAAdapter.updateList(temp)
    }

}