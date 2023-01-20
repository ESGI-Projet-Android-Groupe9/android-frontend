package com.esgi.groupe9.frontend

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.utils.API
import com.esgi.groupe9.frontend.utils.GameListAdapter
import com.esgi.groupe9.frontend.utils.OnGameListener
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val policy = ThreadPolicy
            .Builder()
            .permitAll()
            .build()
        StrictMode
            .setThreadPolicy(policy)
        
        // REAL DATA
        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3003/")
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
                Log.d("HomeActivity", "Try to launch the Recycle View with the data from the BFF")
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
                Log.d("HomeActivity", "There is an error with the following message : ${e.message}")
            }
        }
    }
}
