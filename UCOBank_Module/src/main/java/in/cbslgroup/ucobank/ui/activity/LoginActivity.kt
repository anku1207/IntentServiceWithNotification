package `in`.cbslgroup.ucobank.ui.activity

import `in`.cbslgroup.ucobank.util.SessionManager
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.ucobank_module.R


class LoginActivity : AppCompatActivity() {

    lateinit var navController: NavController

    val sessionManager: SessionManager by lazy { SessionManager(this) }

    lateinit var progress: ProgressDialog

    /*override fun onBackPressed() {

        if (navController.popBackStack().not()) {

            finish()
        } else {

            navController.navigateUp()
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //testing purpose
        // startActivity(Intent(this, MainActivity::class.java))
        val toolbar: Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_login_fragment)

//        //the user is already logged in
//        if (sessionManager.isLoggedIn) {
//
//
//
//        }

//        findViewById<Button>(R.id.btn_login).setOnClickListener {
//
//            startActivity(Intent(this, OtpActivity::class.java))
//
//        }
//
//
//        findViewById<TextView>(R.id.tv_login_register).setOnClickListener {
//
//            startActivity(Intent(this, RegisterActivity::class.java))
//
//        }


    }


   /* override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
*/

    fun showProgress() {

        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please Wait..")
        progress.setCancelable(false)

    }

    fun dismissProgress() {

        progress.cancel()
    }


}