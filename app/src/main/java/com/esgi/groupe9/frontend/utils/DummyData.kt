package com.esgi.groupe9.frontend.utils

import com.esgi.groupe9.frontend.entity.Game

object DummyData {

    val DUMMY_GAME: Game =
        Game(
            id = 1,
            name = "Titan Fall",
            editor = listOf("Nom de l'éditeur"),
            detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
            price = 10.00,
            image = "drawable/destiny2_ps4.png",
            background = "drawable/destiny_2_background.jpeg"
        )
}