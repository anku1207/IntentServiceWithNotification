package `in`.cbslgroup.ucobank.ui.fragment.main

import `in`.cbslgroup.ucobank.adapters.AppointmentAdapter
import `in`.cbslgroup.ucobank.adapters.DashboardAdapter
import `in`.cbslgroup.ucobank.interfaces.CommonMethods
import `in`.cbslgroup.ucobank.model.DashboardItem
import `in`.cbslgroup.ucobank.model.DashboardMenuVO
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItem
import `in`.cbslgroup.ucobank.util.SessionManager
import `in`.cbslgroup.ucobank.viewmodels.DashboardViewModel
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentDashboardBinding
import com.google.android.material.navigation.NavigationView
import com.roysoft.documentmanagementsystem.network.Resource


class DashboardFragment : Fragment(R.layout.fragment_dashboard),CommonMethods {

    lateinit var dashboardBinding: FragmentDashboardBinding
    lateinit var dashboardAdapter: DashboardAdapter
    lateinit var aptAdapter: AppointmentAdapter
    var aptList: ArrayList<AppointmentListItem> =ArrayList()

    var dashboardList: ArrayList<DashboardItem> = ArrayList()

    lateinit var dashboardViewModel: DashboardViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init all common methods
        this.init(view)

        /*activity?.let {
            val navView: NavigationView = it.findViewById(R.id.nav_view)
            val  side_menu_name : TextView = navView.getHeaderView(0).findViewById(R.id.side_menu_name)
            val side_menu_email :TextView =navView.getHeaderView(0).findViewById(R.id.side_menu_email)

            side_menu_name.text="testing name"
            side_menu_email.text="manojshakya1207@gmail.com"
        }*/




    }

    override fun initViewModelsAndDataBinding(view: View) {
        //init view bindings
        val binding = FragmentDashboardBinding.bind(view)
        dashboardBinding = binding //h
        //init viewmodels
        // historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun initObservers() {
        dashboardViewModel.getDashboardData(SessionManager(requireActivity()).userDetails[SessionManager.KEY_CONSTOMER_ID]!!)
            .observe(this, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.data != null && it.data.isError == false) {
                            dashboardList.clear()
                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.ADD_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_baseline_add_circle_24
                                    )!!,
                                    "Add Appointment",
                                    "0"
                                )
                            )
                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.ALL_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.all_appointment
                                    )!!,
                                    "All Appointments",
                                    it.data.AllAppointment.toString()
                                )
                            )
                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.TODAY_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.today_appointment
                                    )!!,
                                    "Today Appointment",
                                    it.data.TodayAppointment.toString()
                                )
                            )


                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.UPCOMING_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_baseline_compare_arrows_24
                                    )!!,
                                    "Upcoming Appointments",
                                    it.data.upcomingAppointment.toString()
                                )
                            )
                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.CANCELLED_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_baseline_clear_24
                                    )!!,
                                    "Cancelled Appointments",
                                    it.data.cancelledAppointment.toString()
                                )
                            )
                            dashboardList.add(
                                DashboardItem(
                                    DashboardMenuVO.COMPLETED_APPOINTMENT,
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_baseline_check_24
                                    )!!,
                                    "Completed Appointments",
                                    it.data.CompletedAppointment.toString()
                                )
                            )
                            dashboardAdapter.notifyDataSetChanged()
                            dashboardBinding.pbDashboardMain.visibility = View.GONE
                            dashboardBinding.llDashboardMain.visibility = View.VISIBLE

                            if (!aptList.isNullOrEmpty()) {
                                aptList.clear()
                            }
                            if (it.data.appointmentList!!.size > 0) {
                                dashboardBinding.rvDashboardUpcomingApt.visibility = View.VISIBLE
                                dashboardBinding.tvDashboardNoAptFound.visibility = View.GONE
                                aptList.addAll(it.data.appointmentList!!)
                                aptAdapter.notifyDataSetChanged()
                            } else {
                                dashboardBinding.rvDashboardUpcomingApt.visibility = View.GONE
                                dashboardBinding.tvDashboardNoAptFound.visibility = View.VISIBLE
                            }


                            //side menu show username and email id
                            activity?.run {
                                val navView: NavigationView = this.findViewById(R.id.nav_view)
                                val  side_menu_name : TextView = navView.getHeaderView(0).findViewById(R.id.side_menu_name)
                                val side_menu_email :TextView =navView.getHeaderView(0).findViewById(R.id.side_menu_email)
                                side_menu_name.text=it.data.Name
                                side_menu_email.text=it.data.Email
                            }
                        } else {
                            dashboardBinding.rvDashboardUpcomingApt.visibility = View.GONE
                            dashboardBinding.tvDashboardNoAptFound.visibility = View.VISIBLE
                        }
                    }

                    Resource.Status.ERROR -> {
                        dashboardBinding.pbDashboardMain.visibility = View.GONE
                        dashboardBinding.llDashboardMain.visibility = View.VISIBLE
                    }
                    Resource.Status.LOADING -> {
                        dashboardBinding.pbDashboardMain.visibility = View.VISIBLE
                        dashboardBinding.llDashboardMain.visibility = View.GONE
                    }
                }
            })
    }

    override fun initViews(view: View) {


    }

    override fun initRecyclerViews(view: View) {

        //dashoboard adapter
        dashboardAdapter = DashboardAdapter(dashboardList, findNavController())
        dashboardBinding.rvDashboardCount.apply {
            layoutManager =GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = dashboardAdapter
        }
        //appointment adapter
        aptAdapter=AppointmentAdapter(requireContext(), aptList, findNavController(), "dash")
        dashboardBinding.rvDashboardUpcomingApt.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = aptAdapter
        }
    }

    override fun initListeners() {
    }

    override fun getIntentData() {
    }
}