package `in`.cbslgroup.ucobank.repositories

import `in`.cbslgroup.ucobank.model.response.DashboardResponse
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItemResponse
import com.roysoft.bigstepmovieapptest.network.ResponseHandler
import com.roysoft.documentmanagementsystem.network.ApiInterface
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl.BANK_BASE_URL

class DashboardRepository {

    private var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getDashboardData(customerId: String): Resource<DashboardResponse> {
        return try {
            val bankUrl=BANK_BASE_URL+"Dashboard/"+customerId
            val response = ApiInterface.invoke().getDashboardData(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getAppointment(customerId: String,aptType: String): Resource<AppointmentListItemResponse> {
        return try {
            val bankUrl=BANK_BASE_URL+"Dashboard/"+aptType+"/"+customerId
            val response = ApiInterface.invoke().getAppointment(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}