package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.Constants
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val navController = findNavController()

        // Set HomeFragment toolbar
        setHomeToolbar(view)

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
                // Navigate to Likes list
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLikesFragment2())
                return true
            }
            R.id.home_favorite -> {
                // Navigate to wish list
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWhishlistFragment())
                return true
            }
            R.id.button_signout_home -> {
                // Navigate to login page
                Constants.FIREBASE_AUTH.signOut()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setHomeToolbar(view: View){
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
    private fun setFirstMostPlayedInfos(view: View, navController: NavController, bestGame: Game){
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
    private fun setGamesRecycleView(view: View, navController: NavController, games: List<Game>){
        view.findViewById<RecyclerView>(R.id.games_list_view_home).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = GameListAdapter(games.subList(1, games.size), object : OnGameListener {
                override fun onClicked(game: Game, position: Int) {
                    Toast.makeText(
                        activity,
                        "Game $position clicked",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    navController.navigate(HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(game))
                }
            })
        }
    }

    companion object {
        private const val TAG: String = "HomeFragment"
    }
}
