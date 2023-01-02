package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logUser()
        goRegister()
    }

    private fun logUser() {
        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_text)
                .text
                .toString()
            val password = findViewById<EditText>(R.id.password_text)
                .text
                .toString()

            Log.d("LoginActivity", "Attempt login with Email : $email")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    // TODO : check the successfully of this
                }

            // TODO : add addOnFailureListener()
        }
    }

    private fun goRegister() {
        val registerButton = findViewById<Button>(R.id.signup_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}