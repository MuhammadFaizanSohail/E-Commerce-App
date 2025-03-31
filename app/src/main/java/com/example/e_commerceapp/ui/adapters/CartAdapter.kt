package com.example.e_commerceapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.data.local.entities.CartItem
import com.example.e_commerceapp.databinding.AdapterItemCartBinding
import com.example.e_commerceapp.utils.extensions.loadImage

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    var cartList : MutableList<CartItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onQuantityChanged : ((Int, Int) -> Unit) ?= null
    var onRemove : ((Int) -> Unit) ?= null

    inner class CartViewHolder(private val binding: AdapterItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) = with(binding) {
            tvProductName.text = item.name
            tvProductPrice.text = "$${item.price}"
            tvQuantity.text = item.quantity.toString()

            with(imagePlaceholder) {
                itemView.context.loadImage(
                    imgProduct = imgProduct,
                    imageUrl = item.imageUrl,
                    shimmerLayout = shimmerLayout
                )
            }

            btnIncrease.setOnClickListener {
                onQuantityChanged?.invoke(item.productId, item.quantity + 1)
            }

            btnDecrease.setOnClickListener {
                if (item.quantity > 1) {
                    onQuantityChanged?.invoke(item.productId, item.quantity - 1)
                } else {
                    onRemove?.invoke(item.productId)
                }
            }

            btnRemove.setOnClickListener {
                onRemove?.invoke(item.productId)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }



}