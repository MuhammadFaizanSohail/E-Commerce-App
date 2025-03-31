package com.example.e_commerceapp.data.local.repository

import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.entities.Review
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepository @Inject constructor(private val reviewDao: ReviewDao) {

    suspend fun addReview(review: Review) {
        reviewDao.addReview(review)
    }

    fun getReviews(productId: Int):  Flow<List<Review>> {
        return reviewDao.getReviews(productId)
    }
}
