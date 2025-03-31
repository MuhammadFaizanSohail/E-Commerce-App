package com.example.e_commerceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val orderDate: Long,
    val totalAmount: Double,
    val address: String,
    val items: List<CartItem>
)
