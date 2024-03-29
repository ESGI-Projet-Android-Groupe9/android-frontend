package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.entity.User
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_AUTH
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_FIRESTORE
import com.esgi.groupe9.frontend.utils.RetrofitBuilder
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener
import kotlinx.coroutines.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val navController = findNavController()

        // Set HomeFragment toolbar
        setHomeToolbar(view)

        // Set search view of HomeFragment
        setOnSearchFieldClick(view, navController)

        // Set HomeFragment Games RecycleView
        setHomeGameRecycleView(view, navController)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_like -> {
                var userFromLikesList: User? = null
                // Get User from the Database to send it to likes list to retrieve games
                FIREBASE_FIRESTORE.collection("users")
                    .whereEqualTo("userId", FIREBASE_AUTH.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            userFromLikesList = document.toObject(User::class.java)
                        }
                        // Navigate to Likes List and pass the user to retrieve info in the likes list page
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToLikesFragment2(
                                userFromLikesList
                            )
                        )
                    }
                    .addOnFailureListener {
                        Log.d(TAG, it.message.toString())
                    }
            }
            R.id.home_favorite -> {
                var userFromWishList: User? = null
                // Get User from the Database to send it to likes list to retrieve games
                FIREBASE_FIRESTORE.collection("users")
                    .whereEqualTo("userId", FIREBASE_AUTH.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            userFromWishList = document.toObject(User::class.java)
                        }
                        // Navigate to Likes List and pass the user to retrieve info in the likes list page
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToWhishlistFragment(
                                userFromWishList
                            )
                        )
                    }
                    .addOnFailureListener {
                        Log.d(TAG, it.message.toString())
                    }
            }
            R.id.button_signout_home -> {
                // Navigate to login page
                FIREBASE_AUTH.signOut()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setHomeToolbar(view: View) {
        val homeToolbar = view.findViewById<Toolbar>(R.id.home_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(homeToolbar)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setHomeGameRecycleView(view: View, navController: NavController){
        GlobalScope.launch(Dispatchers.Main) {
            view.findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE

            try {
                // Get the games from the API Request
                val gamesFromApi = withContext(Dispatchers.IO) { apiHelper.getGames() }

                // Get the first Most played game
                val bestGame = gamesFromApi[0]

                view.findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                
                // Fill the first Most played game information in the top of the home page
                setFirstMostPlayedInfos(view, navController, bestGame)

                // Set Games Recycle View
                setGamesRecycleView(view, navController, gamesFromApi)
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    // Fill the first Most played game information in the top of the home page
    private fun setFirstMostPlayedInfos(view: View, navController: NavController, bestGame: Game) {
        view.findViewById<TextView>(R.id.game_name_home)?.apply {
            text = bestGame.name
        }
        view.findViewById<TextView>(R.id.game_short_description_home)?.apply {
            text = bestGame.shortDescription
        }
        val bestGameImageHome = view.findViewById<ImageView>(R.id.best_game_image_home)
        if (bestGameImageHome != null) {
            Glide.with(view).load(bestGame.image).into(bestGameImageHome)
        }
        val bestGameBackgroundHome = view.findViewById<ImageView>(R.id.best_image_background_home)
        if (bestGameBackgroundHome != null) {
            Glide.with(view).load(bestGame.background).into(bestGameBackgroundHome)
        }

        view.findViewById<Button>(R.id.know_more)?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(bestGame)
            navController.navigate(action)
        }
    }

    // Set Games Recycle View
    private fun setGamesRecycleView(view: View, navController: NavController, games: List<Game>) {
        view.findViewById<RecyclerView>(R.id.games_list_view_home).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GameListAdapter(games.subList(1, games.size), object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    navController.navigate(
                        HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(
                            game
                        )
                    )
                }
            })
        }
    }

    // Navigate to SearchGameFragment
    private fun setOnSearchFieldClick(view: View, navController: NavController){
        val searchField = view.findViewById<TextView>(R.id.search_field)
        searchField.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToSearchGameFragment())
        }
    }

    companion object {
        const val TAG: String = "HomeFragment"
    }
}
