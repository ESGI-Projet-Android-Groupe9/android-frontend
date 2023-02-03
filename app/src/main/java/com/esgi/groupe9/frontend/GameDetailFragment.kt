package com.esgi.groupe9.frontend

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esgi.groupe9.frontend.entity.Review
import com.esgi.groupe9.frontend.entity.User
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_AUTH
import com.esgi.groupe9.frontend.utils.Constants.FIREBASE_FIRESTORE
import com.esgi.groupe9.frontend.helper.ApiHelperImpl
import com.esgi.groupe9.frontend.utils.DummyData
import com.esgi.groupe9.frontend.utils.RetrofitBuilder
import com.esgi.groupe9.frontend.viewers.OnReviewListener
import com.esgi.groupe9.frontend.viewers.ReviewListAdapter
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.*

class GameDetailFragment : Fragment(R.layout.fragment_game_details) {
    private val apiHelper = ApiHelperImpl(RetrofitBuilder.apiService)
    private val args: GameDetailFragmentArgs by navArgs()
    lateinit var menu: Menu
    private var isPresent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        // Set the GameDetailFragment toolbar
        setGameDetailToolbar(view)
        // Set the game detail content
        setGameDetailsContent(view)
        // Check if the user had already the game in his LikeList.
        checkIfUserHadGameInLikesList()

