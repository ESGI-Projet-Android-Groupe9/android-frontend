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

        setLikesToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val like_toolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)
        like_toolbar.setNavigationOnClickListener{
            findNavController().navigate(LikesFragmentDirections.actionLikesFragment2ToHomeFragment())
        }
    }

    private fun setLikesToolbar(view: View){
        val like_toolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)
        like_toolbar.setNavigationOnClickListener{
            findNavController().navigate(LikesFragmentDirections.actionLikesFragment2ToHomeFragment())
        }
        (requireActivity() as AppCompatActivity).setSupportActionBar(like_toolbar)
    }
}