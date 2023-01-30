package com.esgi.groupe9.frontend

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.esgi.groupe9.frontend.entity.User
import com.esgi.groupe9.frontend.utils.Constants

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpUser()
        goOnLoginPage()
    }

    private fun signUpUser() {
        val registerButton = findViewById<Button>(R.id.create_account_button)

        checkPasswordEquality()

        registerButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username_input_register).text.toString()
            val email = findViewById<EditText>(R.id.email_input_register).text.toString()
            val password = findViewById<EditText>(R.id.password_input_register).text.toString()

            if (!checkInputRegister(email, password, username)) return@setOnClickListener

            Log.d(TAG, "Try to register a User to the Firebase Authentication")
            Constants.FIREBASE_AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    val userId = it.result.user?.uid.toString()
                    Log.d(
                        TAG,
                        "Successfully created user with the UserID : ${it.result.user?.uid}"
                    )
                    saveUserToDatabase(userId)
                    redirectOnLogin()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to create user due to : ${it.message}")
                    Toast.makeText(
                        this,
                        "Failed to create user due to : ${it.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun saveUserToDatabase(userId: String) {
        val username: String =
            findViewById<EditText>(R.id.username_input_register).text.toString()
        val email: String = findViewById<EditText>(R.id.email_input_register).text.toString()
        val likesList = listOf<String>()
        val wishlist = listOf<String>()
        val user = User(userId, username, email, likesList, wishlist)

        Log.d(TAG, "Try to add a User to the FireStore Database")
        Constants.FIREBASE_FIRESTORE.collection("users")
            .add(user)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d(
                    TAG,
                    "User has been inserted in database with the id : $userId"
                )
            }
            .addOnFailureListener {
                Log.w(
                    TAG,
                    "User has not been inserted in db because of ${it.message}"
                )
            }

    }

    private fun checkPasswordEquality() {
        val confirmPassword = findViewById<EditText>(R.id.confirm_password_input_register)
        confirmPassword.addTextChangedListener {
            val registerButton = findViewById<Button>(R.id.create_account_button)
            val password = findViewById<EditText>(R.id.password_input_register)
            if (confirmPassword.text.toString() == password.text.toString()) {
                registerButton.isEnabled = true
                registerButton.background.alpha = 255
                registerButton.setTextColor(Color.WHITE)
            } else {
                registerButton.isEnabled = false
                registerButton.background.alpha = 100
                registerButton.setTextColor(Color.GRAY)
            }
        }
        // TODO: check when the password has been changed after the equality of both input
    }

    private fun checkInputRegister(email: String, password: String, username: String): Boolean {
        when {
            email.isEmpty() && password.isNotEmpty() && username.isNotEmpty() -> {
                Toast
                    .makeText(this, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            password.isEmpty() && email.isNotEmpty() && username.isNotEmpty() -> {
                Toast
                    .makeText(this, "Please fill the password input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            username.isEmpty() && email.isNotEmpty() && password.isNotEmpty() -> {
                Toast
                    .makeText(this, "Please fill the username input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() && username.isEmpty() -> {
                Toast
                    .makeText(this, "Please fill all input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() -> {
                Toast
                    .makeText(
                        this,
                        "Email & Password are required to create account",
                        Toast.LENGTH_SHORT
                    )
                    .show()
                return false
            }
        }
        return true
    }

    private fun goOnLoginPage() {
        val backButton = findViewById<Button>(R.id.return_login_page)
        backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun redirectOnLogin() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 2000)
    }

    companion object {
        private const val TAG: String = "HomeActivity"
    }
}

