package `in`.cbslgroup.ucobank.viewmodels

import `in`.cbslgroup.ucobank.model.response.DashboardResponse
import `in`.cbslgroup.ucobank.model.response.appointment.SearchAppointmentVO
import `in`.cbslgroup.ucobank.model.response.dashboard.AppointmentListItemResponse
import `in`.cbslgroup.ucobank.repositories.DashboardRepository
import `in`.cbslgroup.ucobank.repositories.SearchRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.roysoft.documentmanagementsystem.network.Resource
import kotlinx.coroutines.Dispatchers

class DashboardViewModel : ViewModel() {

    fun getDashboardData(customerId: String): LiveData<Resource<DashboardResponse>> =

        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {

                val req = DashboardRepository().getDashboardData(customerId)
                return@liveData emit(req)

            } catch (e: Exception) {

                return@liveData emit(Resource.Error(e.message!!))
            }

        }


    fun getSearchedAppointments(customerId: String,aptType: String): LiveData<Resource<AppointmentListItemResponse>>
    =liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = DashboardRepository().getAppointment(customerId, aptType)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }



    fun filterAppointment(customerId:Int , branchId:Int , selectDate :String , slot :String): LiveData<Resource<AppointmentListItemResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = SearchRepository().filterAppointment(customerId,branchId,selectDate,slot)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }
}