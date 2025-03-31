package com.example.e_commerceapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.databinding.AdapterProductItemBinding
import com.example.e_commerceapp.utils.extensions.loadImage

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var productsList: MutableList<Product> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClick : ((Int) -> Unit) ?= null
    var onAddToCartClick : ((Product) -> Unit) ?= null

    inner class ProductViewHolder(private val binding: AdapterProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            productTitle.text = product.title
            productPrice.text = "$${product.price}"
            with(imagePlaceholder) {
                itemView.context.loadImage(
                    imgProduct = imgProduct,
                    imageUrl = product.thumbnail,
                    shimmerLayout = shimmerLayout
                )
            }
            itemView.setOnClickListener {
                onItemClick?.invoke(product.id)
            }
            addToCartButton.setOnClickListener {
                onAddToCartClick?.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productsList[position])
    }
}