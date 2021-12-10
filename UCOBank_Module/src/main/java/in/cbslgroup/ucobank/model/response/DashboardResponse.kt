package `in`.cbslgroup.ucobank.model.response

import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItem

data class DashboardResponse(

	val cancelledAppointment: Int? = null,
	val upcomingAppointment: Int? = null,
	val AllAppointment : Int?=null,
	val TodayAppointment : Int?=null,
	val appointmentList: List<AppointmentListItem>? = null,
	val Message: String? = null,
	val CompletedAppointment: Int? = null,
	val isError: Boolean? = null,
	val Name :String?=null,
	val Email :String?=null
)

/*data class AppointmentListItem(

	val mobileNo: String? = null,
	val status: String? = null,
	val customerId: Int? = null,
	val branchId: Int? = null,
	val appointmentNumber: String? = null,
	val serviceId: Int? = null,
	val appointmentdatetime: String? = null,
	val serviceName: String? = null,
	val appointmentId: Int? = null,
	val kioskId: Int? = null,
	val slot: String? = null,
	val customerName: String? = null,
	val branchName: String? = null,
	val tokenId: Int? = null

)*/

