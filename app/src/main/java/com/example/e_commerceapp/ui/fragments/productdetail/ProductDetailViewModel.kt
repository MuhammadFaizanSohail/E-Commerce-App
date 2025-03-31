package com.example.e_commerceapp.ui.fragments.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.data.local.entities.Review
import com.example.e_commerceapp.data.local.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val repository: ReviewRepository) : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    fun getReviews(productId: Int) {
        viewModelScope.launch {
            repository.getReviews(productId).collect { reviews ->
                _reviews.value = reviews
            }
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            repository.addReview(review)
        }
    }
}