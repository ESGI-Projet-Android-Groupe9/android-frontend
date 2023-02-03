package com.esgi.groupe9.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener

class WishlistFragment : Fragment() {
    private val args: WishlistFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)
        val games: List<Game>? = args.user?.wishlist

        games?.let { setGamesRecycleView(view, it) }
        // Set WishListFragment toolbar
        setWishToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wishToolbar = view.findViewById<Toolbar>(R.id.wish_toolbar)

        wishToolbar.setNavigationOnClickListener {
            // Navigate (Return) to Homefragment
            findNavController().navigate(WishlistFragmentDirections.actionWhishlistFragmentToHomeFragment())
        }
    }

    // Set WishFragment toolbar
    private fun setWishToolbar(view: View) {
        val wishToolbar = view.findViewById<Toolbar>(R.id.wish_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(wishToolbar)
    }

    // Set Games Wish View
    private fun setGamesRecycleView(view: View, games: List<Game>) {
        val emptyFragmentView = view.findViewById<ConstraintLayout>(R.id.empty_wish_fragment)
        val wishFragmentView = view.findViewById<RecyclerView>(R.id.wish_list_view)

        wishFragmentView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GameListAdapter(games, object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    findNavController().navigate(
                        WishlistFragmentDirections.actionWhishlistFragmentToGameDetailsFragment(
                            game
                        )
                    )
                }
            })
        }

        if (games.isEmpty()) {
            emptyFragmentView.visibility = View.VISIBLE
            wishFragmentView.visibility = View.GONE
        } else {
            emptyFragmentView.visibility = View.GONE
            wishFragmentView.visibility = View.VISIBLE
        }
    }
}