package `in`.cbslgroup.ucobank.model.response.login

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("Message")
	val Message: String? = null,

	@field:SerializedName("customerId")
	val customerId: Int? = null,

	@field:SerializedName("Password")
	val password: String? = null,

	@field:SerializedName("ConfirmPassword")
	val confirmPassword: String? = null,

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	)
