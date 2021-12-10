package `in`.cbslgroup.ucobank.model.response.dashboard

import com.google.gson.annotations.SerializedName

data class ProfileVO(
    @field:SerializedName("Message")
    val Message: String? = null,

    @field:SerializedName("isError")
    val isError: Boolean? = null,

    @field:SerializedName("CustomerId")
    val CustomerId: Int? = null,

    @field:SerializedName("MobileNo")
    val MobileNo: String? = null,

    @field:SerializedName("Email")
    val Email: String? = null,

    @field:SerializedName("Name")
    val Name: String? = null,

    @field:SerializedName("Password")
    val Password: String? = null,

)
