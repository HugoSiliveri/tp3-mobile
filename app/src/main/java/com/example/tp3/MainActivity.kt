package com.example.tp3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonExo1 = findViewById<Button>(R.id.buttonExo1)
        buttonExo1.setOnClickListener {
            val intent = Intent(this, Exo1Activity::class.java)
            startActivity(intent)
        }
    }
}