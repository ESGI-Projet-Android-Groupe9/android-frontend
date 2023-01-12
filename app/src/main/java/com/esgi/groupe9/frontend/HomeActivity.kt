package com.esgi.groupe9.frontend

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.utils.GameListAdapter
import com.esgi.groupe9.frontend.utils.OnGameListener

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DUMMY DATA
        val games = listOf(
            Game(
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
            ),
            Game(
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
        )

        setContentView(R.layout.activity_home)

        findViewById<RecyclerView>(R.id.games_list_view_home).apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = GameListAdapter(games, object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    Toast.makeText(this@HomeActivity, "Game $position clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}
