package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController

class LikesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_likes, container, false)

        // Set LikesFragment toolbar
        setLikesToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val likestoolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)

        likestoolbar.setNavigationOnClickListener{
            // Navigate (Return) to Homefragment
            findNavController().navigate(LikesFragmentDirections.actionLikesFragment2ToHomeFragment())
        }
    }

    // Set LikesFragment toolbar
    private fun setLikesToolbar(view: View){
        val likestoolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(likestoolbar)
    }
}