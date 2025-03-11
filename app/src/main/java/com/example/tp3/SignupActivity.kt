package com.example.tp3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Locale

class SignupActivity : ComponentActivity() {

    private lateinit var dbExo1: DBExo1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbExo1 = DBExo1(this)

        val editTextLogin = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextFirstName = findViewById<EditText>(R.id.editTextTextFirstName)
        val editTextLastName = findViewById<EditText>(R.id.editTextLastName)
        val editTextBirthday = findViewById<EditText>(R.id.editTextBirthday)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            if (validateLogin(editTextLogin) &&
                validatePassword(editTextPassword) &&
                validateEmail(editTextEmail) &&
                validatePhone(editTextPhone) &&
                validateBirthday(editTextBirthday) &&
                validateHobby(radioGroup)
            ) {
                val login = editTextLogin.text.toString()
                val password = editTextPassword.text.toString()
                val firstName = editTextFirstName.text.toString()
                val lastName = editTextLastName.text.toString()
                val birthday = editTextBirthday.text.toString()
                val phone = editTextPhone.text.toString()
                val email = editTextEmail.text.toString()
                val hobby = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()

                dbExo1.insertUser(login, password, firstName, lastName, birthday, phone, email, hobby)
                Toast.makeText(this, "Inscription terminée", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erreur : veuillez corriger les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Vérification du champ login
    private fun validateLogin(editText: EditText): Boolean {
        val login = editText.text.toString()
        if (!Regex("^[a-zA-Z][a-zA-Z0-9]{0,9}$").matches(login)) {
            editText.error = "Le login doit commencer par une lettre et max 10 caractères"
            return false
        } else if (dbExo1.userExists(login)) {
            editText.error = "Le login existe déjà"
            return false
        } else {
            editText.error = null
            return true
        }
    }

    // Vérification du mot de passe (au moins 6 caractères, 1 lettre et 1 chiffre)
    private fun validatePassword(editText: EditText): Boolean {
        val password = editText.text.toString()
        if (!Regex("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$").matches(password)) {
            editText.error = "Le mot de passe doit contenir au moins 6 caractères, 1 lettre et 1 chiffre"
            return false
        } else {
            editText.error = null
            return true
        }
    }

    // Vérification de l'email (doit être un email valide)
    private fun validateEmail(editText: EditText): Boolean {
        val email = editText.text.toString()
        if (!Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)) {
            editText.error = "Email invalide"
            return false
        } else {
            editText.error = null
            return true
        }
    }

    // Vérification du numéro de téléphone (doit contenir exactement 10 chiffres)
    private fun validatePhone(editText: EditText): Boolean {
        val phone = editText.text.toString()
        if (!Regex("^\\d{10}$").matches(phone)) {
            editText.error = "Le téléphone doit contenir exactement 10 chiffres"
            return false
        } else {
            editText.error = null
            return true
        }
    }

    // Vérification de la date de naissance (doit avoir au moins 18 ans)
    private fun validateBirthday(editText: EditText): Boolean {
        val dateStr = editText.text.toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        dateFormat.isLenient = false
        try {
            dateFormat.parse(dateStr)
            editText.error = null
            return true
        } catch (e: Exception) {
            editText.error = "Format de date invalide (utilisez JJ-MM-AAAA)"
            return false
        }
    }

    // Vérification qu'un loisir a été sélectionné
    private fun validateHobby(radioGroup: RadioGroup): Boolean {
        if (radioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Veuillez sélectionner un loisir", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}
