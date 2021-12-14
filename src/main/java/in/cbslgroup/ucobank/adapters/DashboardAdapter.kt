package `in`.cbslgroup.ucobank.adapters

import `in`.cbslgroup.ucobank.model.DashboardItem
import `in`.cbslgroup.ucobank.model.DashboardMenuVO
import `in`.cbslgroup.ucobank.ui.fragment.main.DashboardFragmentDirections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R


class DashboardAdapter(var dashboardList: ArrayList<DashboardItem>,val navController: NavController) :RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCount: TextView
        val tvName: TextView
        val icon: ImageView
        init {
            tvCount = view.findViewById(R.id.tv_dashboard_item_count)
            tvName = view.findViewById(R.id.tv_dashboard_item_heading)
            icon = itemView.findViewById(R.id.iv_dashboard_item_icon)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_count_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = dashboardList.get(position)
        holder.tvCount.setText(list.count)
        holder.tvName.setText(list.name)
        holder.icon.setImageDrawable(list.image)

        when (list.id) {
            DashboardMenuVO.ADD_APPOINTMENT -> {
                holder.tvCount.visibility = View.INVISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            when (list.id) {
                DashboardMenuVO.ADD_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToAddAppointmentFragment()
                    navController.navigate(directions)
                }
                DashboardMenuVO.ALL_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToSearchFragment(list.id.toString(),"All Appointment List")
                    navController.navigate(directions)
                }
                DashboardMenuVO.TODAY_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToSearchFragment(list.id.toString(),"Today Appointment List")
                    navController.navigate(directions)
                }
                DashboardMenuVO.UPCOMING_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToSearchFragment(list.id.toString(),"UpComing Appointment List")
                    navController.navigate(directions)
                }
                DashboardMenuVO.CANCELLED_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToSearchFragment(list.id.toString(),"Cancel Appointment List")
                    navController.navigate(directions)
                }
                DashboardMenuVO.COMPLETED_APPOINTMENT -> {
                    val directions = DashboardFragmentDirections.actionNavDashboardToSearchFragment(list.id.toString(),"Completed Appointment List")
                    navController.navigate(directions)

                }

            }
        }
    }

    override fun getItemCount(): Int {

        return dashboardList.size
    }


}