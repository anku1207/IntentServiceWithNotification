package `in`.cbslgroup.ucobank.ui.fragment.main

import `in`.cbslgroup.ucobank.customdialog.MyDialog
import `in`.cbslgroup.ucobank.util.*
import `in`.cbslgroup.ucobank.viewmodels.ProfileViewModel
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import com.example.ucobank_module.databinding.FragmentProfileBinding
import com.roysoft.documentmanagementsystem.network.Resource

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile_Fragment : Fragment(),View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentProfileBinding
    lateinit var profileViewModel: ProfileViewModel
    val processDialog : Dialog by lazy { requireActivity().processBarDialog(false,"Connecting")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        // super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_,container,false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnProfileCancel.setOnClickListener(this)
        binding.btnUpdateProfile.setOnClickListener(this)
        getUserDetailsById()
    }

    private fun getUserDetailsById() {
        profileViewModel.getUserDetails(SessionManager(requireActivity()).userDetails[SessionManager.KEY_CONSTOMER_ID]!!.toInt()).observe(requireActivity(), {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Utility.dismissDialog(requireActivity(),processDialog)
                    if (it.data != null && it.data.isError==false) {
                        binding.tieMobileNumber.setText(it.data.MobileNo)
                        binding.tieUserName.setText(it.data.Name)
                        binding.tieEmailId.setText(it.data.Email)
                        binding.tieChangePassword.setText(it.data.Password)
                    }else{
                        it.data?.let {
                            MyDialog.responseDialogCbsl(requireContext(),"Fail",it.Message,false,
                                Utility.getResponseWishStyleForTxnDialog(requireContext(),false)){
                            }
                        }?: run {
                            requireContext().showShortToast(Constants.SOMETHINGWRONG)
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

    companion object {
        fun newInstance(): Profile_Fragment {
            return Profile_Fragment()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnUpdateProfile.id->{

                // all edit Text set error is empty
                setErrorMessageEmpty()

                var emptyFiledCheck :Boolean =true

                if(binding.tieMobileNumber.text.isNullOrEmpty()){
                    emptyFiledCheck = false
                    binding.tilMobileNumber.error = "Mobile Number is Required"
                }

                if(binding.tieChangePassword.text.isNullOrEmpty()){
                    emptyFiledCheck = false
                    binding.tilChangePassword.error = "Password is Required"
                }

                if(!emptyFiledCheck) return



                profileViewModel.changePassword(binding.tieMobileNumber.text.toString(),binding.tieChangePassword.text.toString(),binding.tieChangePassword.text.toString()).observe(requireActivity(),{
                   when (it.status) {
                        Resource.Status.SUCCESS -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            if (it.data != null) {
                                MyDialog.responseDialogCbsl(requireContext(),it.data.Message,null,false,
                                    Utility.getResponseWishStyleForTxnDialog(requireContext(),true)){
                                    findNavController().navigateUp()
                                }
                            }
                        }
                        Resource.Status.ERROR -> {
                            Utility.dismissDialog(requireActivity(),processDialog)
                            MyDialog.responseDialogCbsl(requireContext(),it.data?.Message,null,false,
                                Utility.getResponseWishStyleForTxnDialog(requireContext(),false)){
                                findNavController().navigateUp()
                            }
                        }
                        Resource.Status.LOADING -> {
                            processDialog.show()
                        }
                    }
                })
            }
            binding.btnProfileCancel.id->{
                findNavController().navigateUp()
            }
        }

    }

    fun setErrorMessageEmpty(){
        binding.tieMobileNumber.error = null
        binding.tilMobileNumber.isErrorEnabled = false

        binding.tieUserName.error = null
        binding.tilUserName.isErrorEnabled = false

        binding.tieEmailId.error = null
        binding.tilEmailId.isErrorEnabled = false

        binding.tieChangePassword.error = null
        binding.tilChangePassword.isErrorEnabled = false
    }
}