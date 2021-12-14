package `in`.cbslgroup.ucobank.viewmodels

import `in`.cbslgroup.ucobank.model.response.appointment.SearchAppointmentVO
import `in`.cbslgroup.ucobank.model.response.dashboard.ProfileVO
import `in`.cbslgroup.ucobank.repositories.ProfileRepository
import `in`.cbslgroup.ucobank.repositories.SearchRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.roysoft.documentmanagementsystem.network.Resource
import kotlinx.coroutines.Dispatchers

class SearchViewModel : ViewModel() {
   /* fun filterAppointment(customerId:Int ,barchId : Int ,selectDate:String,slot:String ): LiveData<Resource<SearchAppointmentVO>> =
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val req = SearchRepository().filterAppointment(customerId,barchId,selectDate,slot)
                return@liveData emit(req)
            } catch (e: Exception) {
                return@liveData emit(Resource.Error(e.message!!))
            }
        }*/
}