package `in`.cbslgroup.ucobank.ui.activity

import `in`.cbslgroup.ucobank.LocationUtil.PermissionUtils
import `in`.cbslgroup.ucobank.location.Google_Services
import `in`.cbslgroup.ucobank.location.Google_Services_Interface
import `in`.cbslgroup.ucobank.permission.PermissionHandler
import `in`.cbslgroup.ucobank.ui.bottomsheets.LogoutDialogFragment
import `in`.cbslgroup.ucobank.ui.fragment.main.DashboardFragmentDirections
import `in`.cbslgroup.ucobank.util.*
import android.app.Dialog
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.ucobank_module.R
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import java.util.*


class MainActivity : AppCompatActivity(), Google_Services_Interface ,
    PermissionUtils.PermissionResultCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val sessionManager by lazy { SessionManager(this) }
    lateinit var drawerLayout: DrawerLayout

    lateinit var side_menu_name: TextView
    lateinit var side_menu_email:TextView
    lateinit var side_menu_top_layout: LinearLayout
    var myMenu: Menu? = null


    //get location
    var google_services: Google_Services? = null
    var permissionUtils: PermissionUtils? = null


    //process bar dialog
    val processDialog : Dialog by lazy {processBarDialog(false,"wait...")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        google_services = Google_Services(this, this)
        permissionUtils = PermissionUtils(this)


        // show process bar
        processDialog.show()
        permissionUtils!!.check_permission(
            PermissionHandler.gpsLocationPermission(this),
            "Need GPS permission for getting your location",
            1
        )

        //check Customer Session
        if(!checkCustomerSession()){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        //layout
        drawerLayout = findViewById(R.id.drawer_layout)



        //side menu
        val navView: NavigationView = findViewById(R.id.nav_view)
        side_menu_name=navView.getHeaderView(0).findViewById(R.id.side_menu_name)
        side_menu_email=navView.getHeaderView(0).findViewById(R.id.side_menu_email)
        side_menu_top_layout=navView.getHeaderView(0).findViewById(R.id.side_menu_top_layout)


        //fragment container
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_dashboard, R.id.nav_add_appointment, R.id.nav_logout
            ), drawerLayout
        )*/
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupWithNavController(toolbar, navController, appBarConfiguration)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { it: MenuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_logout -> {
                    val bs = LogoutDialogFragment.newInstance()
                    bs.show(supportFragmentManager, "bottomsheet_logout")
                    bs.onItemClickListener = object : LogoutDialogFragment.OnItemClickListener {
                        override fun onYesClicked() {
                            sessionManager.logoutUser()
                            finish()
                        }

                        override fun onNoClicked() {
                            bs.dismiss()
                        }
                    }
                    true
                }
                R.id.nav_add_appointment -> {
                    navController.navigate(R.id.addAppointmentFragment)
                    true
                }
                else -> {
                    true
                }
            }
        }
        //if the user has not logged in

        side_menu_top_layout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                drawerLayout.closeDrawer(GravityCompat.START);
                val directionsProfile_Fragment =
                    DashboardFragmentDirections.actionNavDashboardToProfileFragment()
                findNavController(R.id.nav_host_fragment).navigate(directionsProfile_Fragment)
            }

        })
    }

    private fun checkCustomerSession() :Boolean{
       return sessionManager.isLoggedIn
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        google_services!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionUtils!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        super.onResume()
        google_services!!.checkPlayServices()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        myMenu = menu;
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return super.onSupportNavigateUp() || navController.navigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
          //  val bs = SearchDialogFragment.newInstance()
         // bs.show(supportFragmentManager, "bottomsheet")

             when( findNavController(R.id.nav_host_fragment).currentDestination?.label.toString()){
                 "Dashboard" -> {
                     val directions =
                         DashboardFragmentDirections.actionNavDashboardToSearchDialogFragment(
                             "Dashboard"
                         )
                     findNavController(R.id.nav_host_fragment).navigate(directions)
                 }

             }
       }
        return super.onOptionsItemSelected(item)
    }

    override fun getLastLocation(location: Location?) {
        if (location != null) {
            //val latLng = LatLng(location.latitude, location.longitude)
            Session.set_Data_Sharedprefence(this,Session.CACHE_LOCATION_LAT,location.latitude.toString())
            Session.set_Data_Sharedprefence(this,Session.CACHE_LOCATION_LON,location.longitude.toString())
            //showLongToast(Session.getSessionByKey(this,Session.CACHE_LOCATION)!!)
        } else {
            showLongToast("Couldn't get the lmBearingAccuracyDegrees = 0.0ocation. Make sure location is enabled on the device")
        }
        Utility.dismissDialog(this,processDialog)
    }

    override fun onFailure(e: Exception?) {
        finish()
    }

    override fun PermissionGranted(request_code: Int) {
        google_services!!.google_Service_LocationRequest()
    }

    override fun PartialPermissionGranted(
        request_code: Int,
        granted_permissions: ArrayList<String>?
    ) {
        finish()
    }

    override fun PermissionDenied(request_code: Int) {
        finish()
    }
    override fun NeverAskAgain(request_code: Int) {
        finish()
    }
}