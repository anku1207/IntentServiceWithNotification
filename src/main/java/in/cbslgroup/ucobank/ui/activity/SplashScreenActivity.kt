package `in`.cbslgroup.ucobank.ui.activity

import `in`.cbslgroup.ucobank.util.SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ucobank_module.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {

            delay(2000)
            // manoj shakya 10-04-2021
            if (!SessionManager(this@SplashScreenActivity).isLoggedIn) {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashScreenActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}