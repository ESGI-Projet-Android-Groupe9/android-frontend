package com.esgi.groupe9.frontend.entity

data class Review (
    val username: String,
    val userReview: String,
    val nbStars: Double
)

fun generateFakeReview() = Review(
    username = "Naruto",
    userReview = "Bacon ipsum dolor amet rump doner brisket corned beef tri-tip. Burgdoggen t-bone leberkas, tri-tip bacon beef ribs...",
    nbStars = 4.5
)