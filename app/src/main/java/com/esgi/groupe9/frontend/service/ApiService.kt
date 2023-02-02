package com.esgi.groupe9.frontend.service

import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.Review
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("game/full_games")
    suspend fun getGamesAsync(): List<Game>

    @GET("review/{game_id}")
    suspend fun getReviewsAsync(@Path("game_id") id: Int?): List<Review>
}
