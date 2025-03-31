package com.example.e_commerceapp.ui.fragments.productdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.local.entities.Review
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.FragmentProductDetailBinding
import com.example.e_commerceapp.ui.adapters.ReviewAdapter
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.fragmentBackPress
import com.example.e_commerceapp.utils.extensions.loadImage
import com.example.e_commerceapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentProductDetailBinding

    private val reviewAdapter by lazy {
        ReviewAdapter()
    }

    private val args: ProductDetailFragmentArgs by navArgs()

    private var selectedProduct: Product? = null

    private var productId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productId = args.productId
        setupReviewsRecyclerView()
        setProductObserver()
        setToolbar()
        setReviewsObserver()
        setBtnClick()
        getReviews()
        activity?.fragmentBackPress {
            setBackPress()
        }
    }

    private fun setBackPress() {
        findNavController().navigateUp()
    }

    private fun setToolbar() = with(binding.toolbar) {
        btnBack.setOnClickListener {
            setBackPress()
        }
        titleTextview.text = selectedProduct?.title
    }

    private fun setReviewsObserver() {
        lifecycleScope.launch {
            productDetailViewModel.reviews.collect { reviews ->
                reviewAdapter.reviewsList = reviews.toMutableList()
                Log.d("review***", "setProductObserver: reviews: $reviews")
            }
        }
    }

    private fun getReviews() {
        productDetailViewModel.getReviews(productId)
    }

    private fun setProductObserver() {
        lifecycleScope.launch {
            handleApiState(productViewModel.apiState.value) { products ->
                selectedProduct = products.first { it.id == productId }
                displayProductData(selectedProduct)
            }
        }
    }

    private fun displayProductData(product: Product?) = with(binding) {
        productTitle.text = product?.title
        productPrice.text = "$${product?.price}"
        productDescription.text = product?.description
        with(imagePlaceholder) {
            requireContext().loadImage(
                imgProduct = imgProduct,
                imageUrl = product?.thumbnail,
                shimmerLayout = shimmerLayout
            )
        }
    }

    private fun setBtnClick() = with(binding) {
        submitReviewButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val reviewText = reviewEditText.text.toString().trim()
            val rating = ratingBar.rating

            if (name.isEmpty() || reviewText.isEmpty() || rating.toInt() == 0) {
                requireContext().showToast(getString(R.string.please_fill_all_fields))
                return@setOnClickListener
            }

            val newReview = Review(
                productId = productId,
                userName = name,
                comment = reviewText,
                rating = rating
            )
            productDetailViewModel.addReview(newReview)
            nameEditText.text.clear()
            reviewEditText.text.clear()
            ratingBar.rating = 0f
        }
    }

    private fun setupReviewsRecyclerView() = with(binding.recyclerViewReviews) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = reviewAdapter
    }

}