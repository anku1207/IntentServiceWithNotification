package `in`.cbslgroup.ucobank.viewmodels

import `in`.cbslgroup.ucobank.model.response.dashboard.ProfileVO
import `in`.cbslgroup.ucobank.model.response.login.ChangePasswordResponse
import `in`.cbslgroup.ucobank.repositories.LoginRepository
import `in`.cbslgroup.ucobank.repositories.ProfileRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.roysoft.documentmanagementsystem.network.Resource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.NotNull

class ProfileViewModel : ViewModel(){
    fun getUserDetails(customerId : Int): LiveData<Resource<ProfileVO>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = ProfileRepository().getUserDetails(customerId)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }


    fun changePassword(mobileno: String,newPwd: String,confirmPwd: String,): LiveData<Resource<ChangePasswordResponse>>
    = liveData(Dispatchers.IO)
    {
        emit(value = Resource.Loading())
        try {
            return@liveData emit(LoginRepository().changePassword(mobileno, newPwd, confirmPwd))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }

}