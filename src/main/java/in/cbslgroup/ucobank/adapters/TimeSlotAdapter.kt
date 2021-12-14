package `in`.cbslgroup.ucobank.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R

class TimeSlotAdapter(var timeslotList: List<String>) :
    RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {


    lateinit var onItemClickListner: OnItemClickListner

    interface OnItemClickListner {
        fun onTimeSlotClicked(slot: String)
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTimeSlot: TextView
        init {
            tvTimeSlot = v.findViewById(R.id.tv_time_slot)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_slot_item_layout, parent, false)

        return ViewHolder(v)


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = timeslotList.get(position)
        holder.tvTimeSlot.text = item

        holder.itemView.setOnClickListener {

            onItemClickListner.onTimeSlotClicked(item)

        }

    }

    override fun getItemCount(): Int {

        return timeslotList.size
    }


}