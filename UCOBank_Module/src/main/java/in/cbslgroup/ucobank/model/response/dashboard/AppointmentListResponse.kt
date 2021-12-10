package `in`.cbslgroup.ucobank.model.response.dashboard

import com.google.gson.annotations.SerializedName

data class AppointmentListResponse(

    @field:SerializedName("AppointmentId") val appointmentId: Int,
    @field:SerializedName("AppointmentNumber") val appointmentNumber: String,
    @field:SerializedName("TokenId") val tokenId: Int,
    @field:SerializedName("CustomerId") val customerId: Int,
    @field:SerializedName("CustomerName") val customerName: String,
    @field:SerializedName("CustomerType") val customerType: String,
    @field:SerializedName("MobileNo") val mobileNo: Int,
    @field:SerializedName("BranchId") val branchId: Int,
    @field:SerializedName("BranchName") val branchName: String,
    @field:SerializedName("KioskId") val kioskId: Int,
    @field:SerializedName("ServiceId") val serviceId: Int,
    @field:SerializedName("ServiceName") val serviceName: String,
    @field:SerializedName("Appointmentdatetime") val appointmentdatetime: String,
    @field:SerializedName("Slot") val slot: String,
    @field:SerializedName("Status") val status: String

)
