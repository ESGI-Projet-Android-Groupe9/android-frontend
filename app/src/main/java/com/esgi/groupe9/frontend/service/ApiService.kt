package com.esgi.groupe9.frontend.service

import com.esgi.groupe9.frontend.entity.Game
import retrofit2.http.GET

interface ApiService {

    @GET("game")
    suspend fun getGamesAsync(): List<Game>
}
