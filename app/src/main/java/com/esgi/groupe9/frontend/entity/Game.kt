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

fun generateFakeGame() = Game(
    name = "Destiny 2",
    editor = "Nom de l'éditeur",
    detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    price = 10.00,
    image = "drawable/destiny2_ps4.png",
    thumbnail = "drawable/destiny2_ps4.png",
    backgroundRaw = "drawable/destiny2_ps4.png",
    background = "drawable/destiny_2_full_image.png",
    headerImage = "drawable/man_hunter.png"
)
fun generateFakeBattlefieldGame() = Game(
    name = "BattleField 1",
    editor = "Nom de l'éditeur",
    detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    price = 10.00,
    image = "",
    thumbnail = "",
    backgroundRaw = "drawable/battlefield1_ps4.png",
    background = "drawable/battlefield_background.png",
    headerImage = "drawable/man_hunter.png"
)
fun generateFakeTitanFallGame() = Game(
    name = "Titan Fall",
    editor = "Nom de l'éditeur",
    detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
    price = 10.00,
    image = "drawable/titanfall_2_ps4.png",
    thumbnail = "drawable/titanfall_2_ps4.png",
    backgroundRaw = "drawable/titanfall_2_ps4.png",
    background = "drawable/man_titan_image.png",
    headerImage = "drawable/man_titan_image.png"
)

//fun generateFakeGames() = Game(
//    name = "Nom du jeu",
//    editor = "Nom de l'éditeur",
//    detailedDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
//    aboutTheGame = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
//    shortDescription = "Lorem ipsum dolor sit amet. Et dolor culpa \nsit minima quia est nihil fuga sit facilis perspiciatis \nid perferendis vitae qui sunt modi eum excepturi accusantium.",
//    price = 10.00,
//    image = R.drawable.destiny2_ps4,
//    thumbnail = R.drawable.destiny_2_full_image,
//    headerImage = R.drawable.destiny_2_background
//)
