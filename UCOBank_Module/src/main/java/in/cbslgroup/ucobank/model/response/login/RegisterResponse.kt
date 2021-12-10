package `in`.cbslgroup.ucobank.model.response.login

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("Message")
	val Message: String? = null,

	@field:SerializedName("OTP")
	val oTP: String? = null,

	@field:SerializedName("CustomerId")
	val customerId: Int? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Password")
	val password: String? = null
)
