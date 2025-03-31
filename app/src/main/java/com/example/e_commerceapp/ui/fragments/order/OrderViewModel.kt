package com.example.e_commerceapp.ui.fragments.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.local.entities.CartItem
import com.example.e_commerceapp.data.local.entities.Order
import com.example.e_commerceapp.data.local.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository) : ViewModel() {

    val orders = repository.getOrders().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun placeOrder(items: List<CartItem>, totalAmount: Double, address: String) {
        viewModelScope.launch {

            val order = Order(
                orderDate = System.currentTimeMillis(),
                totalAmount = totalAmount,
                items = items,
                address = address
            )
            repository.placeOrder(order)


        }
    }


}
