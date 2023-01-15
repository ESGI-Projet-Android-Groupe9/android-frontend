package com.esgi.groupe9.frontend.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class ServerResponse (
    @SerializedName("error")
    val error: String?,
    @SerializedName("response")
    val response: Response?
    ) {
    data class Response(
        @SerializedName("name")
        val name: String,
        @SerializedName("editor")
        val editor: String,
        @SerializedName("detailedDescription")
        val detailedDescription: String,
        @SerializedName("aboutTheGame")
        val aboutTheGame: String,
        @SerializedName("shortDescription")
        val shortDescription: String,
        @SerializedName("price")
        val price: Double,
        @SerializedName("image")
        val image: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("headerImage")
        val headerImage: String,
        @SerializedName("background")
        val background: String,
        @SerializedName("backgroundRaw")
        val backgroundRaw: String
    )

    fun toGame(): Game = response?.let { response ->
        return Game(
            name = response.name,
            editor = response.editor,
            detailedDescription = response.detailedDescription,
            aboutTheGame = response.aboutTheGame,
            shortDescription = response.shortDescription,
            price = response.price,
            image = response.image,
            thumbnail = response.thumbnail,
            headerImage = response.headerImage,
            background = response.background,
            backgroundRaw = response.backgroundRaw
        )
    } ?: throw Exception("Unable to parse the game")
}
