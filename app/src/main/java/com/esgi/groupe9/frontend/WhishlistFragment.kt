package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController


class WhishlistFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_wishlist, container, false)

        setWhishlistToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val whishlist_toolbar = view.findViewById<Toolbar>(R.id.whishlist_toolbar)
        whishlist_toolbar.setNavigationOnClickListener{
            findNavController().navigate(WhishlistFragmentDirections.actionWhishlistFragmentToHomeFragment())
        }
    }

    private fun setWhishlistToolbar(view: View){
        val whishlist_toolbar = view.findViewById<Toolbar>(R.id.whishlist_toolbar)
        whishlist_toolbar.setNavigationOnClickListener{
            findNavController().navigate(WhishlistFragmentDirections.actionWhishlistFragmentToHomeFragment())
        }
        (requireActivity() as AppCompatActivity).setSupportActionBar(whishlist_toolbar)
    }
}