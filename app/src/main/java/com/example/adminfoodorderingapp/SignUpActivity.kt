
package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
import com.example.adminfoodorderingapp.model.UserM
import com.example.adminfoodorderingapp.sqlitedatabase.UserDatabaseHelper

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dbHelper = UserDatabaseHelper(this)

        binding.createAccount.setOnClickListener {
            val userName = binding.name.text.toString().trim()
            val nameOfRestaurant = binding.restaurantName.text.toString().trim()
            val email = binding.emailOrPhone.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                saveUserData(userName, nameOfRestaurant, email, password)
            }
        }

        binding.alreadyHaveButton.setOnClickListener {
            finish() // Finish SignUpActivity
        }

        val locationList = arrayOf("Mt Eden", "Mt Roskill", "Newmarket", "Epsom")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        binding.listOfLocation.setAdapter(adapter)
    }

    private fun saveUserData(userName: String, nameOfRestaurant: String, email: String, password: String) {
        val user = UserM(userName, nameOfRestaurant, email, password)
        val result = dbHelper.insertUser(user)
        if (result != -1L) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish SignUpActivity
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }
}
