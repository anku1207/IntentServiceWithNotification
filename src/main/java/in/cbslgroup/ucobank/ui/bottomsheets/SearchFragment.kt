package `in`.cbslgroup.ucobank.ui.bottomsheets

import `in`.cbslgroup.ucobank.adapters.AppointmentAdapter
import `in`.cbslgroup.ucobank.model.DashboardMenuVO
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItem
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.DashboardViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentSearchBinding
import com.roysoft.documentmanagementsystem.network.Resource
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var searchBinding: FragmentSearchBinding
    lateinit var aptAdapter: AppointmentAdapter
    lateinit var dashboardViewModel: DashboardViewModel
    var aptList: ArrayList<AppointmentListItem> =ArrayList()
    val sessionManager: SessionManager by lazy { SessionManager(requireActivity()) }
    var searchType :String = ""
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false, "Connecting")}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        // super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchFragmentArgs = navArgs<SearchFragmentArgs>()
        searchType = searchFragmentArgs.value.aptType

        initObserversAndViewmodels(view)
        initRecyclerViews()

        val formTypejson=HashMap<Int, String>()
        formTypejson.put(DashboardMenuVO.ADD_APPOINTMENT, "")
        formTypejson.put(DashboardMenuVO.ALL_APPOINTMENT, "getAllAppointmentbyId")
        formTypejson.put(DashboardMenuVO.TODAY_APPOINTMENT, "getToDayAppointmentbyId")
        formTypejson.put(DashboardMenuVO.UPCOMING_APPOINTMENT, "getUpcomingAppointmentbyId")
        formTypejson.put(DashboardMenuVO.CANCELLED_APPOINTMENT, "getCancelledAppointmentbyId")
        formTypejson.put(DashboardMenuVO.COMPLETED_APPOINTMENT, "getCompletedAppointmentbyId")
        //populate the list

        val sizeOfSearchType = searchType.split("|");
        if(sizeOfSearchType.size>1){
            filterList(sizeOfSearchType[0].toInt(),sizeOfSearchType[1].toInt(),sizeOfSearchType[2],sizeOfSearchType[3])
        }else{
            searchList(searchType, formTypejson.get(searchType.toInt())!!)
        }
    }

   private fun searchList(menuTypeId: String?, aptType: String) {
       dashboardViewModel.getSearchedAppointments(sessionManager.getCustomerId()!!, aptType)
           .observe(requireActivity(), {
               when (it.status) {
                   Resource.Status.SUCCESS -> {
                       Utility.dismissDialog(requireActivity(), processDialog)
                       if (it.data != null && it.data.isError == false) {
                           if (!aptList.isNullOrEmpty()) {
                               aptList.clear()
                           }
                           aptList.addAll(it.data.appointmentList!!)
                           aptAdapter.notifyDataSetChanged()
                           //show recycler view
                           searchBinding.rvSearchMain.visibility = View.VISIBLE
                           searchBinding.rvListNotFound.visibility = View.GONE
                       } else {
                           it.data?.let {
                               //hide recycler view
                               searchBinding.rvSearchMain.visibility = View.GONE
                               searchBinding.rvListNotFound.visibility = View.VISIBLE
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

    private fun filterList(customerId:Int , branchId:Int , selectDate :String , slot :String) {
        dashboardViewModel.filterAppointment(customerId,branchId,selectDate,slot)
            .observe(requireActivity(), {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        Utility.dismissDialog(requireActivity(), processDialog)
                        if (it.data != null && it.data.isError == false) {
                            if (!aptList.isNullOrEmpty()) {
                                aptList.clear()
                            }
                            aptList.addAll(it.data.appointmentList!!)
                            aptAdapter.notifyDataSetChanged()
                            //show recycler view
                            searchBinding.rvSearchMain.visibility = View.VISIBLE
                            searchBinding.rvListNotFound.visibility = View.GONE
                        } else {
                            it.data?.let {
                                //hide recycler view
                                searchBinding.rvSearchMain.visibility = View.GONE
                                searchBinding.rvListNotFound.visibility = View.VISIBLE
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



    fun initObserversAndViewmodels(view: View) {
        //init viewbinding
        searchBinding = FragmentSearchBinding.bind(view)
        //initviewmodels
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    fun initRecyclerViews() {
        aptAdapter = AppointmentAdapter(
            requireActivity(),
            aptList,
            findNavController(),
            "list"
        )
        searchBinding.rvSearchMain.apply {
            layoutManager = LinearLayoutManager(requireContext())
            searchBinding.rvSearchMain.adapter = aptAdapter
      }

    }

}