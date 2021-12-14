package `in`.cbslgroup.ucobank.model.response.appointment

import com.google.gson.annotations.SerializedName

data class ServicesResponse(

	@field:SerializedName("isError")
		val isError: Boolean? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("serviceList")
	val serviceList: List<ServiceListItem>? = null
)

data class ServiceListItem(

	@field:SerializedName("ServiceName")
	val serviceName: String? = null,

	@field:SerializedName("ServiceId")
	val serviceId: Int? = null
)
