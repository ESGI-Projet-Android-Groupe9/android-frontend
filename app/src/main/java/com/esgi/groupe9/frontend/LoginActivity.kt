package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.esgi.groupe9.frontend.utils.Constants

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

            // check the input of the user in the LoginActivity
            if (!checkInput(email, password)) return@setOnClickListener

            Log.d("LoginActivity", "Attempt login with Email : $email")
            Constants.FIREBASE_AUTH.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        TAG,
                        "Successfully login user with the UserID : ${it.result.user?.uid}"
                    )
                    goOnHomePage()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to login user due to : ${it.message}")
                    Toast.makeText(
                        this,
                        "Failed to login user due to : ${it.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun checkInput(email: String, password: String): Boolean {
        when {
            email.isEmpty() && password.isNotEmpty() -> {
                Toast
                    .makeText(this, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            password.isEmpty() && email.isNotEmpty() -> {
                Toast
                    .makeText(this, "Please fill the password input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() && password.isEmpty() -> {
                Toast
                    .makeText(this, "Please fill both input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    private fun goOnHomePage() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun goOnRegisterPage() {
        val registerButton = findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun goOnForgotPasswordPage() {
        val forgotLinkPassword = findViewById<TextView>(R.id.forgot_password_link)
        forgotLinkPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG: String = "LoginActivity"
    }
}