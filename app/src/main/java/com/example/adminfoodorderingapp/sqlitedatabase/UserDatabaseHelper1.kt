package com.example.adminfoodorderingapp.sqlitedatabase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.adminfoodorderingapp.model.UserM

class UserDatabaseHelper1(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_RESTAURANT_NAME = "restaurant_name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USERNAME TEXT,"
                + "$COLUMN_RESTAURANT_NAME TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createUserTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // If you need to upgrade the database, add logic here
    }

    fun insertUser(user: UserM): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, user.name)
        values.put(COLUMN_RESTAURANT_NAME, user.restaurantName)
        values.put(COLUMN_EMAIL, user.emailOrPhone)
        values.put(COLUMN_PASSWORD, user.password)

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getUserByEmailAndPassword(email: String, password: String): UserM? {
        val db = this.readableDatabase
        val cursor: Cursor? = db.query(
            TABLE_NAME, arrayOf(
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_RESTAURANT_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
            ),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null,
            null
        )
        var user: UserM? = null
        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val userName = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                val nameOfRestaurant = cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_NAME))
                val userEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                val userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                user = UserM(userName, nameOfRestaurant, userEmail, userPassword)
            }
        }
        return user
    }
}
