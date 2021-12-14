package `in`.cbslgroup.ucobank.model.response.appointment

import com.google.gson.annotations.SerializedName

data class TimeSlotVO(

    @field:SerializedName("isError")
    val isError: Boolean? = null,

    @field:SerializedName("Message")
    val Message: String? = null,


    @field:SerializedName("BranchId")
    val BranchId: Int? = null,

    @field:SerializedName("AppointmentDate")
    val AppointmentDate: String? = null,

    @field:SerializedName("Slot")
    val Slot: List<String>? = null,
)
