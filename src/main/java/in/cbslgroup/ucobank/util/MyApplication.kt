package `in`.cbslgroup.ucobank.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class  MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate
                    .MODE_NIGHT_NO
            )
    }


}