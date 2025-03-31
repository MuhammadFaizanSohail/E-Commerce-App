package com.example.e_commerceapp.data.remote.model

import com.example.e_commerceapp.data.model.Product

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
