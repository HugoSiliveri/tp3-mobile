package com.example.tp3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBExo1(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE users (
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
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
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
        val result = db.insert("users", null, values)
        db.close()
        return result != -1L
    }

    fun getUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)
        if (cursor.moveToFirst()) {
            do {
                val user = User(
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
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun getLastUser(): User? {
        val db = readableDatabase
        var user: User? = null
        val cursor = db.rawQuery("SELECT * FROM users ORDER BY rowid DESC LIMIT 1", null)
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
        val cursor = db.rawQuery("SELECT * FROM users WHERE login = ?", arrayOf(login))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun checkUser(login: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE login = ? AND password = ?", arrayOf(login, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1
    }
}

data class User(
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

