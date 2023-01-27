package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class GameDescriptionFragment : Fragment() {
    //private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_game_description,
            container,
            false
        )
        //val game = args.gameItem
        //view.findViewById<TextView>(R.id.game_description_detail).text = game.detailedDescription

        return view
    }
}