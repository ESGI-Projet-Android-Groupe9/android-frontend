package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInUser()
        goOnRegisterPage()
        goOnForgotPasswordPage()
    }

    private fun signInUser() {
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_input_login).text.toString()
            val password = findViewById<EditText>(R.id.password_input_login).text.toString()

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
            }

            Log.d("LoginActivity", "Attempt login with Email : $email")
            Constants.FIREBASE_AUTH.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    // TODO : check the successfully of this, on the next video of kotlin messenger
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        "LoginActivity",
                        "Successfully login user with the UserID : ${it.result.user?.uid}"
                    )
                }
                // TODO : add addOnFailureListener(), on the next video of kotlin messenger
                .addOnFailureListener {
                    Log.d("LoginActivity", "Failed to login user due to : ${it.message}")
                    Toast.makeText(
                        this,
                        "Failed to login user due to : ${it.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun goOnRegisterPage() {
        val registerButton = findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goOnForgotPasswordPage() {
        val forgotLinkPassword = findViewById<TextView>(R.id.forgot_password_link)
        forgotLinkPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}