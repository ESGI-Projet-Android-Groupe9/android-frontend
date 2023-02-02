package com.esgi.groupe9.frontend.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userId: String? = "",
    val username: String? = "",
    val email: String? = "",
    val likesList: List<Game>? = listOf(),
    val wishlist: List<Game>? = listOf()
) : Parcelable