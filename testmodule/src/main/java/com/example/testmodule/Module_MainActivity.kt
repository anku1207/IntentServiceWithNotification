package com.example.testmodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.testmodule.databinding.ModuleActivityMainBinding

class Module_MainActivity : AppCompatActivity() {
    lateinit var module_btn:Button
    lateinit var mainBinding: ModuleActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_activity_main)


        mainBinding = DataBindingUtil.setContentView(this, R.layout.module_activity_main)

      //  module_btn=findViewById(R.id.module_btn);

        mainBinding.moduleBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.putExtra("value","sdfsljdf")
                setResult(RESULT_OK, intent)
                finish()
            }

        })


    }
}