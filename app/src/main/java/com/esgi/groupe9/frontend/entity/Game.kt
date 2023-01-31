package com.esgi.groupe9.frontend.entity

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    @SerializedName("_gameId")
    val id: Int,
    @SerializedName("_name")
    val name: String,
    @SerializedName("_editor")
    val editor: List<String>,
    @SerializedName("_detailedDescription")
    val detailedDescription: String,
    @SerializedName("_aboutTheGame")
    val aboutTheGame: String,
    @SerializedName("_shortDescription")
    val shortDescription: String,
    @SerializedName("_price")
    val price: Number,
    @SerializedName("_headerImage")
    val image: String,
    @SerializedName("_background")
    val background: String,
): Parcelable
