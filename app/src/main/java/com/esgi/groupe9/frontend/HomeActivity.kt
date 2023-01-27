package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.RetrofitBuilder.apiService
import com.esgi.groupe9.frontend.viewers.GameListAdapter
import com.esgi.groupe9.frontend.viewers.OnGameListener
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity() {

    private val apiHelper = ApiHelperImpl(apiService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /* TODO:
            Before launch the activity
            wait for the api response and show a progress dialog
        */

        showTopGames()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun showTopGames() {
        Log.d(TAG, "Try to launch the Recycle View with the data from the BFF")
        GlobalScope.launch(Dispatchers.Main) {
            //val navController = findNavController(R.id.nav_host_fragment)
            try {
                val gamesFromApi = withContext(Dispatchers.IO) { apiHelper.getGames() }
                findViewById<RecyclerView>(R.id.games_list_view_home).apply {
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                    adapter = GameListAdapter(gamesFromApi, object : OnGameListener {
                        override fun onClicked(game: Game, position: Int) {
                            /* TODO: the logic to redirect the
                                user to a detail page live right there */
                            Toast.makeText(
                                this@HomeActivity,
                                "Game $position clicked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val navController = Navigation.findNavController(this@HomeActivity, R.id.nav_host_fragment)
                            navController.navigate(HomeActivityDirections.actionHomeActivityToGameDetailFragment(game))
                        }
                    })
                }
            } catch (e: Exception) {
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    companion object {
        private const val TAG: String = "HomeActivity"
    }
}
