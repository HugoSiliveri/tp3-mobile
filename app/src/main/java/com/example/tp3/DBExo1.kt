package com.example.tp3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBExo1(context: Context) : SQLiteOpenHelper(context, "db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = """
            CREATE TABLE user (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                login TEXT,
                password TEXT,
                firstname TEXT,
                lastname TEXT,
                birthday TEXT,
                phone TEXT,
                email TEXT,
                hobby TEXT
            )
        """.trimIndent()

        val createTableActivities = """
            CREATE TABLE activities (
                id INTEGER PRIMARY KEY AUTOINCREMENT,  
                slot_one TEXT,
                slot_two TEXT,
                slot_three TEXT,
                slot_four TEXT
            )
        """.trimIndent()

        val createTablePlanning = """
            CREATE TABLE planning (
                id_user INTEGER,
                id_activities INTEGER,
                date TEXT,
                PRIMARY KEY (id_user, id_activities),
                FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE,
                FOREIGN KEY (id_Activities) REFERENCES Activities(id) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createTableUser)
        db.execSQL(createTableActivities)
        db.execSQL(createTablePlanning)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS planning")
        db.execSQL("DROP TABLE IF EXISTS activities")
        onCreate(db)
    }

    fun insertUser(login: String, password: String, firstname: String, lastname: String, birthday: String, phone: String, email: String, hobby: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("login", login)
            put("password", password)
            put("firstname", firstname)
            put("lastname", lastname)
            put("birthday", birthday)
            put("phone", phone)
            put("email", email)
            put("hobby", hobby)
        }
        val result = db.insert("user", null, values)
        db.close()
        return result != -1L
    }

    fun insertActivities(slotOne: String, slotTwo: String, slotThree: String, slotFour: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("slot_one", slotOne)
            put("slot_two", slotTwo)
            put("slot_three", slotThree)
            put("slot_four", slotFour)
        }
        val result = db.insert("activities", null, values)
        db.close()
        return result != -1L
    }

    fun insertPlanning(idUser: Int, idActivities: Int, date: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id_user", idUser)
            put("id_activities", idActivities)
            put("date", date)
        }
        val result = db.insert("planning", null, values)
        db.close()
        return result != -1L
    }

    fun getLastUser(): User? {
        val db = readableDatabase
        var user: User? = null
        val cursor = db.rawQuery("SELECT * FROM user ORDER BY rowid DESC LIMIT 1", null)
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(0),
                login = cursor.getString(1),
                password = cursor.getString(2),
                firstname = cursor.getString(3),
                lastname = cursor.getString(4),
                birthday = cursor.getString(5),
                phone = cursor.getString(6),
                email = cursor.getString(7),
                hobby = cursor.getString(8)
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun getLastActivities(): Activities? {
        val db = readableDatabase
        var activities: Activities? = null
        val cursor = db.rawQuery("SELECT * FROM activities ORDER BY rowid DESC LIMIT 1", null)
        if (cursor.moveToFirst()) {
            activities = Activities(
                id = cursor.getInt(0),
                slotOne = cursor.getString(1),
                slotTwo = cursor.getString(2),
                slotThree = cursor.getString(3),
                slotFour = cursor.getString(4),
            )
        }
        cursor.close()
        db.close()
        return activities
    }

    fun getUserById(idUser: Int): User? {
        val db = readableDatabase
        var user: User? = null
        val cursor = db.rawQuery("SELECT * FROM user WHERE id = ?", arrayOf(idUser.toString()))
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(0),
                login = cursor.getString(1),
                password = cursor.getString(2),
                firstname = cursor.getString(3),
                lastname = cursor.getString(4),
                birthday = cursor.getString(5),
                phone = cursor.getString(6),
                email = cursor.getString(7),
                hobby = cursor.getString(8)
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun getActivitiesById(idActivities: Int): Activities? {
        val db = readableDatabase
        var activities: Activities? = null
        val cursor = db.rawQuery("SELECT * FROM activities WHERE id = ?", arrayOf(idActivities.toString()))
        if (cursor.moveToFirst()) {
            activities = Activities(
                id = cursor.getInt(0),
                slotOne = cursor.getString(1),
                slotTwo = cursor.getString(2),
                slotThree = cursor.getString(3),
                slotFour = cursor.getString(4),
            )
        }
        cursor.close()
        db.close()
        return activities
    }

    fun getPlanningById(idUser: Int, idActivities: Int): Planning? {
        val db = readableDatabase
        var planning: Planning? = null
        val cursor = db.rawQuery("SELECT * FROM planning WHERE id_user = ? AND id_activities = ?", arrayOf(idUser.toString(), idActivities.toString()))
        if (cursor.moveToFirst()) {
            planning = Planning(
                idUser = cursor.getInt(0),
                idActivities = cursor.getInt(1),
                date = cursor.getString(2),
            )
        }
        cursor.close()
        db.close()
        return planning
    }

    fun getUser(login: String, password: String): User? {
        val db = readableDatabase
        var user: User? = null
        val cursor = db.rawQuery("SElECT * FROM user WHERE login = ? AND password = ?", arrayOf(login, password))
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(0),
                login = cursor.getString(1),
                password = cursor.getString(2),
                firstname = cursor.getString(3),
                lastname = cursor.getString(4),
                birthday = cursor.getString(5),
                phone = cursor.getString(6),
                email = cursor.getString(7),
                hobby = cursor.getString(8)
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun userExists(login: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE login = ?", arrayOf(login))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }
}

data class User (
    val id: Int,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val phone: String,
    val email: String,
    val hobby: String
)

data class Activities (
    val id: Int,
    val slotOne: String,
    val slotTwo: String,
    val slotThree: String,
    val slotFour: String
)

data class Planning (
    val idUser: Int,
    val idActivities: Int,
    val date: String
)
