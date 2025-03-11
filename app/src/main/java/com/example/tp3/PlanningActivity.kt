package com.example.tp3

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat

class PlanningActivity : ComponentActivity() {

    private lateinit var dbExo1: DBExo1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        dbExo1 = DBExo1(this)

        val login = intent.getStringExtra("login").toString()
        val password = intent.getStringExtra("password").toString()

        val user = dbExo1.getUser(login, password)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewDate = findViewById<TextView>(R.id.textViewDate)
        val editTextSlotOne = findViewById<EditText>(R.id.editTextSlotOne)
        val editTextSlotTwo = findViewById<EditText>(R.id.editTextSlotTwo)
        val editTextSlotThree = findViewById<EditText>(R.id.editTextSlotThree)
        val editTextSlotFour = findViewById<EditText>(R.id.editTextSlotFour)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formattedDate = formatter.format(date)

        if (user != null) {
            textViewName.text = "${user.firstname} ${user.lastname}"
        }
        textViewDate.text = "${formattedDate}"

        buttonSubmit.setOnClickListener {
            val slotOne = editTextSlotOne.text.toString()
            val slotTwo = editTextSlotTwo.text.toString()
            val slotThree = editTextSlotThree.text.toString()
            val slotFour = editTextSlotFour.text.toString()

            dbExo1.insertActivities(slotOne, slotTwo, slotThree, slotFour)

            val activities = dbExo1.getLastActivities()
            if (user != null && activities != null) {
                dbExo1.insertPlanning(user.id, activities.id, formattedDate)
                Toast.makeText(this, "Planning sauvegardé", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, PlanningViewActivity::class.java)
                intent.putExtra("idUser", user.id)
                intent.putExtra("idActivities", activities.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Problème lors de la sauvegarde du planning", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
