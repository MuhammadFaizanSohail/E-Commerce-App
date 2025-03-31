package com.example.e_commerceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val userName: String,
    val comment: String,
    val rating: Float
)