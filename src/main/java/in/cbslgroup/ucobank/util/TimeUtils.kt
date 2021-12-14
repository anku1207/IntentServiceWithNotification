package `in`.cbslgroup.ucobank.util

import java.util.*

internal object TimeUtils {
    // <-- no need to care about indexes
// <-- List instead of array
    val dummyList: List<String>
        get() {
            val quarterHours = arrayOf("00", "15", "30", "45")
            val times: MutableList<String> = ArrayList() // <-- List instead of array
            for (i in 9..23) {
                for (j in 0..3) {
                    var time = i.toString() + ":" + quarterHours[j]
                    if (i < 10) {
                        time = "0$time"
                    }
                    times.add("$time") // <-- no need to care about indexes
                }
            }
            return times
        }
}