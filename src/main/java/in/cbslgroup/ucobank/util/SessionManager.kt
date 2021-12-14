package `in`.cbslgroup.ucobank.util

import `in`.cbslgroup.ucobank.ui.activity.LoginActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*

object Session{
    lateinit var sharedPreferences: SharedPreferences
    var gson = Gson()
    var CACHE_LOCATION = "LOCATION"
    var CACHE_LOCATION_LAT = "LOCATION_LAT"
    var CACHE_LOCATION_LON = "LOCATION_LON"



    fun set_Data_Sharedprefence(context: Context, CacheName: String?, data: String?) {
        sharedPreferences =context.getSharedPreferences(SessionManager.PREF_NAME, 0)
        val edit: SharedPreferences.Editor =sharedPreferences.edit()
        edit.putString(CacheName, data)
        edit.apply()
        edit.commit()
    }
    fun getSessionByKey(context: Context, cacheKey: String?): String? {
        val sharedPreferences =context.getSharedPreferences(SessionManager.PREF_NAME, 0)
        return sharedPreferences.getString(cacheKey, null)
    }
}

class SessionManager(context: Context) {
    var sharedPreferences: SharedPreferences
    var context: Context
    var editor: SharedPreferences.Editor
    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
        editor = sharedPreferences.edit()
        this.context = context
    }

    fun createLoginSession(customerid: String?, firstLogin: Boolean, contact: String?) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)
        //Storing User Details
        editor.putString(KEY_CONTACT, contact)
        editor.putString(KEY_CONSTOMER_ID, customerid)
        editor.putBoolean(KEY_FIRST_LOGIN, firstLogin)
        // commit changes
        editor.commit()

    }

    /**
     * Get customer id
     */

    fun getCustomerId() : String?{

        return sharedPreferences.getString(KEY_CONSTOMER_ID, "1")

    }



    /**
     * Get stored session data
     */
    val userDetails: HashMap<String, String?>
        get() {
            val user =
                HashMap<String, String?>()
            // user name
            user[KEY_CONSTOMER_ID] = sharedPreferences.getString(KEY_CONSTOMER_ID, "1")
            //user[KEY_FIRST_LOGIN] = sharedPreferences.getString(KEY_FIRST_LOGIN, "0")
            user[KEY_CONTACT] = sharedPreferences.getString(KEY_CONTACT, "1")
            // return user
            return user
        }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
//    fun checkLogin() { // Check login status
//        if (isLoggedIn) { // user is not logged in redirect him to Login Activity
//            val i = Intent(context, LoginActivity::class.java)
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            // Add new Flag to start new Activity
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            // Staring Login Activity
//            context.startActivity(i)
//        }
//    }

    /**
     * Clear session details
     */
    fun logoutUser() { // Clearing all data from Shared Preferences
        editor.clear()
        editor.commit()
        // After logout redirect user to Login Activity
        val i = Intent(context, LoginActivity::class.java)
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Add new Flag to start new Activity
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        // Staring Login Activity
        context.startActivity(i)

    }

    /**
     * Quick check for login
     */
// Get Login State
    val isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGIN, false)

    val isLoggedFirstTime: Boolean?
        get() = sharedPreferences.getBoolean(KEY_FIRST_LOGIN, false)


    companion object {
        // (make variable public to access from outside)
        const val KEY_CONSTOMER_ID = "customer_id"
        const val KEY_CONTACT = "user_contact"
        const val KEY_FIRST_LOGIN = "user_first_login"

        // Sharedpref file name
        public const val PREF_NAME = "UserDetails"
        // All Shared Preferences Keys
        private const val IS_LOGIN = "IsLoggedIn"
    }
}