package com.esgi.groupe9.frontend.helper

import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.service.ApiService

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getGames(): List<Game> = apiService.getGamesAsync()
}