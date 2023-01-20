package com.esgi.groupe9.frontend.utils

import com.esgi.groupe9.frontend.entity.Game
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface API {

    @GET("/game")
    fun getGames(): Deferred<List<Game>>
}
