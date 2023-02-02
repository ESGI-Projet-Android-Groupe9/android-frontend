package com.esgi.groupe9.frontend.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review (
    @SerializedName("_reviewId")
    val reviewId: String,
    @SerializedName("_username")
    val username: String,
    @SerializedName("_review")
    val reviewContent: String,
    @SerializedName("_rating")
    val nbStars: Double
): Parcelable