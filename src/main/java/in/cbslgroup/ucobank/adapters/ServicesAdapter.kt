package `in`.cbslgroup.ucobank.adapters

import `in`.cbslgroup.ucobank.model.response.appointment.ServiceListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R

class ServicesAdapter(var servicesList: List<ServiceListItem>) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    lateinit var onServiceItemClickListener: OnServiceItemClickListener

    interface OnServiceItemClickListener {

        fun getServiceDetails(serviceName: String, serviceId: String)

    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val tvBranchName: TextView


        init {

            tvBranchName = v.findViewById(R.id.tv_service_name)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_item_layout, parent, false)

        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = servicesList.get(position)
        holder.tvBranchName.text = item.serviceName


        holder.itemView.setOnClickListener {

            onServiceItemClickListener.getServiceDetails(item.serviceName.toString(),item.serviceId.toString())

        }

    }

    override fun getItemCount(): Int {

        return servicesList.size
    }


}