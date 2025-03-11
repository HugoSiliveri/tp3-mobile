package com.example.tp3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class PlanningViewActivity : ComponentActivity() {

    private lateinit var dbExo1: DBExo1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning_view)

        dbExo1 = DBExo1(this)

        val idUser = intent.getIntExtra("idUser", -1)
        val idActivities = intent.getIntExtra("idActivities", -1)

        val textViewInfo = findViewById<TextView>(R.id.textViewInfo)
        val textViewSlotOne = findViewById<TextView>(R.id.textViewSlotOne)
        val textViewSlotTwo = findViewById<TextView>(R.id.textViewSlotTwo)
        val textViewSlotThree = findViewById<TextView>(R.id.textViewSlotThree)
        val textViewSlotFour = findViewById<TextView>(R.id.textViewSlotFour)

        val activities = dbExo1.getActivitiesById(idActivities)
        val planning = dbExo1.getPlanningById(idUser, idActivities)

        if (activities != null && planning != null) {
            textViewInfo.text = textViewInfo.text.toString() + " ${planning.date}"
            textViewSlotOne.text = textViewSlotOne.text.toString()  + " ${activities.slotOne}"
            textViewSlotTwo.text = textViewSlotTwo.text.toString()  + " ${activities.slotTwo}"
            textViewSlotThree.text = textViewSlotThree.text.toString()  + " ${activities.slotThree}"
            textViewSlotFour.text = textViewSlotFour.text.toString()  + " ${activities.slotFour}"
        }
    }
}