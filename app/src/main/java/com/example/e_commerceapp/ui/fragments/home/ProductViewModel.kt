package com.example.e_commerceapp.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.model.ApiState
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.data.remote.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState: StateFlow<ApiState> = _apiState.asStateFlow()
    private var productsList: List<Product> = emptyList()

    private val _searchQueryProducts = MutableStateFlow("")
    val searchQueryProducts: StateFlow<String> = _searchQueryProducts.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            _apiState.emit(ApiState.Loading)
            try {
                val products = repository.getProducts()
                productsList = products
                filterProducts()
            } catch (e: Exception) {
                _apiState.emit(ApiState.Error(e.message ?: "Failed to fetch news"))
            }
        }
    }

    fun updateSearchQuery(string: String) {
        viewModelScope.launch {
            _searchQueryProducts.value = string
            filterProducts()
        }
    }

    private fun filterProducts() {
        val query = searchQueryProducts.value
        val filteredList = if (query.isBlank()) productsList else productsList.filter { it.title.contains(query, ignoreCase = true) }
        _apiState.value = ApiState.Success(filteredList)
    }
}