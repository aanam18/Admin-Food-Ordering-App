//package com.example.adminfoodorderingapp
//
//import android.R
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
//import com.google.firebase.Firebase
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.database
//
//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var email: String
//    private lateinit var password: String
//    private lateinit var userName: String
//    private lateinit var nameOfRestaurant: String
//
//    //Using database to store username and name of restaurant, username
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
//        //Initialize firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        //Initialize firebase database
//        database = Firebase.database.reference
//
//        binding.createAccount.setOnClickListener {
//            userName = binding.name.text.toString().trim()
//            nameOfRestaurant = binding.restaurantName.text.toString().trim()
//            email = binding.emailOrPhone.text.toString().trim()
//            password = binding.password.text.toString().trim()
//            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
//            } else {
////                createAccount1(email, password)
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT)
//                                .show()
//                            startActivity(Intent(this, LoginActivity::class.java))
//                            finish()
//                        } else {
//                            Toast.makeText(
//                                this,
//                                "Registration Failed: ${task.exception?.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//
////            }
//                            binding.alreadyHaveButton.setOnClickListener {
//                                val intent = Intent(this, LoginActivity::class.java)
//                                startActivity(intent)
//                            }
//                            val locationList =
//                                arrayOf("Mt Eden", "Mt Roskill", "Newmarket", "Epsom")
//                            //how the list will be displayed
//                            val adapter =
//                                ArrayAdapter(this, R.layout.simple_list_item_1, locationList)
//                            val autoCompleteTextView = binding.listOfLocation
//                            autoCompleteTextView.setAdapter(adapter)
//                        }
//                    }
//       }
//
//    private fun createAccount1(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Account creation Failed", Toast.LENGTH_SHORT).show()
//                Log.d("Account", "createAccount: Failure", task.exception)
//            }
//        }
//    }
//}
//
package com.example.adminfoodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
import com.example.adminfoodorderingapp.model.UserM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference

        binding.createAccount.setOnClickListener {
            val userName = binding.name.text.toString().trim()
            val nameOfRestaurant = binding.restaurantName.text.toString().trim()
            val email = binding.emailOrPhone.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (userName.isEmpty() || nameOfRestaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                createUserWithEmailAndPassword(email, password)
            }
        }

        binding.alreadyHaveButton.setOnClickListener {

            finish() // Finish SignUpActivity
        }

        val locationList = arrayOf("Mt Eden", "Mt Roskill", "Newmarket", "Epsom")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        binding.listOfLocation.setAdapter(adapter)
    }

    private fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    // You can save additional user details to Firebase database here
                    // For example: database.child("users").child(user.uid).setValue(User(userName, nameOfRestaurant, email))
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    saveUserData()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Finish SignUpActivity
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
// save  data into database
    private fun saveUserData() {
        val userName = binding.name.text.toString().trim()
        val nameOfRestaurant = binding.restaurantName.text.toString().trim()
        val email = binding.emailOrPhone.text.toString().trim()
        val password = binding.password.text.toString().trim()
//save user data in firebase
        val user = UserM(userName,nameOfRestaurant,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}

//package com.example.adminfoodorderingapp
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import com.example.adminfoodorderingapp.databinding.ActivitySignUpBinding
//import com.example.adminfoodorderingapp.model.UserModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//    private lateinit var binding: ActivitySignUpBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
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
//                createUserWithEmailAndPassword(email, password, userName, nameOfRestaurant)
//            }
//        }
//
//        binding.alreadyHaveButton.setOnClickListener {
//            finish() // Finish SignUpActivity
//        }
//
//        val locationList = arrayOf("Mt Eden", "Mt Roskill", "Newmarket", "Epsom")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
//        binding.listOfLocation.setAdapter(adapter)
//    }
//
//    private fun createUserWithEmailAndPassword(email: String, password: String, userName: String, nameOfRestaurant: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign up success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//                    // Save user data to Firebase database
//                    saveUserData(userName, nameOfRestaurant, email, password)
//                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//                    // Navigate to login activity after successful registration
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
//
//    private fun saveUserData(userName: String, nameOfRestaurant: String, email: String, password: String) {
//        // Save user data in Firebase
//        val userId = auth.currentUser?.uid ?: ""
//        val user = UserModel(userName, nameOfRestaurant, email, password)
//        database.child("users").child(userId).setValue(user)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("SignUpActivity", "User data saved successfully")
//                } else {
//                    Log.e("SignUpActivity", "Error saving user data: ${task.exception}")
//                }
//            }
//    }
//}
