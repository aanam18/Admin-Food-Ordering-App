
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

//package com.example.adminfoodorderingapp
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
//import com.example.adminfoodorderingapp.model.UserM
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//
//    private val binding: ActivitySignUpBinding by lazy {
//        ActivitySignUpBinding.inflate(layoutInflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        // Initialize Firebase database reference
//        database = FirebaseDatabase.getInstance().reference
//
//        binding.createAccount.setOnClickListener {
//            val userName = binding.name.text.toString().trim()
//            val nameOfRestaurant = binding.restaurantName.text.toString().trim()
//            val email = binding.emailOrPhone.text.toString().trim()
//            val password = binding.password.text.toString().trim()
//
//            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
//            } else {
//                createUserWithEmailAndPassword(email, password)
//            }
//        }
//
//        binding.alreadyHaveButton.setOnClickListener {
//
//            finish() // Finish SignUpActivity
//        }
//
//        val locationList = arrayOf("Mt Eden", "Mt Roskill", "Newmarket", "Epsom")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
//        binding.listOfLocation.setAdapter(adapter)
//    }
//
//    private fun createUserWithEmailAndPassword(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign up success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//                    // You can save additional user details to Firebase database here
//                    // For example: database.child("users").child(user.uid).setValue(User(userName, nameOfRestaurant, email))
//                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//                    saveUserData()
//                    val intent = Intent(this,LoginActivity::class.java)
//                    startActivity(intent)
//                    finish() // Finish SignUpActivity
//                } else {
//                    // If sign up fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext, "Registration failed: ${task.exception?.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//    }
//    // save  data into database
//    private fun saveUserData() {
//        val userName = binding.name.text.toString().trim()
//        val nameOfRestaurant = binding.restaurantName.text.toString().trim()
//        val email = binding.emailOrPhone.text.toString().trim()
//        val password = binding.password.text.toString().trim()
////save user data in firebase
//        val user = UserM(userName,nameOfRestaurant,email,password)
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//        database.child("user").child(userId).setValue(user)
//    }
//}
