package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener

class SearchGameFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_games, container, false)

        val games: List<Game> = listOf(); //TODO
        view.findViewById<TextView>(R.id.game_list_title_search).apply{
            text = "Nombres de résultats: ${games.size}";
        }

        view.findViewById<RecyclerView>(R.id.games_list_view_search).apply {
            layoutManager = LinearLayoutManager(activity,)
            adapter = GameListAdapter(games, object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    Toast.makeText(activity, "Game $position clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return view
    }
}