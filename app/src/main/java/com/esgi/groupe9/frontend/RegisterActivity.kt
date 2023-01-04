package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpUser()
        goOnLoginPage()
    }

    private fun signUpUser() {
        val registerButton = findViewById<Button>(R.id.create_account_button)
        registerButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username_input_register).text.toString()
            val email = findViewById<EditText>(R.id.email_input_register).text.toString()
            val password = findViewById<EditText>(R.id.password_input_register).text.toString()
            val confirmPassword =
                findViewById<EditText>(R.id.confirm_password_input_register).text.toString()

            when {
                email.isEmpty() && password.isNotEmpty() -> {
                    Toast
                        .makeText(this, "Please fill the email input text", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                password.isEmpty() && email.isNotEmpty() -> {
                    Toast
                        .makeText(this, "Please fill the password input text", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                email.isEmpty() && password.isEmpty() -> {
                    Toast
                        .makeText(this, "Please fill both input text", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                username.isEmpty() -> {
                    Toast
                        .makeText(this, "Please fill the username input text", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            }

            /* TODO :
                implement logic to check the equality between password and confirm Password and for all
                the others fields if it's not, set the colorText to red and set the create button to disable.
            */

            Log.d("RegisterActivity", "Try to register a User to the Firebase Authentication")
            Constants.FIREBASE_AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    val userId = it.result.user?.uid.toString();
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        "RegisterActivity",
                        "Successfully created user with the UserID : ${it.result.user?.uid}"
                    )
                    saveUserToDatabase(userId)
                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "Failed to create user due to : ${it.message}")
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
        val username: String = findViewById<EditText>(R.id.username_input_register).text.toString()
        val email: String = findViewById<EditText>(R.id.email_input_register).text.toString()
        val user = User(userId, username, email)

        Log.d("RegisterActivity", "Try to add a User to the FireStore Database")
        Constants.FIREBASE_FIRESTORE.collection("users")
            .add(user)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("RegisterActivity", "User has been inserted in database with the id : $userId")
            }
            .addOnFailureListener {
                Log.w(
                    "RegisterActivity",
                    "User has not been inserted in db because of ${it.message}"
                )
            }

    }

    private fun goOnLoginPage() {
        val backButton = findViewById<Button>(R.id.return_login_page)
        backButton.setOnClickListener {
            finish()
        }
    }
}
