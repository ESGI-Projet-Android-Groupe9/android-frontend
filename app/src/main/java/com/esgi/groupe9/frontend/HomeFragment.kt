package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val navController = findNavController()
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

        return view
    }

    companion object {
        private const val TAG: String = "HomeFragment"
    }
}