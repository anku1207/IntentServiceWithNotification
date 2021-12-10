package `in`.cbslgroup.ucobank.model.response.login

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("Message")
	val Message: String? = null,

	@field:SerializedName("OTP")
	val oTP: String? = null
)
