package com.example.testmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Module_MainActivity : AppCompatActivity() {
    lateinit var module_btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_activity_main)

        module_btn=findViewById(R.id.module_btn);

        module_btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra("value","sdfsljdf")
                setResult(RESULT_OK, intent)
                finish()
            }

        })


    }
}