package `in`.cbslgroup.ucobank.adapters

import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItem
import `in`.cbslgroup.ucobank.ui.bottomsheets.SearchFragmentDirections
import `in`.cbslgroup.ucobank.ui.fragment.main.DashboardFragmentDirections
import `in`.cbslgroup.ucobank.util.Utility
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R

class AppointmentAdapter(
    var context : Context,
    var aptList: List<AppointmentListItem>,
    val navController: NavController,
    val fragName: String
) :RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvHeading: TextView
        val tvStatus: TextView
        val tvDate: TextView
        val tvIcon: TextView

        init {

            tvHeading = itemView.findViewById(R.id.tv_apt_name)
            tvStatus = itemView.findViewById(R.id.tv_apt_status)
            tvDate = itemView.findViewById(R.id.tv_apt_date)
            tvIcon = itemView.findViewById(R.id.tv_apt_letter)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.apt_item_layout, parent, false)

        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = aptList.get(position)
        holder.tvDate.text = item.appointmentdatetime
        holder.tvHeading.text = item.branchName
        holder.tvStatus.text = item.status
        holder.tvIcon.text = item.branchName?.substring(0, 1)
        when(item.status){
            "Cancelled"->{
                holder.tvStatus.setBackgroundColor(Utility.getColorWrapper(context,R.color.md_red_700))
            }
            "Attended"->{
                holder.tvStatus.setBackgroundColor(Utility.getColorWrapper(context,R.color.md_green_700))
            }
            "InActive"->{
                holder.tvStatus.setBackgroundColor(Utility.getColorWrapper(context,R.color.md_yellow_700))
            }
        }

        holder.itemView.setOnClickListener {
            if (fragName.equals("dash")) {
                val directions =DashboardFragmentDirections.actionNavDashboardToAppointmentDescribeFragment(item.appointmentNumber.toString())
                navController.navigate(directions)
            } else if (fragName.equals("list")) {
                val directions =SearchFragmentDirections.actionSearchFragmentToAppointmentDescribeFragment(item.appointmentNumber.toString())
                navController.navigate(directions)
            }
       }
    }

    override fun getItemCount(): Int {
        return aptList.size
    }
}