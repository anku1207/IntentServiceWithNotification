package `in`.cbslgroup.ucobank.model

import android.graphics.drawable.Drawable

data class DashboardItem(var id: Int,var image: Drawable, var name: String, var count: String)

object DashboardMenuVO{
    const val ADD_APPOINTMENT=1
    const val ALL_APPOINTMENT=2
    const val TODAY_APPOINTMENT=3
    const val UPCOMING_APPOINTMENT=4
    const val CANCELLED_APPOINTMENT=5
    const val COMPLETED_APPOINTMENT=6
}
