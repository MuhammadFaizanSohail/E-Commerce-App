package com.example.e_commerceapp.ui.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentCartBinding
import com.example.e_commerceapp.ui.adapters.CartAdapter
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.beGone
import com.example.e_commerceapp.utils.extensions.beVisible
import com.example.e_commerceapp.utils.extensions.fragmentBackPress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private lateinit var binding: FragmentCartBinding

    private val cartAdapter by lazy {
        CartAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setCartRecyclerView()
        setObserver()
        setBtnClicks()
        activity?.fragmentBackPress {
            setBackPress()
        }
    }

    private fun setBtnClicks() {
        with(cartAdapter) {
            onRemove = { productId ->
                cartViewModel.removeFromCart(productId)
            }
            onQuantityChanged = { productId, quantity ->
                cartViewModel.updateQuantity(productId = productId, quantity = quantity)
            }
        }
        binding.btnReviewPayment.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartToOrdersFragment())
        }
    }


    private fun setObserver() = with(binding) {
        lifecycleScope.launch {
            cartViewModel.cartItems.collect { items ->
                cartAdapter.cartList = items.toMutableList()
                val totalPrice = items.sumOf { it.price * it.quantity }
                tvTotalPrice.text = "Total: $${String.format(Locale.US, "%.2f", totalPrice)}"
                if (items.isEmpty()) {
                    noItemsLayout.beVisible()
                    itemFoundLayout.beGone()
                } else {
                    noItemsLayout.beGone()
                    itemFoundLayout.beVisible()
                }
            }
        }
    }

    private fun setCartRecyclerView() = with(binding.recyclerViewCart) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = cartAdapter
    }

    private fun setBackPress() {
        findNavController().navigateUp()
    }

    private fun setToolbar() = with(binding.toolbar) {
        btnBack.setOnClickListener {
            setBackPress()
        }
        titleTextview.text = getString(R.string.cart)
    }

}