package com.example.intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity()  {
    val CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    lateinit var intentFilter :IntentFilter
    lateinit var checkInternetReceiver: CheckInternetReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("BaseActivity" , "BaseActivity")

        intentFilter= IntentFilter()
        intentFilter.addAction(CONNECTIVITY_ACTION)
        checkInternetReceiver=CheckInternetReceiver()
    }

    override fun onStart() {
        super.onStart()
        Log.w("onStart", "onStart");
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(checkInternetReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(checkInternetReceiver)

    }

    override fun onStop() {
        super.onStop()
    }

    inner class CheckInternetReceiver : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.w("BroadcastReceiver", p1?.action.toString());
        }

    }

}