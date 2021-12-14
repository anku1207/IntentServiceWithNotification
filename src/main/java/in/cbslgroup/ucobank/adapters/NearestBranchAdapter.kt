package `in`.cbslgroup.ucobank.adapters

import `in`.cbslgroup.ucobank.model.response.appointment.BranchListItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R

class NearestBranchAdapter(var branchList: List<BranchListItem>) :
    RecyclerView.Adapter<NearestBranchAdapter.ViewHolder>() {

    lateinit var onBranchItemClickListener: OnBranchItemClickListener
    interface OnBranchItemClickListener {
        fun getBranchDetails(branchName: String, branchId: String)
    }



    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvBranchName: TextView
        init {
            tvBranchName = v.findViewById(R.id.tv_branch_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.nearest_places_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = branchList.get(position)
        holder.tvBranchName.text = item.branchName
        holder.itemView.setOnClickListener {
            onBranchItemClickListener.getBranchDetails(item.branchName.toString(), item.branchId.toString())
        }
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    fun updateList(list: List<BranchListItem>?) {
        branchList = list!!;
        notifyDataSetChanged()
    }

}