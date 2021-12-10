package `in`.cbslgroup.ucobank.ui.bottomsheets

import `in`.cbslgroup.ucobank.adapters.CustomerTypeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class CustomerTypeFragment : BottomSheetDialogFragment() {
    lateinit var customerTypeRecyclerView: RecyclerView
    lateinit var onTimeSlotClickListner: OnItemClickListner
    interface OnItemClickListner {
        fun onCustomerTypeClicked(customerType: String)
    }

    companion object {
        fun newInstance(): CustomerTypeFragment {
            val fragment= CustomerTypeFragment()
            return fragment
        }
        var customerTypeListArray: List<String> = Arrays.asList("Normal Citizen","Senior Citizen","Army Personnel")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_customer_type, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerTypeRecyclerView=view.findViewById(R.id.rv_customer_tk)
        customerTypeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
        }
        val customerTypeAdapter=CustomerTypeAdapter(customerTypeListArray,onTimeSlotClickListner,this)
        customerTypeRecyclerView.adapter=customerTypeAdapter
    }
}