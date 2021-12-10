package `in`.cbslgroup.ucobank.ui.fragment.account


import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import `in`.cbslgroup.ucobank.ui.activity.MainActivity
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AccountViewModel
import android.app.Dialog
import android.app.ProgressDialog
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
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentLoginBinding
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.roysoft.documentmanagementsystem.network.Resource


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    lateinit var accountViewModel: AccountViewModel
    val sessionManager: SessionManager by lazy { SessionManager(requireActivity()) }
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //init bindings and viewmodel
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        //init viewmodels
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListerners()
    }


    private fun initListerners() {
        binding.tvLoginRegister.setOnClickListener {
            val directions = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(directions)
        }

        binding.tvLoginForgotPwd.setOnClickListener {
            val directions = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(directions)
        }

        binding.btnLoginUserlogin.setOnClickListener {
            if (checkValidation()) {
                val username = binding.tieLoginUsername.text.toString()
                val password = binding.tieLoginPwd.text.toString()
                accountViewModel.login(username, password).observe(requireActivity()) {
                    when (it.status) {
                        Resource.Status.ERROR -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            it.message?.let { it1 -> requireContext().showShortToast(it1) }
                        }
                        Resource.Status.LOADING -> {
                            processDialog.show()
                        }
                        Resource.Status.SUCCESS -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            if (it.data != null) {
                                val info = it.data
                                if(info.isError==false){
                                    //setting the details in session manager
                                    sessionManager.createLoginSession(
                                        info.customerId.toString(),
                                        info.isFirstLogin!!,
                                        info.mobileNo!!
                                    )
                                    // if isFirstLog==0 move to  Change_password_Fragment and if isFirstLog==1 move to MainActivity
                                    if(info.isFirstLogin==false){
                                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                                        requireActivity().finish()
                                    }else{
                                        //navigation to Change Password fragment
                                        val directionsChangePasswordFragment =LoginFragmentDirections.actionLoginFragmentToChangePasswordFragment(username)
                                        findNavController().navigate(directionsChangePasswordFragment)
                                    }
                                }else{
                                    info.Message?.let { it1 -> requireContext().showShortToast(it1) }
                                }
                            }else{
                                requireContext().showShortToast(Constants.SOMETHINGWRONG)
                            }
                        }
                    }
                }
            } else {
                requireContext().showShortToast("Fill All Fields")
            }
        }
    }

    fun checkValidation(): Boolean {
        var isAllFieldFilled = true
        binding.tilLoginUsername.error = null
        binding.tilLoginUsername.isErrorEnabled = false
        binding.tilLoginPwd.error = null
        binding.tilLoginPwd.isErrorEnabled = false
        if (binding.tieLoginUsername.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tilLoginUsername.error = "Please Enter Username"
        }
        if (binding.tieLoginPwd.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tilLoginPwd.error = "Please Enter Password"
        }
        return isAllFieldFilled
    }


}