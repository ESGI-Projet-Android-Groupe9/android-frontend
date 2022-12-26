package com.esgi.groupe9.android_frontend

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // Method to disconnect the user
    private fun signOut() {
        Firebase.auth.signOut()
    }
}
