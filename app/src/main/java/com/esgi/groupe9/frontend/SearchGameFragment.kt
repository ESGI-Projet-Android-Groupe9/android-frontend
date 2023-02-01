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
import com.esgi.groupe9.frontend.utils.DummyData
import com.esgi.groupe9.frontend.utils.RetrofitBuilder
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Get the games from the API Request
                val games = withContext(Dispatchers.IO) { apiHelper.getGames() }

                view.findViewById<TextView>(R.id.game_list_title_search).apply{
                    text = "Nombres de résultats: ${games.size}";
                }

                val searchRecycleView = view.findViewById<RecyclerView>(R.id.games_list_view_search)
                searchRecycleView.layoutManager = LinearLayoutManager(activity,)
                var gameAdapter = GameListAdapter(games, object : OnGameListener {
                    override fun onClicked(game: Game, position: Int) {
                        Toast.makeText(activity, "Game $position clicked", Toast.LENGTH_SHORT).show()
                    }
                })
                searchRecycleView.adapter = gameAdapter

                val searchView = view.findViewById<SearchView>(R.id.search_field_search_fragment)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false;
                    }

                    override fun onQueryTextChange(gameName: String): Boolean {
                        val query = gameName
                        if (query != null) {
                            val filteredList = ArrayList<Game>()
                            for (i in games) {
                                if (i.name.contains(query)) {
                                    filteredList.add(i)
                                }
                            }
                            if (filteredList.isEmpty()) {
                                Toast.makeText(activity, "No Games found", Toast.LENGTH_SHORT).show()
                            } else {
                                gameAdapter.setFilteredList(filteredList)
                                searchRecycleView.adapter = gameAdapter
                            }
                            view.findViewById<TextView>(R.id.game_list_title_search).apply{
                                text = "Nombres de résultats: ${filteredList.size}";
                            }
                        }
                        return true
                    }
                })
            } catch (e: Exception) {
                Log.d(HomeFragment.TAG, e.toString())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchToolbar = view.findViewById<Toolbar>(R.id.search_toolbar)
        searchToolbar?.setNavigationOnClickListener {
            findNavController().navigate(SearchGameFragmentDirections.actionSearchGameFragmentToHomeFragment())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val TAG: String = "SearchGameFragment"
    }
}