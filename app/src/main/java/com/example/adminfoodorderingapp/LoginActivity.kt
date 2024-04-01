package com.example.adminfoodorderingapp

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.adminfoodorderingapp.databinding.ActivityLoginBinding
import com.example.adminfoodorderingapp.model.UserM
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private var userName:String? = null
    private var nameOfRestaurant: String?=null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase database reference
        database = Firebase.database.reference

        binding.loginButton.setOnClickListener {
            // Get text from edit text

            email = binding.EmailLogin.text.toString().trim()
            password = binding.LoginPass.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
            }

        }
        binding.dontHaveAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                user?.let { updateUri(it) }
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, "Create User & Login Successful", Toast.LENGTH_SHORT).show()

                        saveUserData()
                        user?.let { updateUri(it) }
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        Log.d(
                            "Account",
                            "createUserAccount: Authentication Failed",
                            task.exception
                        )
                    }
                }
            }
        }
    }


private fun saveUserData() {
    email = binding.EmailLogin.text.toString().trim()
    password = binding.LoginPass.text.toString().trim()
    val user = UserM(userName, nameOfRestaurant,email, password)
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    userId?.let {
        database.child("user").child(it).setValue(user)
    }
}

private fun updateUri(user: FirebaseUser) {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}
}