package `in`.cbslgroup.ucobank.viewmodels

import `in`.cbslgroup.ucobank.model.response.appointment.AppointmentVO
import `in`.cbslgroup.ucobank.model.response.appointment.BranchResponse
import `in`.cbslgroup.ucobank.model.response.appointment.ServicesResponse
import `in`.cbslgroup.ucobank.model.response.appointment.TimeSlotVO
import `in`.cbslgroup.ucobank.model.response.login.LoginResponse
import `in`.cbslgroup.ucobank.repositories.AppointmentRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.roysoft.documentmanagementsystem.network.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import org.json.JSONObject

class AppointmentViewModel: ViewModel()  {

    fun getBranch( lat :Double , log :Double): LiveData<Resource<BranchResponse>> =
       liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = AppointmentRepository().getBranch(lat,log)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }


    fun getServices(branchId :String): LiveData<Resource<ServicesResponse>>
    =liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = AppointmentRepository().getServices(branchId)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }




    fun getSlotByBranchWise(branchId : Int ,selectDate : String ): LiveData<Resource<TimeSlotVO>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = AppointmentRepository().getSlotByBranchWise(branchId , selectDate)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }

    fun saveAppointment(requestData : RequestBody): LiveData<Resource<AppointmentVO>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = AppointmentRepository().saveAppointment(requestData)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }

    fun getKioskIdbyBranch(branchId: String): LiveData<Resource<String>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = AppointmentRepository().getKioskIdbyBranch(branchId)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }
}