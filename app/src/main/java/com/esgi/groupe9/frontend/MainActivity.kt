package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.util.Log.WARN
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        setContentView(R.layout.login)

        val login_button = findViewById<Button>(R.id.login_button)

        login_button.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_text).text.toString()
            val password = findViewById<EditText>(R.id.password_text).text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("EmailPassword", "signInWithEmail:success")
                        Toast.makeText(
                            baseContext, "Authentication success.",
                            Toast.LENGTH_LONG
                        ).show()
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("EmailPassword", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }
}
