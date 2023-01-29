package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
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

        setHomeToolbar(view)
        fillGames(view, navController)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_like -> {
                // TODO Handle like icon click
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLikesFragment2())
                return true
            }
            R.id.home_favorite -> {
                // TODO Handle favorite icon click
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWhishlistFragment())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setHomeToolbar(view: View){
        val homeToolbar = view.findViewById<Toolbar>(R.id.home_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(homeToolbar)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fillGames(view: View, navController: NavController){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val gamesFromApi = withContext(Dispatchers.IO) { apiHelper.getGames() }
                view.findViewById<RecyclerView>(R.id.games_list_view_home).apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = GameListAdapter(gamesFromApi, object : OnGameListener {
                        override fun onClicked(game: Game, position: Int) {
                            /* TODO: the logic to redirect the
                                user to a detail page live right there */
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
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                //e.message?.let { Log.d(activity.TAG, it) }
            }
        }
    }

    companion object {
        private const val TAG: String = "HomeFragment"
    }
}