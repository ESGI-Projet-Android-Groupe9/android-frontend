package com.esgi.groupe9.frontend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.utils.API
import com.esgi.groupe9.frontend.utils.GameListAdapter
import com.esgi.groupe9.frontend.utils.OnGameListener
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        
        // REAL DATA
        val api = Retrofit.Builder()
            .baseUrl("http://localhost:3003/game")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .addCallAdapterFactory(
                CoroutineCallAdapterFactory()
            )
            .build()
            .create(API::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val games = withContext(Dispatchers.IO)
                { api.getGames().await() }
                findViewById<RecyclerView>(R.id.games_list_view_home).apply {
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                    adapter = GameListAdapter(games, object : OnGameListener {
                        override fun onClicked(game: Game, position: Int) {
                            Toast.makeText(
                                this@HomeActivity,
                                "Game $position clicked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
