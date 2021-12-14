package `in`.cbslgroup.ucobank.model.response.dashboard

import com.google.gson.annotations.SerializedName

data class AppointmentListItemResponse(

	@field:SerializedName("isError")
	val isError: Boolean? = null,

	@field:SerializedName("Message")
	val Message: String? = null,

	@field:SerializedName("appointmentList")
	val appointmentList: List<AppointmentListItem>? = null

)

data class AppointmentListItem(

	@field:SerializedName("MobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("CustomerId")
	val customerId: Int? = null,

	@field:SerializedName("BranchId")
	val branchId: Int? = null,

	@field:SerializedName("AppointmentNumber")
	val appointmentNumber: String? = null,

	@field:SerializedName("CustomerType")
	val customerType: Any? = null,

	@field:SerializedName("ServiceId")
	val serviceId: Int? = null,

	@field:SerializedName("Appointmentdatetime")
	val appointmentdatetime: String? = null,

	@field:SerializedName("ServiceName")
	val serviceName: String? = null,

	@field:SerializedName("AppointmentId")
	val appointmentId: Int? = null,

	@field:SerializedName("KioskId")
	val kioskId: Int? = null,

	@field:SerializedName("Slot")
	val slot: String? = null,

	@field:SerializedName("CustomerName")
	val customerName: String? = null,

	@field:SerializedName("BranchName")
	val branchName: String? = null,

	@field:SerializedName("TokenId")
	val tokenId: Int? = null
)
