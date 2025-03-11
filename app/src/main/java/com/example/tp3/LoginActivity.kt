package com.example.tp3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity() {

    private lateinit var dbExo1: DBExo1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbExo1 = DBExo1(this)

        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val login = editTextLogin.text.toString()
            val password = editTextPassword.text.toString()

            if (dbExo1.getUser(login, password) != null) {
                val intent = Intent(this, PlanningActivity::class.java)
                intent.putExtra("login", login)
                intent.putExtra("password", password)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}