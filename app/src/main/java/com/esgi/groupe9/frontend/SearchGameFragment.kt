package com.esgi.groupe9.frontend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.RetrofitBuilder
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class SearchGameFragment : Fragment() {
    private val apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_games, container, false)

        // Set the Search game View
        setSearchGamesView(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        searchToolbar?.setNavigationOnClickListener {
            findNavController().navigate(SearchGameFragmentDirections.actionSearchGameFragmentToHomeFragment())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    // Set the Search game View
    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun setSearchGamesView(view: View){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Get the games from the API Request
                val games = withContext(Dispatchers.IO) { apiHelper.getGames() }

                // Set the number of games
                view.findViewById<TextView>(R.id.game_list_title_search).apply{
                    text = "Nombres de résultats: ${games.size}";
                }

                // Set the preview games
                val searchRecycleView = view.findViewById<RecyclerView>(R.id.games_list_view_search)
                searchRecycleView.layoutManager = LinearLayoutManager(activity,)
                val gameAdapter = GameListAdapter(games, object : OnGameListener {
                    override fun onClicked(game: Game, position: Int) {
                        // Navigate the gameDetailFragment
                        findNavController().navigate(SearchGameFragmentDirections.actionSearchGameFragmentToGameDetailFragment(game))
                    }
                })
                searchRecycleView.adapter = gameAdapter

                // Set the search field filter
                val searchView = view.findViewById<SearchView>(R.id.search_field_search_fragment)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false;
                    }

                    override fun onQueryTextChange(gameName: String): Boolean {
                        val filteredList = ArrayList<Game>()
                        // Search the typed game name in all the games
                        for (i in games) {
                            // Add game in the found games
                            if (i.name.contains(gameName)) {
                                filteredList.add(i)
                            }
                        }

                        if (filteredList.isEmpty()) {
                            Toast.makeText(activity, "No Games found", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // Set the adapter with the found games
                            gameAdapter.setFilteredList(filteredList)

                            // Update the recycle view
                            searchRecycleView.adapter = gameAdapter
                        }
                        // Update the number of found games
                        view.findViewById<TextView>(R.id.game_list_title_search).apply {
                            text = "Nombres de résultats: ${filteredList.size}";
                        }
                        return true
                    }
                })
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        const val TAG: String = "SearchGameFragment"
    }
}