package com.esgi.groupe9.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Game
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.Constants
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> {
                Constants.FIREBASE_AUTH.signOut()
                goOnLoginPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun showTopGames() {
        Log.d(TAG, "Try to launch the Recycle View with the data from the BFF")
        GlobalScope.launch(Dispatchers.Main) {
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
                        }
                    })
                }
            } catch (e: Exception) {
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    private fun goOnLoginPage() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        private const val TAG: String = "HomeActivity"
    }
}
