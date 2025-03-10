package com.example.tp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment

class Exo1Fragment : Fragment() {

    private lateinit var dbExo1: DBExo1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_exo1_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbExo1 = DBExo1(requireContext())

        val editTextLogin = view.findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword)
        val editTextFirstName = view.findViewById<EditText>(R.id.editTextTextFirstName)
        val editTextLastName = view.findViewById<EditText>(R.id.editTextLastName)
        val editTextBirthday = view.findViewById<EditText>(R.id.editTextBirthday)
        val editTextPhone = view.findViewById<EditText>(R.id.editTextPhone)
        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            val login = editTextLogin.text.toString()
            val password = editTextPassword.text.toString()
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val birthday = editTextBirthday.text.toString()
            val phone = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()

            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val hobby = if (selectedRadioButtonId != -1) {
                view.findViewById<RadioButton>(selectedRadioButtonId).text.toString()
            } else {
                "Aucun"
            }

            dbExo1.insertUser(login, password, firstName, lastName, birthday, phone, email, hobby)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, Exo1FragmentResult())
                .commit()
        }
    }
}