package `in`.cbslgroup.ucobank.ui.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ucobank_module.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LogoutDialogFragment : BottomSheetDialogFragment(){

    lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {

        fun onYesClicked()
        fun onNoClicked()

    }


    companion object {

        fun newInstance(): LogoutDialogFragment {

            return LogoutDialogFragment()
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_logout_dialog, container, false)

        v.findViewById<Button>(R.id.btn_logout_yes).setOnClickListener {

           onItemClickListener.onYesClicked()

        }

        v.findViewById<Button>(R.id.btn_logout_no).setOnClickListener {

            onItemClickListener.onNoClicked()
        }

        // Inflate the layout for this fragment
        return v

    }

}