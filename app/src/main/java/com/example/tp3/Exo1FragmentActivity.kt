package com.example.tp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Exo1FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo1_fragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, Exo1Fragment())
            .commit()
    }
}