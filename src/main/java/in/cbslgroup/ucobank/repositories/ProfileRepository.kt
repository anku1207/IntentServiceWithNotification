package `in`.cbslgroup.ucobank.repositories

import `in`.cbslgroup.ucobank.model.response.appointment.BranchResponse
import `in`.cbslgroup.ucobank.model.response.dashboard.ProfileVO
import com.roysoft.bigstepmovieapptest.network.ResponseHandler
import com.roysoft.documentmanagementsystem.network.ApiInterface
import com.roysoft.documentmanagementsystem.network.Resource
import com.roysoft.documentmanagementsystem.utils.ApiUrl

class ProfileRepository {
    private var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getUserDetails(customerId :Int ): Resource<ProfileVO> {
        return try {
            val bankUrl= ApiUrl.BANK_BASE_URL +"Account/getCustomerProfile/"+customerId
            val response = ApiInterface.invoke().getUserDetails(bankUrl)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}