package com.esgi.groupe9.frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.entity.Review
import com.esgi.groupe9.frontend.entity.generateFakeReview
import com.esgi.groupe9.frontend.viewers.OnReviewListener
import com.esgi.groupe9.frontend.viewers.ReviewListAdapter


class GameReviewsFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO decomment following 2 lines
//        val bundle = arguments
//        val game = bundle?.getParcelable<Game>("gameItem")

        // TODO Get review from the gameId of Parcelable 'gameItem'
        val reviews = listOf(
            generateFakeReview(),
            generateFakeReview(),
            generateFakeReview(),
            generateFakeReview(),
            generateFakeReview(),
        )
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)
        rootView.findViewById<RecyclerView>(R.id.games_reviews_list_view_fragment).apply {
            layoutManager = LinearLayoutManager(activity ,)
            adapter = ReviewListAdapter(reviews, object : OnReviewListener {
                override fun onClicked(review: Review, position: Int) {
                    Toast.makeText(activity, "Game $position clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
        return rootView
    }
}