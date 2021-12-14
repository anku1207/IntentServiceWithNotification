package `in`.cbslgroup.ucobank.repositories


import `in`.cbslgroup.ucobank.model.response.appointment.AppointmentVO
import `in`.cbslgroup.ucobank.model.response.appointment.BranchResponse
import `in`.cbslgroup.ucobank.model.response.appointment.ServicesResponse
import `in`.cbslgroup.ucobank.model.response.appointment.TimeSlotVO
import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import com.roysoft.bigstepmovieapptest.network.ResponseHandler
import com.roysoft.documentmanagementsystem.network.ApiInterface
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl
import okhttp3.RequestBody
import org.json.JSONObject

class AppointmentRepository {

    private var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getBranch(lat :Double , log :Double): Resource<BranchResponse> {
        return try {
            val bankUrl= ApiUrl.BANK_BASE_URL +"Appointment/getBranch?lat="+lat+"&lng="+log
            val response = ApiInterface.invoke().getBranch(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getServices(branchId : String): Resource<ServicesResponse> {

        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Appointment/getServices/"+branchId
            val response = ApiInterface.invoke().getServices(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)

        }
    }


    suspend fun getSlotByBranchWise(branchId : Int , selectDate : String): Resource<TimeSlotVO> {
        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Appointment/getEmptyTimeSlot/"+branchId+"/"+selectDate
            val response = ApiInterface.invoke().getSlotByBranchWise(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)

        }
    }

    suspend fun saveAppointment(requestData : RequestBody): Resource<AppointmentVO> {
        return try {
            val response = ApiInterface.invoke().saveAppointment(requestData)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)

        }
    }

    suspend fun getKioskIdbyBranch(branchId: String): Resource<String> {
        return try {
            val bankUrl=ApiUrl.BANK_BASE_URL +"Appointment/getKioskIdbyBranch/"+branchId
            val response = ApiInterface.invoke().getKioskIdbyBranch(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)

        }
    }
}