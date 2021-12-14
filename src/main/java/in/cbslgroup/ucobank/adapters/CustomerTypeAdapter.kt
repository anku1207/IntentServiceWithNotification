package `in`.cbslgroup.ucobank.adapters

import `in`.cbslgroup.ucobank.ui.bottomsheets.CustomerTypeFragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomerTypeAdapter(
    var customerTypeList: List<String>,
    var onTimeSlotClickListner: CustomerTypeFragment.OnItemClickListner,
    var context: CustomerTypeFragment
) :RecyclerView.Adapter<CustomerTypeAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv_customer_type: TextView = v.findViewById(R.id.tv_customer_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.customer_type_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = customerTypeList.get(position)
        holder.tv_customer_type.text = item
        holder.itemView.setOnClickListener {
            onTimeSlotClickListner.onCustomerTypeClicked(item)
            context.dismiss()
        }
    }
    override fun getItemCount(): Int {
        return customerTypeList.size
    }
}