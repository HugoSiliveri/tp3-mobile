package com.example.tp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Exo1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo1)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, Exo1Fragment())
            .commit()
    }
}