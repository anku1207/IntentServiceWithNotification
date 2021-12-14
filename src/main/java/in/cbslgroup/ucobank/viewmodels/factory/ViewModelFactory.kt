package `in`.cbslgroup.ucobank.viewmodels.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
//
//            return HistoryViewModel(application) as T
//
//        }

        throw IllegalArgumentException("Unknown class name")
    }

}