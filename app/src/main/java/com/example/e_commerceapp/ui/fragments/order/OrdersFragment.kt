package com.example.e_commerceapp.ui.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentOrdersBinding
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setPrice()
        setBtnClick()
    }

    private fun setPrice() = with(binding) {
        val items = cartViewModel.cartItems.value
        val totalAmount = items.sumOf { it.price * it.quantity }
        priceTv.text = "$$totalAmount"
    }

    private fun setBtnClick() {
        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun setToolbar() = with(binding.toolbar) {
        btnBack.setOnClickListener {
            setBackPress()
        }
        titleTextview.text = getString(R.string.order)
    }

    private fun setBackPress() {
        findNavController().navigateUp()
    }

    private fun placeOrder() = with(binding) {
        lifecycleScope.launch {
            val address = addressEditText.text.toString().trim()
            if (address.isNotEmpty()) {
                val items = cartViewModel.cartItems.value
                if (items.isNotEmpty()) {
                    val totalAmount = items.sumOf { it.price * it.quantity }
                    priceTv.text = "$$totalAmount"
                    orderViewModel.placeOrder(
                        items = items,
                        totalAmount = totalAmount,
                        address = address

                    )
                    cartViewModel.clearCart()
                    requireContext().showToast(getString(R.string.order_placed_successfully))
                    findNavController().navigate(OrdersFragmentDirections.actionOrderToHomeFragment())
                } else {
                    requireContext().showToast(getString(R.string.your_cart_is_empty))
                }
            } else {
                addressEditText.error = getString(R.string.enter_your_address)
            }
        }
    }

}