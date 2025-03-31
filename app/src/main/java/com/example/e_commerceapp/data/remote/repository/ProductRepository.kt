package com.example.e_commerceapp.data.remote.repository

import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.remote.api.ProductApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val api: ProductApi) {
    suspend fun getProducts(): List<Product> = api.getProducts().products
}