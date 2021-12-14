package `in`.cbslgroup.ucobank.location

import android.location.Location

interface Google_Services_Interface {
    fun getLastLocation(location: Location?)
    fun onFailure(e: Exception?)
}