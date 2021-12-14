package `in`.cbslgroup.ucobank.ui.fragment.account


import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.AccountViewModel
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.roysoft.documentmanagementsystem.network.Resource
import dmax.dialog.SpotsDialog
import com.example.ucobank_module.databinding.FragmentRegisterBinding as FragmentRegisterBinding1


class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding1
    lateinit var accountViewModel: AccountViewModel
    lateinit var processDialog :Dialog
    var isMobileNumberIsDuplicate:Boolean=true

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //init bindings and viewmodel
        binding =DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        //init viewmodels
        accountViewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)
        return binding.root    // Inflate the layout for this fragment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            // manoj shaya 13-04-21
            processDialog =requireActivity().processBarDialog(false,"Connecting")
            initListerners()
            //disable register button
            changeBtnColorAndError(R.color.md_grey_300,true)



            // sign in TextView Click
            binding.tvRegisterAlreadyLoggedIn.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            };




            // set  text change listener on register mobile filed
           binding.tieRegisterMobile.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if(binding.tieRegisterMobile.length() != 10){
                        changeBtnColorAndError(R.color.md_grey_300,true)
                    }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.tieRegisterMobile.length() == 10) {
                    binding.tilRegisterMobile.error = null
                    binding.tilRegisterMobile.isErrorEnabled = false
                    if (Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)!=null) {
                        binding.tilRegisterMobile.error = Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)
                    }else{
                        checkDuplicateNumber(binding.tieRegisterMobile.text.toString()){ result ->
                            //use result
                            if(!result){
                                changeBtnColorAndError(R.color.colorPrimary,false)
                            }
                        }
                    }
                }
            }
        })

        binding.tieRegisterMobile.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {

            } else{
                if (binding.tieRegisterMobile.length() < 10) {
                    if (Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)!=null) {
                        binding.tilRegisterMobile.error = Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)
                    }
                }
            }
        }
    }


    // check enter mobile number api
    fun checkDuplicateNumber(mobileNumber: String, myCallback: (result: Boolean) -> Unit) {
        // checkDuplicateNumber some network work
        accountViewModel.checkDuplicateNumber(mobileNumber).observe(requireActivity(), {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    // handel success response
                    if (it.data != null) {
                        if(it.data.isError == false){
                            myCallback.invoke(false)
                        }else{
                            changeBtnColorAndError(R.color.md_grey_300,true)
                            requireContext().showShortToast(it.data.Message)
                        }
                    }
                }
                Resource.Status.ERROR -> {

                    // handel api server error and show message
                    Utility.dismissDialog(requireActivity(),processDialog)
                    it.message?.let { it1 -> requireContext().showShortToast(it1) }
                    changeBtnColorAndError(R.color.md_grey_300,true)
                }
                Resource.Status.LOADING -> {
                    // handel api server loading
                    processDialog.show()
                }
            }
        })
    }

    private fun changeBtnColorAndError(color :Int , duplicateNumber:Boolean){
        // if duplicateNumber is false  mobile number is not duplicate
        isMobileNumberIsDuplicate=duplicateNumber

        //change registration button background color
        val nextColor = ContextCompat.getColor(requireContext(),color)
        binding.btnRegister.backgroundTintList = ColorStateList.valueOf(nextColor)
        if(!duplicateNumber){
            //focus on Email Edit text
            binding.tieRegisterEmail.requestFocus()

            // Enable registration Button click
            binding.btnRegister.isClickable=true
            binding.btnRegister.isEnabled=true
       }else{

            // disable registration Button click
            binding.btnRegister.isClickable=false
            binding.btnRegister.isEnabled=false

        }
    }

    private fun initListerners() {


        // registration button click
         binding.btnRegister.setOnClickListener {
            if (checkValidation() && !isMobileNumberIsDuplicate  ) {
                val username = binding.tieRegisterUsername.text.toString().trim()
                val email = binding.tieRegisterEmail.text.toString().trim()
                val contact = binding.tieRegisterMobile.text.toString().trim()

                accountViewModel.register(contact, username, email).observe(requireActivity(), {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            if (it.data != null) {
                                if(it.data.isError == false){
                                    val directions = RegisterFragmentDirections.actionRegisterFragmentToOtpFragment(contact,Constants.FRAG_REGISTER)
                                    findNavController().navigate(directions)
                                }else{
                                    requireContext().showShortToast("Something went wrong")
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            it.message?.let { it1 -> requireContext().showShortToast(it1) }
                        }
                        Resource.Status.LOADING -> {
                            processDialog.show()
                        }

                    }
                })
            }
        }


    }

    fun checkValidation(): Boolean {

        var isAllFieldFilled = true

        binding.tilRegisterEmail.error = null
        binding.tilRegisterEmail.isErrorEnabled = false

        binding.tilRegisterMobile.error = null
        binding.tilRegisterMobile.isErrorEnabled = false

        binding.tilRegisterUsername.error = null
        binding.tilRegisterUsername.isErrorEnabled = false


        if (binding.tieRegisterEmail.text.toString().trim().isEmpty()) {
            isAllFieldFilled = false
            binding.tilRegisterEmail.error = "Please Enter Email"
        } else if (Utility.validatePattern(binding.tieRegisterEmail.text.toString().trim(),Constants.EMAIL_VALIDATION)!=null) {
            isAllFieldFilled = false
            binding.tilRegisterEmail.error = Utility.validatePattern(binding.tieRegisterEmail.text.toString().trim(),Constants.EMAIL_VALIDATION)
        }
        // manoj shakya 10-04-2021
        if (binding.tieRegisterMobile.text.toString().trim().isEmpty()) {
            isAllFieldFilled = false
            binding.tilRegisterMobile.error = "Please Enter Mobile"
            // manoj shakya 10-04-2021
        } else if (Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)!=null) {
            isAllFieldFilled = false
            binding.tilRegisterMobile.error = Utility.validatePattern(binding.tieRegisterMobile.text.toString().trim(),Constants.MOBILENO_VALIDATION)
        }
        if (binding.tieRegisterUsername.text.toString().trim().isEmpty()) {

            isAllFieldFilled = false
            binding.tilRegisterUsername.error = "Please Enter Username"
        }
        return isAllFieldFilled

    }


}