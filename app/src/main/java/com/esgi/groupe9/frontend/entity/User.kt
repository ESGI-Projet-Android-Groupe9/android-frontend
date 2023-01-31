package com.esgi.groupe9.frontend.entity

data class User(
    val userId: String,
    val username: String,
    val email: String,
    val likesList: List<String>,
    val wishlist: List<String>
)