        return view
    }

    private fun setGameDetailsContent(view: View) {
        val gameDescriptionView = view.findViewById<TextView>(R.id.game_description_detail)
        val gameReviewsRecyclerView =
            view.findViewById<RecyclerView>(R.id.game_reviews_view_in_detail)
        val emptyReviewsText = view.findViewById<TextView>(R.id.empty_review_view_in_detail)
        val game = args.gameItem

        // Set description visible by default
        gameDescriptionView.visibility = View.VISIBLE
        gameReviewsRecyclerView.visibility = View.GONE

        // Set game description in description tab
        gameDescriptionView.text = Html.fromHtml(game.detailedDescription)

        // Request from api and set Reviews in RecycleView
        game.id?.let { setReviewsRecycleView(gameReviewsRecyclerView, emptyReviewsText, it) }

        // Show Description tab
        view.findViewById<Button>(R.id.button_description).setOnClickListener {
            gameReviewsRecyclerView.visibility = View.GONE
            gameDescriptionView.visibility = View.VISIBLE
            view.findViewById<Button>(R.id.button_description)
                .setBackgroundResource(R.drawable.button_description_selected)
            view.findViewById<Button>(R.id.button_reviews)
                .setBackgroundResource(R.drawable.button_reviews_unselected)
        }

        // Show Reviews tab
        view.findViewById<Button>(R.id.button_reviews).setOnClickListener {
            gameDescriptionView.visibility = View.GONE
            gameReviewsRecyclerView.visibility = View.VISIBLE
            view.findViewById<Button>(R.id.button_reviews)
                .setBackgroundResource(R.drawable.button_reviews_selected)
            view.findViewById<Button>(R.id.button_description)
                .setBackgroundResource(R.drawable.button_description_unselected)
        }
    }

    // Request from api and set Reviews in RecycleView
    @OptIn(DelicateCoroutinesApi::class)
    private fun setReviewsRecycleView(gameReviewsRecyclerView: RecyclerView, emptyReviewsText: TextView, gameId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Get the games from the API Request
                val reviews = withContext(Dispatchers.IO) { apiHelper.getReviews(gameId) }

                if (reviews.isEmpty()){
                    // Show empty reviews text
                    emptyReviewsText.visibility = View.VISIBLE
                    gameReviewsRecyclerView.visibility = View.GONE
                } else {
                    // Mask recycleView
                    emptyReviewsText.visibility = View.GONE

                    // Fill Reviews in recycle
                    gameReviewsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = ReviewListAdapter(reviews, object : OnReviewListener {
                            override fun onClicked(review: Review, position: Int) {
                                Toast.makeText(activity, "Review $position clicked", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        // Set header Game in GameDetailFragment.
        setHeaderGame(itemView)
        // Retrieve the toolbar to navigate between fragment.
        val gameDetailToolbar = view?.findViewById<Toolbar>(R.id.game_detail_toolbar)
        gameDetailToolbar?.setNavigationOnClickListener {
            findNavController().navigate(GameDetailFragmentDirections.actionGameDetailFragmentToHomeFragment())
        }
    }

    // Create the different options in the toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_game_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    // TODO : You can't click several times on the
    //  Menu Item Like or Wished of GameDetailFragment check the behaviour
    // Set on Action of onSelect an option
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.game_detail_like -> {
                if (!isPresent) {
                    addGameToLikeList(item)
                } else {
                    removeGameToLikeList(item)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Set the game_detail_toolbar to our current activity
    private fun setGameDetailToolbar(view: View) {
        val gameDetailToolbar = view.findViewById<Toolbar>(R.id.game_detail_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(gameDetailToolbar)
    }

    // Set header Game in GameDetailFragment
    private fun setHeaderGame(itemView: View) {
        val game = args.gameItem

        view?.findViewById<TextView>(R.id.game_name_item_detail)?.text = game.name

        view?.findViewById<TextView>(R.id.game_editor_item_detail)?.text = game.editor.toString()

        val gameItemImageDetail = view?.findViewById<ImageView>(R.id.game_image_item_detail)
        if (gameItemImageDetail != null) {
            Glide.with(itemView).load(game.image).into(gameItemImageDetail)
        }
        val gameItemBackgroundDetail =
            view?.findViewById<ImageView>(R.id.game_background_item_detail)
        if (gameItemBackgroundDetail != null) {
            Glide.with(itemView).load(game.background).into(gameItemBackgroundDetail)
        }
        val gameItemBackgroundHeadDetail =
            view?.findViewById<ImageView>(R.id.game_background_detail)
        if (gameItemBackgroundHeadDetail != null) {
            Glide.with(itemView).load(game.background).into(gameItemBackgroundHeadDetail)
        }
    }

    private fun checkIfUserHadGameInLikesList() {
        val userId = FIREBASE_AUTH.currentUser?.uid.toString()
        val gameId = args.gameItem.id

        FIREBASE_FIRESTORE
            .collection("users")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val userSearched = document.toObject(User::class.java)
                    val likedGameList = userSearched.likesList!!

                    for (likeGame in likedGameList) {
                        if (gameId == likeGame.id) {
                            Log.d(
                                TAG,
                                "User has already liked this game : ${likeGame.id.toString()}"
                            )
                            menu.getItem(0).icon =
                                context?.resources?.getDrawable(R.drawable.like_full)
                            isPresent = true
                        } else {
                            Log.d(
                                TAG,
                                "User hasn't liked this game yet : ${likeGame.id.toString()}"
                            )
                            menu.getItem(0).icon =
                                context?.resources?.getDrawable(R.drawable.like)
                            isPresent = false
                        }
                    }
                }
            }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addGameToLikeList(item: MenuItem) {
        val game = args.gameItem
        val userId = FIREBASE_AUTH.currentUser?.uid.toString()

        FIREBASE_FIRESTORE
            .collection("users")
            .document(userId)
            .update("likesList", FieldValue.arrayUnion(game))
            .addOnSuccessListener {
                item.icon = context?.resources?.getDrawable(R.drawable.like_full)
                item.isEnabled = true
            }
    }

    private fun removeGameToLikeList(item: MenuItem) {
        val game = args.gameItem
        val userId = FIREBASE_AUTH.currentUser?.uid.toString()

        FIREBASE_FIRESTORE
            .collection("users")
            .document(userId)
            .update("likesList", FieldValue.arrayRemove(game))
            .addOnSuccessListener {
                item.icon = context?.resources?.getDrawable(R.drawable.like)
            }
    }

    companion object {
        const val TAG: String = "GameDetailFragment"
    }
}
