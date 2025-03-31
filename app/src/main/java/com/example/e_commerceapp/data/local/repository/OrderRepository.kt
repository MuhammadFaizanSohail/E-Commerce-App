package com.example.e_commerceapp.data.local.repository

import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.entities.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderDao: OrderDao) {
    suspend fun placeOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    fun getOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }
}
