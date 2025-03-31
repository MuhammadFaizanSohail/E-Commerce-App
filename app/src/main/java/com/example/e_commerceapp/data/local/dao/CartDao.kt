package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.e_commerceapp.data.local.entities.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: CartItem)

    @Query("SELECT * FROM CartItem")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("DELETE FROM CartItem WHERE productId = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query("SELECT * FROM CartItem WHERE productId = :productId LIMIT 1")
    suspend fun getCartItem(productId: Int): CartItem?

    @Query("UPDATE CartItem SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM CartItem")
    suspend fun deleteCart()
}
