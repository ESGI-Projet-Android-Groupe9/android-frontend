package com.esgi.groupe9.frontend.entity

data class Game(
    val name: String,
    val editor: String,
    val detailedDescription: String,
    val aboutTheGame: String,
    val shortDescription: String,
    val price: Double,
    val image: String,
    val thumbnail: String,
    val headerImage: String,
    val background: String,
    val backgroundRaw: String
)
