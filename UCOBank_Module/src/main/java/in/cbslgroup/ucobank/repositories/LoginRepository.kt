package `in`.cbslgroup.ucobank.repositories

import `in`.cbslgroup.ucobank.model.response.DefaultResponse
import `in`.cbslgroup.ucobank.model.response.DuplicateVO
import `in`.cbslgroup.ucobank.model.response.login.ChangePasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.ForgotPasswordResponse
import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import `in`.cbslgroup.ucobank.model.response.login.RegisterResponse
import android.util.Log
import com.roysoft.bigstepmovieapptest.network.ResponseHandler
import com.roysoft.documentmanagementsystem.network.ApiInterface
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import retrofit2.http.Field


class LoginRepository {

    val responseHandler = ResponseHandler()

    suspend fun login(@NotNull mobileNo: String, @NotNull password: String): Resource<LoginResponse> {
        return try {
            val jsondata =JSONObject()
            jsondata.let {
                it.put("MobileNo", mobileNo)
                it.put("Password", password)
                it.put("url", ApiUrl.BANK_BASE_URL +"Account/Login")
            }
            val request = jsondata.toString().toRequestBody("application/json".toMediaTypeOrNull());
            val response = ApiInterface.invoke().login(request)
            return responseHandler.handleSuccess(response)
       } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }


    suspend fun verifyOtp(@NotNull mobileNo: String, @NotNull otp: String): Resource<DefaultResponse> {
        return try {
            val jsondata =JSONObject()
            jsondata.let {
                it.put("MobileNo", mobileNo)
                it.put("OTP", otp)
                it.put("url",ApiUrl.BANK_BASE_URL +"Account/VerifyOTP")
            }
            val request = jsondata.toString().toRequestBody("application/json".toMediaTypeOrNull());
            val response = ApiInterface.invoke().verifyOtp(request)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }

    suspend fun changePassword(
        @NotNull mobileNo: String,
        @NotNull password: String,
        @NotNull confirmPassword: String,
    ): Resource<ChangePasswordResponse> {
        return try {
            val jsondata =JSONObject()
            jsondata.let {
                it.put("MobileNo", mobileNo)
                it.put("Password", password)
                it.put("ConfirmPassword", confirmPassword)
                it.put("url",ApiUrl.BANK_BASE_URL +"Account/ChangePassword")
            }
            val request = jsondata.toString().toRequestBody("application/json".toMediaTypeOrNull());
            val response =ApiInterface.invoke().changePwd(request)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }


    suspend fun forgotPassword(@NotNull mobileNo: String): Resource<ForgotPasswordResponse> {
        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Account/ForgetPassword/"+mobileNo
            val response =ApiInterface.invoke().forgotPassword(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }

    suspend fun forgetPasswordOTPVerification(@NotNull mobileNo: String, @NotNull otp: String): Resource<DefaultResponse> {
        return try {
            val jsondata =JSONObject()
            jsondata.let {
                it.put("MobileNo", mobileNo)
                it.put("OTP", otp)
                it.put("url",ApiUrl.BANK_BASE_URL +"Account/VerifyOTP")
            }
            val request = jsondata.toString().toRequestBody("application/json".toMediaTypeOrNull());
            val response =ApiInterface.invoke().verifyOtp(request)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }



    suspend fun resendOtp(
        @NotNull mobileNo: String,
    ): Resource<DefaultResponse> {
        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Account/ResendOTP/"+mobileNo
            val response =
                ApiInterface.invoke().resendOTP(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }

    suspend fun register(@NotNull mobileNo: String, @NotNull name: String, @NotNull email: String): Resource<RegisterResponse> {
        return try {

            val jsondata =JSONObject()
            jsondata.let {
                it.put("MobileNo", mobileNo)
                it.put("Name", name)
                it.put("Email", email)
                it.put("url",ApiUrl.BANK_BASE_URL +"Account/Register")
            }
            val request = jsondata.toString().toRequestBody("application/json".toMediaTypeOrNull());
            val response =ApiInterface.invoke().register(request)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }


    suspend fun checkDuplicateNumber(@NotNull mobileNo: String): Resource<DuplicateVO> {
        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Account/isDuplicateMobileNo/"+mobileNo
            val response =ApiInterface.invoke().isDuplicateMobileNo(bankUrl);
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e("retro", e.message!!)
            responseHandler.handleException(e)
        }
    }


}