package com.esgi.groupe9.frontend.utils

import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.Review

object DummyData {

    val DUMMY_GAME =
        Game(
            id = 1,
            name = "Titan Fall",
            editor = listOf("Nom de l'éditeur"),
            detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            price = "10.00",
            image = "https://cdn.cloudflare.steamstatic.com/steam/apps/730/hero_capsule.jpg",
            background = "https://cdn.cloudflare.steamstatic.com/steam/apps/730/header.jpg"
        )

    val DUMMY_REVIEW = Review(
        username = "Naruto",
        userReview = "Bacon ipsum dolor amet rump doner brisket corned beef tri-tip. Burgdoggen t-bone leberkas, tri-tip bacon beef ribs...",
        nbStars = 4.5
    )
}