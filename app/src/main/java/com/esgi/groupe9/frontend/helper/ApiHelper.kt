package com.esgi.groupe9.frontend.helper

import com.esgi.groupe9.frontend.entity.Game

interface ApiHelper {

    suspend fun getGames(): List<Game>
}