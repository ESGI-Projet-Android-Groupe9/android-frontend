package com.esgi.groupe9.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

class LikesFragment : Fragment() {
    private val args: LikesFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_likes, container, false)
        val games: List<Game>? = args.user?.likesList

        games?.let { setGamesRecycleView(view, it) }
        // Set LikesFragment toolbar
        setLikesToolbar(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val likestoolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)

        likestoolbar.setNavigationOnClickListener {
            // Navigate (Return) to Homefragment
            findNavController().navigate(LikesFragmentDirections.actionLikesFragmentToHomeFragment())
        }
    }

    // Set LikesFragment toolbar
    private fun setLikesToolbar(view: View) {
        val likestoolbar = view.findViewById<Toolbar>(R.id.likes_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(likestoolbar)
    }

    // Set Games Likes View
    private fun setGamesRecycleView(view: View, games: List<Game>) {
        val emptyFragmentView = view.findViewById<ConstraintLayout>(R.id.empty_likes_fragment)
        val likesFragmentView = view.findViewById<RecyclerView>(R.id.likes_list_view)

        likesFragmentView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GameListAdapter(games, object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    findNavController().navigate(
                        LikesFragmentDirections.actionLikesFragmentToGameDetailsFragment(
                            game
                        )
                    )
                }
            })
        }

        if (games.isEmpty()) {
            emptyFragmentView.visibility = View.VISIBLE
            likesFragmentView.visibility = View.GONE
        } else {
            emptyFragmentView.visibility = View.GONE
            likesFragmentView.visibility = View.VISIBLE
        }
    }
}