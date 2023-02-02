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
import com.esgi.groupe9.frontend.utils.DummyData
import com.esgi.groupe9.frontend.viewers.OnReviewListener
import com.esgi.groupe9.frontend.viewers.ReviewListAdapter
import com.google.firebase.firestore.FieldValue

class GameDetailFragment : Fragment(R.layout.fragment_game_details) {
    private val args: GameDetailFragmentArgs by navArgs()
    lateinit var menu: Menu

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

        return view
    }

    private fun setGameDetailsContent(view: View) {
        val gameDescriptionView = view.findViewById<TextView>(R.id.game_description_detail)
        val gameReviewsRecyclerView =
            view.findViewById<RecyclerView>(R.id.game_reviews_view_in_detail)
        val reviews = listOf(
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
            DummyData.DUMMY_REVIEW,
        )

        gameDescriptionView.visibility = View.VISIBLE
        gameReviewsRecyclerView.visibility = View.GONE

        gameDescriptionView.text = Html.fromHtml(args.gameItem.detailedDescription)
        gameReviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ReviewListAdapter(reviews, object : OnReviewListener {
                override fun onClicked(review: Review, position: Int) {
                    Toast.makeText(activity, "Review $position clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
        view.findViewById<Button>(R.id.button_description).setOnClickListener {
            gameReviewsRecyclerView.visibility = View.GONE
            gameDescriptionView.visibility = View.VISIBLE
            view.findViewById<Button>(R.id.button_description)
                .setBackgroundResource(R.drawable.button_description_selected)
            view.findViewById<Button>(R.id.button_reviews)
                .setBackgroundResource(R.drawable.button_reviews_unselected)
        }

        view.findViewById<Button>(R.id.button_reviews).setOnClickListener {
            gameDescriptionView.visibility = View.GONE
            gameReviewsRecyclerView.visibility = View.VISIBLE
            view.findViewById<Button>(R.id.button_reviews)
                .setBackgroundResource(R.drawable.button_reviews_selected)
            view.findViewById<Button>(R.id.button_description)
                .setBackgroundResource(R.drawable.button_description_unselected)
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        // Set header Game in GameDetailFragment
        setHeaderGame(itemView)
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

    // Set on Action of onSelect an option
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.game_detail_like -> {
                val game = args.gameItem
                val userId = FIREBASE_AUTH.currentUser?.uid.toString()

                FIREBASE_FIRESTORE
                    .collection("users")
                    .document(userId)
                    .update("likesList", FieldValue.arrayUnion(game))
                    .addOnCompleteListener {
                        item.icon = context?.resources?.getDrawable(R.drawable.like_full)
                    }
            }
            R.id.game_detail_favorite -> {
                // TODO Add action onClick to add this item game in the wishlist of the user
                //  and modify the view to have a filled wishlist icon
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Set the game_detail_toolbar to our current activity
    private fun setGameDetailToolbar(view: View) {
        val gameDetailToolbar = view.findViewById<Toolbar>(R.id.game_detail_toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(gameDetailToolbar)
        checkIfUserHadGame()
    }

    private fun checkIfUserHadGame() {
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
                            // TODO: when the game is found change the drawable with a filled like button
                            Log.d(
                                TAG,
                                "User has already liked this game : ${likeGame.id.toString()}"
                            )
                            menu.getItem(0).icon =
                                context?.resources?.getDrawable(R.drawable.like_full)
                            break
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, it.message.toString())
            }

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

    companion object {
        const val TAG: String = "GameDetailFragment"
    }
}
