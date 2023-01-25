package com.esgi.groupe9.frontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            try {
                val usersFromApi = withContext(Dispatchers.IO) { apiHelper.getGames() }
                findViewById<RecyclerView>(R.id.games_list_view_home).apply {
                    layoutManager = LinearLayoutManager(this@HomeActivity)
                    adapter = GameListAdapter(usersFromApi, object : OnGameListener {
                        override fun onClicked(game: Game, position: Int) {
                            /* TODO: the logic to redirect the
                                user to a detail page live right there */
                            Toast.makeText(
                                this@HomeActivity,
                                "Game $position clicked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })
                }
            } catch (e: Exception) {
                Log.d(TAG, "There is an error with the following message : ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG: String = "HomeActivity"
    }
}
