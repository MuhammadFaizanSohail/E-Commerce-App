package com.example.e_commerceapp.data.model

sealed class ApiState {
    data object Loading : ApiState()
    data class Success(val products: List<Product>) : ApiState()
    data class Error(val errorMessage: String) : ApiState()
}
