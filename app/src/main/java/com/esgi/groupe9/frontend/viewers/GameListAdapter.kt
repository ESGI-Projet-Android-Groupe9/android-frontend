package com.esgi.groupe9.frontend.viewers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.R
import com.esgi.groupe9.frontend.entity.Game

class GameListAdapter(
    private var games: List<Game>,
    private val listener: OnGameListener,
) : RecyclerView.Adapter<GameViewHolder>(), Filterable {

    override fun getItemCount(): Int = games.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.game_item, parent, false
            )
        )
    }

    fun setFilteredList(foundGames: List<Game>){
        games = foundGames
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.updateGame(game)
        holder.itemView.setOnClickListener {
            listener.onClicked(game, position)
        }
    }

    override fun getFilter(): Filter? {
        return null;
    }
}

class GameViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val gameName = v.findViewById<TextView>(R.id.game_name_item_list)
    private val gameEditor = v.findViewById<TextView>(R.id.game_editor_item_list)
    private val gamePrice = v.findViewById<TextView>(R.id.game_price_item_list)
    private val gameImage =
        v.findViewById<ImageView>(R.id.game_image_item_list)
    private val gameBackgroundImage =
        v.findViewById<ImageView>(R.id.game_background_item_list)

    @SuppressLint("SetTextI18n")
    fun updateGame(game: Game) {
        gameName.text = game.name
        gameEditor.text = game.editor.toString()
        gamePrice.text = "Prix: ${game.price}"
        Glide.with(itemView).load(game.image).into(gameImage)
        Glide.with(itemView).load(game.background).into(gameBackgroundImage)
    }
}

interface OnGameListener {
    fun onClicked(game: Game, position: Int)
}
