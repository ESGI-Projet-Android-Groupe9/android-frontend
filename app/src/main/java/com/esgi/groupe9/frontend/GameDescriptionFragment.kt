package com.esgi.groupe9.frontend

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.esgi.groupe9.frontend.entity.Game

class GameDescriptionFragment : Fragment() {
    lateinit var gameDescription: String;

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        view.findViewById<TextView>(R.id.game_description_detail).text = gameDescription

        return view
    }

}