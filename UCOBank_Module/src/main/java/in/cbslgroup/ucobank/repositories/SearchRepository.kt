package `in`.cbslgroup.ucobank.repositories

import `in`.cbslgroup.ucobank.model.response.DashboardResponse
import `in`.cbslgroup.ucobank.model.response.appointment.SearchAppointmentVO
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItemResponse
import com.roysoft.bigstepmovieapptest.network.ResponseHandler
import com.roysoft.documentmanagementsystem.network.ApiInterface
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl

class SearchRepository {

    private var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun filterAppointment(customerId:Int , branchId:Int , selectDate :String , slot :String ): Resource<AppointmentListItemResponse> {
        return try {

            val bankUrl= ApiUrl.BANK_BASE_URL +"Appointment/getAppointment/?customerid="+customerId+"&branchid="+branchId+"&date="+selectDate+"&slot="+slot

            val response = ApiInterface.invoke().getFilterAppointment(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }


}