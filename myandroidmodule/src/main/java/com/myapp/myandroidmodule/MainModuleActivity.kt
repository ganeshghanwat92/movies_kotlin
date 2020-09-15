package com.myapp.myandroidmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_module.*

class MainModuleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_module)

        buttonMyModuleButton.setOnClickListener {

            Toast.makeText(this,"Button click",Toast.LENGTH_SHORT).show()

        }

    }
}
