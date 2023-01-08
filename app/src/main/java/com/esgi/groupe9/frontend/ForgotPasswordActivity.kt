package com.esgi.groupe9.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.esgi.groupe9.frontend.utils.Constants
import java.util.*

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        resetPassword()
    }

    private fun resetPassword() {
        val resetPasswordButton = findViewById<Button>(R.id.forgot_password_button)

        resetPasswordButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.forgot_password_email_input).text.toString()

            if (!checkInputForgot(email)) return@setOnClickListener

            Log.d("ForgotPasswordActivity", "Try to send an email for reset password to the User")
            Constants.FIREBASE_AUTH.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        "ForgotPasswordActivity",
                        "Successfully send email to the user with the email : $email"
                    )
                    Toast
                        .makeText(
                            this,
                            "Successfully send email to the user with the email : $email",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    redirectOnLogin()
                }
                .addOnFailureListener {
                    Log.d(
                        "ForgotPasswordActivity",
                        "Error while sending the email due to : ${it.message}"
                    )
                    Toast
                        .makeText(
                            this,
                            "Error while sending the email due to : ${it.message}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        }
    }

    private fun checkInputForgot(email: String): Boolean {
        when {
            email.isEmpty() -> {
                Toast
                    .makeText(this, "Please fill the email input text", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    private fun redirectOnLogin() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 2000)
    }
}


