package `in`.cbslgroup.ucobank.model.response.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("MobileNo")
    val mobileNo: String? = null,

    @field:SerializedName("isFirstLogin")
    val isFirstLogin: Boolean? = null,

    @field:SerializedName("customerId")
    val customerId: Int? = null,

//    @field:SerializedName("ResponseMessage")
//    val responseMessage: String? = null,


    @field:SerializedName("Password")
    val password: String? = null,

    @field:SerializedName("Message")
    val Message: String? = null,

    @field:SerializedName("isError")
    val isError: Boolean? = null,
)
