package `in`.cbslgroup.ucobank.ui.fragment.account


import `in`.cbslgroup.ucobank.ui.activity.MainActivity
import `in`.cbslgroup.ucobank.util.SessionManager
import `in`.cbslgroup.ucobank.util.Utility
import `in`.cbslgroup.ucobank.util.processBarDialog
import `in`.cbslgroup.ucobank.util.showShortToast
import `in`.cbslgroup.ucobank.viewmodels.AccountViewModel
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentChangePasswordBinding
import com.roysoft.documentmanagementsystem.network.Resource


class ChangePasswordFragment : Fragment() {

    lateinit var binding: FragmentChangePasswordBinding
    lateinit var accountViewModel: AccountViewModel
    val sessionManager: SessionManager by lazy { SessionManager(requireContext()) }
    lateinit var mobileNo :String

    lateinit var processDialog : Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        //check if the user is already chnaged his password in lifetime
        //Direct to the main dashboard page if he or she is already changed his password
        if(sessionManager.isLoggedFirstTime == true){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
        //init bindings and viewmodel
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false)
        //init viewmodels
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        val changePasswordFragmentArgs: ChangePasswordFragmentArgs by navArgs()
        mobileNo = changePasswordFragmentArgs.mobileNo
        Log.e("change", mobileNo)
        return binding.root    // Inflate the layout for this fragment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processDialog =requireActivity().processBarDialog(false,"Connecting")
        initListerners()
    }


    private fun initListerners() {

        binding.btnConfirmPwd.setOnClickListener {
            if (checkValidation()) {
                val newPwd = binding.tieChangePwdNewPwd.text.toString()
                val confirmPwd = binding.tieChangePwdConfirmPwd.text.toString()
                accountViewModel.changePassword(mobileNo,newPwd,confirmPwd).observe(requireActivity(), {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            if (it.data != null) {
                                startActivity(Intent(requireActivity(), MainActivity::class.java))
                                requireActivity().finish()
                              }
                        }
                        Resource.Status.ERROR -> {
                            it.message?.let { it1 -> requireContext().showShortToast(it1) }
                            Utility.dismissDialog(requireActivity(),processDialog)
                        }
                        Resource.Status.LOADING -> {
                            processDialog.show()
                        }
                    }
                })
            } else {
                requireContext().showShortToast("Fill All Fields")
            }
        }
    }

    private fun checkValidation(): Boolean {
        var isAllFieldFilled = true
        binding.tilChangePwdNewPwd.error = null
        binding.tilChangePwdNewPwd.isErrorEnabled = false

        binding.tilChangePwdConfirmPwd.error = null
        binding.tilChangePwdConfirmPwd.isErrorEnabled = false

        if (binding.tieChangePwdNewPwd.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tilChangePwdNewPwd.error = "Please New Password"
        }
        if (binding.tieChangePwdConfirmPwd.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tilChangePwdConfirmPwd.error = "Please Confirm Password"
        }else if(!binding.tieChangePwdNewPwd.text.toString().equals(binding.tieChangePwdConfirmPwd.text.toString())){
            isAllFieldFilled = false
            Toast.makeText(requireActivity(),"New password and confirm password doesn't match",Toast.LENGTH_LONG).show()
        }
        return isAllFieldFilled
    }
}