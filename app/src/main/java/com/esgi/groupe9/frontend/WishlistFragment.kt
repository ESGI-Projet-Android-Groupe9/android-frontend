package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController


class WishlistFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_wishlist, container, false)

        // Set WishlistFragment toolbar
        setWishlistToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wishlist_toolbar = view.findViewById<Toolbar>(R.id.whishlist_toolbar)
        wishlist_toolbar.setNavigationOnClickListener{
            // Navigate (Return) to HomeFragment
            findNavController().navigate(WishlistFragmentDirections.actionWhishlistFragmentToHomeFragment())
        }
    }

    // Set WishlistFragment toolbar
    private fun setWishlistToolbar(view: View){
        val wishlist_toolbar = view.findViewById<Toolbar>(R.id.whishlist_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(wishlist_toolbar)
    }
}