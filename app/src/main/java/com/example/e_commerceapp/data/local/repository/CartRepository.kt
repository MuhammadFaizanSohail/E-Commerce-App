package com.example.e_commerceapp.data.local.repository

import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.entities.CartItem
import com.example.e_commerceapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(private val cartDao: CartDao) {

    suspend fun addToCart(product: Product) {
        val existingCartItem = cartDao.getCartItem(product.id)
        if (existingCartItem != null) {
            // If item exists, increase quantity
            val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
            cartDao.updateCartItem(updatedCartItem)
        } else {
            // If not, add a new entry
            val cartItem = CartItem(
                id = product.id,
                productId = product.id,
                name = product.title,
                price = product.price,
                imageUrl = product.thumbnail,
                quantity = 1
            )
            cartDao.addToCart(cartItem)
        }
    }

    fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems()
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.removeFromCart(productId)
    }

    suspend fun updateQuantity(productId: Int, quantity: Int) {
        cartDao.updateQuantity(productId, quantity)
    }

    suspend fun clearCart() {
        cartDao.deleteCart()
    }


}
