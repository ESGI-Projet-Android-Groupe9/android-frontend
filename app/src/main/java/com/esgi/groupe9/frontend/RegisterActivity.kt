package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        registerUser()
        goBack()
    }

    private fun registerUser() {
        val registerButton = findViewById<Button>(R.id.signup_button_register)
        registerButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username_text_register)
                .text
                .toString()
            val email = findViewById<EditText>(R.id.email_text_register)
                .text
                .toString()
            val password = findViewById<EditText>(R.id.password_text_register)
                .text
                .toString()
            val confirmPassword = findViewById<EditText>(R.id.confirm_password_text_register)
                .text
                .toString()

            // TODO : try to refactor this also and use switch case
            if (email.isEmpty() && password.isNotEmpty()) {
                Toast
                    .makeText(this, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (password.isEmpty() && email.isNotEmpty()) {
                Toast
                    .makeText(this, "Please fill the password input text", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (email.isEmpty() && password.isEmpty()) {
                Toast
                    .makeText(this, "Please fill both input text", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            /* TODO : implement logic to check the equality between password and
                confirmPassword if it's not, set the colorText to red and set the create button to disable.
            */

            Log.d("RegisterActivity", "Try to register a User to the Firebase Authentication")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        "RegisterActivity",
                        "Successfully created user with the UserID : ${it.result.user?.uid}"
                    )
                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "Failed to create user due to : ${it.message}")
                    Toast
                        .makeText(
                            this,
                            "Failed to create user due to : ${it.message}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        }
    }

    private fun goBack() {
        val backButton = findViewById<Button>(R.id.return_login_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}