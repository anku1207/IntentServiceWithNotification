package `in`.cbslgroup.ucobank.model.response.appointment

import com.google.gson.annotations.SerializedName

data class AppointmentVO(
    @field:SerializedName("Message")
    val Message: String? = null,

    @field:SerializedName("isError")
    val isError: Boolean? = null,)
