package com.esgi.groupe9.frontend.viewers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.groupe9.frontend.R
import com.esgi.groupe9.frontend.entity.Review

class ReviewListAdapter(
    private val reviews: List<Review>,
    private val listener: OnReviewListener,
) : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun getItemCount(): Int = reviews.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.review_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.updateDay(review)

        holder.itemView.setOnClickListener {
            listener.onClicked(review, position)
        }
    }
}

class ReviewViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val reviewUsername = v.findViewById<TextView>(R.id.review_username)
    private val UserReview = v.findViewById<TextView>(R.id.user_review)
    private val ratingBar = v.findViewById<RatingBar>(R.id.rating_stars_review)

    fun updateDay(review: Review) {
        reviewUsername.text = review.username
        UserReview.text = review.userReview
        ratingBar.rating = review.nbStars.toFloat()
    }
}

interface OnReviewListener {
    fun onClicked(review: Review, position: Int)
}