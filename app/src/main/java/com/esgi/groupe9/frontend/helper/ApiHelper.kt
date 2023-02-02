package com.esgi.groupe9.frontend.helper

import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.Review

interface ApiHelper {

    suspend fun getGames(): List<Game>
    suspend fun getReviews(gameId: Int): List<Review>
}