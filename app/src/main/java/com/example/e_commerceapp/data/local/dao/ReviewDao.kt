package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerceapp.data.local.entities.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReview(review: Review)

    @Query("SELECT * FROM reviews WHERE productId = :productId ORDER BY id DESC")
    fun getReviews(productId: Int): Flow<List<Review>>
}
