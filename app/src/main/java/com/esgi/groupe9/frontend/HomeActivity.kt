package com.esgi.groupe9.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.generateFakeGame
import com.esgi.groupe9.frontend.entity.generateFakeTitanFallGame
import com.esgi.groupe9.frontend.utils.GameListAdapter
import com.esgi.groupe9.frontend.utils.OnGameListener

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val games = listOf(generateFakeTitanFallGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame(),
            generateFakeGame()
        )

        setContentView(R.layout.activity_home)

        findViewById<RecyclerView>(R.id.games_list_view_home).apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = GameListAdapter(games, object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    Toast.makeText(this@HomeActivity, "Game $position clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
