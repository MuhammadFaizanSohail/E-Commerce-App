package com.example.e_commerceapp.data.remote.api


import com.example.e_commerceapp.data.remote.model.ProductResponse
import retrofit2.http.GET


interface ProductApi {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}
