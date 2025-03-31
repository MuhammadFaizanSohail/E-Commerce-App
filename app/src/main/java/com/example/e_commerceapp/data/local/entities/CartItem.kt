package com.example.e_commerceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey val id: Int,
    val productId: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    var quantity: Int
)
