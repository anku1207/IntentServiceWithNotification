
package `in`.cbslgroup.ucobank.ui.fragment.account

import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AccountViewModel
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentOtpBinding
import com.roysoft.documentmanagementsystem.network.Resource


class OtpFragment : Fragment() {

    lateinit var binding: FragmentOtpBinding
    lateinit var accountViewModel: AccountViewModel

    lateinit var mobileNo: String
    lateinit var module: String

    lateinit var processDialog : Dialog
    lateinit var timer: CountDownTimer

    // val progress: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //init bindings and viewmodel
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp, container, false)
        //init viewmodels
        accountViewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)
        //getting the passed value
        //safe args
        val otpFragmentArgs: OtpFragmentArgs by navArgs()
        mobileNo = otpFragmentArgs.mobileNo
        //check from which page the otp function will be called for e.g regster login or forgot password
        module = otpFragmentArgs.module
        Log.e("OtpFragment_mobile_no", mobileNo)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processDialog =requireActivity().processBarDialog(false,"Connecting")
        initListerners()
        initTimer()
    }

    override fun onStop() {
        super.onStop()
        requireContext().showShortToast("stop fragment")
        timer.cancel()
    }

    private fun initTimer() {
       timer= object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvOtpTimer.setText("Seconds remaining: " + Utility.getTimeStampToGetTime(millisUntilFinished/1000))
                //here you can have your logic to set text to edittext
            }
            override fun onFinish() {
                binding.tvOtpTimer.setText("Click to resend OTP")
            }
        }
        timer.start()
    }

    private fun initListerners() {
        binding.tvOtpTimer.setOnClickListener {
            if (binding.tvOtpTimer.text.toString().equals("Click to resend OTP")) {
                accountViewModel.resendOTP(mobileNo).observe(requireActivity(), {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                Utility.dismissDialog(requireActivity(),processDialog)
                                initTimer()
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
            }
        }

        binding.btnOtpNext.setOnClickListener {
            if (checkValidation()) {
               val otp = binding.tieOtpVerificationCode.text.toString()

                when(module){
                    Constants.FRAG_REGISTER,Constants.FRAG_LOGIN ->{
                        accountViewModel.verifyOtp(mobileNo, otp).observe(requireActivity(), {
                            when (it.status) {
                                Resource.Status.SUCCESS -> {
                                    Utility.dismissDialog(requireActivity(),processDialog)
                                    if (it.data != null) {
                                        //setting the details in session manager
                                        val info = it.data
                                        //navigation to otp fragment
                                        val directions = OtpFragmentDirections.actionOtpFragmentToChangePasswordFragment(mobileNo)
                                        findNavController().navigate(directions)
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
                    }

                    Constants.FRAG_FORGOT_PASSWORD ->{
                        accountViewModel.forgetPasswordOTPVerification(mobileNo, otp).observe(requireActivity(), {
                            when (it.status) {
                                Resource.Status.SUCCESS -> {
                                    Utility.dismissDialog(requireActivity(),processDialog)
                                    if (it.data != null) {
                                        //setting the details in session manager
                                        //navigation to otp fragment
                                        val directions = OtpFragmentDirections.actionOtpFragmentToChangePasswordFragment(mobileNo)
                                        findNavController().navigate(directions)
                                    }
                                }
                                Resource.Status.ERROR -> {
                                    it.message?.let { it1 -> requireActivity().showShortToast(it1) }
                                    Utility.dismissDialog(requireActivity(),processDialog)
                                }
                                Resource.Status.LOADING -> {
                                    processDialog.show()
                                }
                            }
                        })
                    }
                }
            }else {
                requireContext().showShortToast("Fill All Fields")
            }
        }
    }

    private fun checkValidation(): Boolean {
        var isAllFieldFilled = true
        binding.tilOtpVerificationCode.error = null

        if (binding.tieOtpVerificationCode.text.toString().isEmpty()) {
            isAllFieldFilled = false
            binding.tieOtpVerificationCode.error = "Please Enter Verification code"
        }

        return isAllFieldFilled

    }


}