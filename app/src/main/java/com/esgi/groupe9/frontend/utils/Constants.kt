package com.esgi.groupe9.frontend.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Constants {

    val FIREBASE_AUTH = FirebaseAuth.getInstance()
    val FIREBASE_FIRESTORE = Firebase.firestore
}