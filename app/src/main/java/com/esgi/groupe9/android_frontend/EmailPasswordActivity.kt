package com.esgi.groupe9.android_frontend

import android.app.Activity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailPasswordActivity : Activity() {

    private lateinit var auth: FirebaseAuth

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in
        val currentUser = auth.currentUser
        // if he's not do reload
        if (currentUser != null) {
            reload();
        }
    }

    private fun reload() {
        // TODO : implement the logic for the user who have been rejected
    }
}
