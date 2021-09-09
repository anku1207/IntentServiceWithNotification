package com.example.intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.intentservice.service.CustomServie
import android.app.Activity




class MainActivity : BaseActivity() {
    lateinit var customEvent :CustomEvent
    lateinit var textview:TextView
    var dd :String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.w("MainActivity" , "MainActivity")

        textview=findViewById(R.id.textview)
        customEvent=CustomEvent()

        val intent =Intent(this,CustomServie::class.java)
        startService(intent)



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

    inner class CustomEvent :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            textview.text=p1?.getStringExtra("data").toString()
        }

    }
}

