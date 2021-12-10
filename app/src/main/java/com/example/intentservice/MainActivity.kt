package com.example.intentservice

import `in`.cbslgroup.ucobank.ui.activity.SplashScreenActivity
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.testmodule.Module_MainActivity


class MainActivity : BaseActivity() {
    lateinit var customEvent :CustomEvent
    lateinit var textview: TextView
    lateinit var btn: Button
    var dd :String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.w("MainActivity" , "MainActivity")

        textview=findViewById(R.id.textview)
        btn=findViewById(R.id.btn)


        customEvent=CustomEvent()

      /*  val intent =Intent(this,CustomServie::class.java)
        startService(intent)
*/


        var myActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult> {
                // ToDO:
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    Toast.makeText(this,it.data?.getStringExtra("value"),Toast.LENGTH_SHORT).show()
                }
            }
        ) as ActivityResultLauncher<Intent>

        btn.setOnClickListener{
            val intent =Intent(this, SplashScreenActivity::class.java)
            myActivityResultLauncher.launch(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(customEvent, IntentFilter("custom-event"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(customEvent)
    }

    fun uiUpdate(data :String){
    }

    inner class CustomEvent : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            textview.text=p1?.getStringExtra("data").toString()
        }
    }
}

