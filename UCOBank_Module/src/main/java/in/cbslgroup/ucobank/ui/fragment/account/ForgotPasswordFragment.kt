package `in`.cbslgroup.ucobank.ui.fragment.account


import `in`.cbslgroup.ucobank.util.Constants
import `in`.cbslgroup.ucobank.util.Utility
import `in`.cbslgroup.ucobank.util.processBarDialog
import `in`.cbslgroup.ucobank.util.showShortToast
import `in`.cbslgroup.ucobank.viewmodels.AccountViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentForgotPasswordBinding
import com.roysoft.documentmanagementsystem.network.Resource

class ForgotPasswordFragment : Fragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    lateinit var accountViewModel: AccountViewModel
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_forgot_password,container,false)
        //init viewmodels
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    fun initListeners() {

        binding.btnForgotNext.setOnClickListener {

            if (checkValidation()) {
                val mobileNo = binding.tieForgotMobileNo.text.toString()
                accountViewModel.forgotPassword(mobileNo).observe(requireActivity(), {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            if (it.data != null) {
                                if (it.data.isError == false) {
                                    //navigation to otp fragment
                                    val directions =ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToOtpFragment(mobileNo, Constants.FRAG_FORGOT_PASSWORD)
                                    findNavController().navigate(directions)
                                } else {
                                    it.data.Message?.let { it1 ->
                                        requireContext().showShortToast(it1)
                                    }
                                }
                            } else {
                                requireContext().showShortToast("Something went wrong")
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
                Toast.makeText(requireActivity(), "Fill All fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkValidation(): Boolean {
        var isAllFieldFilled = true
        binding.tilForgotMobileNo.error = null
        binding.tilForgotMobileNo.isErrorEnabled = false
        if (binding.tieForgotMobileNo.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tilForgotMobileNo.error = "Please Enter Mobile No."
        }
        return isAllFieldFilled
    }
}