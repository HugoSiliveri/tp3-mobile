package com.example.tp3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class Exo1FragmentResult : Fragment() {

    private lateinit var databaseHelper: DBExo1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_exo1_fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DBExo1(requireContext())
        val user = databaseHelper.getLastUser()

        val textViewResult = view.findViewById<TextView>(R.id.textViewResult)
        val resultText = StringBuilder()

        if (user != null) {
            resultText.append("Login: ${user.login}\nNom: ${user.firstname} ${user.lastname}\nEmail: ${user.email}\nHobby: ${user.hobby}\n\n")
        }

        textViewResult.text = resultText.toString()
    }

}