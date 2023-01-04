package com.esgi.groupe9.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
            Log.d("ForgotPasswordActivity", "Try to send an email for reset password to the User")
            Constants.FIREBASE_AUTH.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d(
                        "ForgotPasswordActivity",
                        "Successfully send email to the user with the email : $email"
                    )
                    // i wait 2 seconds after i redirect the user to the login page
                    Handler().postDelayed({
                        val intent = Intent(this, LoginActivity::class.java)
                        // TODO : need to clear the TAG on this page before startActivity
                        startActivity(intent)
                    }, 2000)
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

    private fun Timer.schedule(i: Int, function: () -> Unit) {}
}


