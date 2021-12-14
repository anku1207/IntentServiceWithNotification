package `in`.cbslgroup.ucobank.model.response.appointment

import com.google.gson.annotations.SerializedName

data class SearchAppointmentVO(

    @field:SerializedName("isError")
    val isError: Boolean? = null,

    @field:SerializedName("Message")
    val Message: String? = null,
)
