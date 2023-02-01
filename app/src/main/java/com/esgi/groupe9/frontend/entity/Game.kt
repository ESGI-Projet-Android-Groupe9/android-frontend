package com.esgi.groupe9.frontend.entity

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    @SerializedName("_gameId")
    val id: Int? = 0,
    @SerializedName("_name")
    val name: String? = "",
    @SerializedName("_editor")
    val editor: List<String>? = listOf(),
    @SerializedName("_detailedDescription")
    val detailedDescription: String? = "",
    @SerializedName("_aboutTheGame")
    val aboutTheGame: String? = "",
    @SerializedName("_shortDescription")
    val shortDescription: String? = "",
    @SerializedName("_price")
    val price: String? = "",
    @SerializedName("_headerImage")
    val image: String? = "",
    @SerializedName("_background")
    val background: String? = "",
): Parcelable
