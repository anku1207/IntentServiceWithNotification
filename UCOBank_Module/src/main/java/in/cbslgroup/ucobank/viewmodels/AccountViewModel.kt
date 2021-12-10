package `in`.cbslgroup.ucobank.viewmodels

import `in`.cbslgroup.ucobank.model.response.DefaultResponse
import `in`.cbslgroup.ucobank.model.response.DuplicateVO
import `in`.cbslgroup.ucobank.model.response.login.ChangePasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.ForgotPasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import `in`.cbslgroup.ucobank.model.response.login.RegisterResponse
import `in`.cbslgroup.ucobank.repositories.LoginRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.roysoft.documentmanagementsystem.network.Resource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.NotNull

class AccountViewModel : ViewModel() {

    var mobileno = MutableLiveData<String>()
    var password = MutableLiveData<String>()


    fun login(@NotNull mobileno: String,@NotNull pwd: String,): LiveData<Resource<LoginResponse>>
    = liveData(Dispatchers.IO){
        emit(value = Resource.Loading())
        try {
            return@liveData emit(LoginRepository().login(mobileno, pwd))
        } catch (e: Exception) {
            Log.e("login_ex", e.toString())
            return@liveData emit(Resource.Error(e.toString()))
        }


    }


    fun verifyOtp(@NotNull mobileno: String,@NotNull otp: String,): LiveData<Resource<DefaultResponse>>
        = liveData(Dispatchers.IO)
    {

        emit(value = Resource.Loading())
        try {
            return@liveData emit(LoginRepository().verifyOtp(mobileno, otp))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }


    fun changePassword(
        @NotNull mobileno: String,
        @NotNull newPwd: String,
        @NotNull confirmPwd: String,
    ): LiveData<Resource<ChangePasswordResponse>> = liveData(Dispatchers.IO)
    {

        emit(value = Resource.Loading())
        try {
            return@liveData emit(
                LoginRepository().changePassword(
                    mobileno, newPwd, confirmPwd
                )
            )
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }


    fun forgotPassword(@NotNull mobileno: String,): LiveData<Resource<ForgotPasswordResponse>>
    = liveData(Dispatchers.IO){
        emit(value = Resource.Loading())
        try {
            return@liveData emit(LoginRepository().forgotPassword(mobileno))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }


    fun forgetPasswordOTPVerification(@NotNull mobileno: String,@NotNull otp: String,): LiveData<Resource<DefaultResponse>>
    = liveData(Dispatchers.IO)
    {
        emit(value = Resource.Loading())
        try {
            return@liveData emit(
                LoginRepository().forgetPasswordOTPVerification(mobileno, otp)
            )
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }
    }


    fun resendOTP(@NotNull mobileno: String): LiveData<Resource<DefaultResponse>> = liveData(Dispatchers.IO){
        emit(value = Resource.Loading())
        try {
            return@liveData emit(LoginRepository().resendOtp(mobileno))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }


    fun register(@NotNull mobileNo: String,@NotNull name: String,@NotNull email: String): LiveData<Resource<RegisterResponse>>
    = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            return@liveData emit(LoginRepository().register(mobileNo, name, email))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }



    fun checkDuplicateNumber(@NotNull mobileNo: String): LiveData<Resource<DuplicateVO>>
            = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            return@liveData emit(LoginRepository().checkDuplicateNumber(mobileNo))
        } catch (e: Exception) {
            return@liveData emit(Resource.Error(e.toString()))
        }

    }

}