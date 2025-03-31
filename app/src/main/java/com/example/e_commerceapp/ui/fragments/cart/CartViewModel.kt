package com.example.e_commerceapp.ui.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.local.entities.CartItem
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.local.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _cartEvent = MutableSharedFlow<String>()
    val cartEvent: SharedFlow<String> get() = _cartEvent

    init {
        viewModelScope.launch {
            repository.getCartItems().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            repository.addToCart(product)
            _cartEvent.emit("${product.title} has been added to the cart.")
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productId, quantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
