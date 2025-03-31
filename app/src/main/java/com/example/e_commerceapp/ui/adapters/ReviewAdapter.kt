package com.example.e_commerceapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.data.local.entities.Review
import com.example.e_commerceapp.databinding.AdapterReviewItemBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviewsList: MutableList<Review> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ReviewViewHolder(private val binding: AdapterReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) = with(binding) {
            userName.text = review.userName
            reviewText.text = review.comment
            ratingBar.rating = review.rating
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterReviewItemBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewsList[position])
    }
}