package com.example.e_commerceapp.ui.fragments.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.example.e_commerceapp.ui.adapters.ProductAdapter
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.fragmentBackPress
import com.example.e_commerceapp.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val productAdapter by lazy {
        ProductAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setupDrawer()
        setupSearchView()
        setupRecyclerView()
        setApiObserver()
        setCartObserver()
        setBtnClicks()
        setBackPress()
    }

    private fun setCartObserver() {
        lifecycleScope.launch {
            cartViewModel.cartEvent.collect { message ->
                requireContext().showToast(message)
            }
        }
    }

    private fun setupSearchView() = with(binding.searchView) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let { query ->
                    productViewModel.updateSearchQuery(query.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun setBackPress() {
        activity?.fragmentBackPress {
            exitDialog?.show()
        }
    }

    private fun setToolbar() = with(binding.toolbar) {
        btnBack.setImageResource(R.drawable.menu_icon)
        titleTextview.text = getString(R.string.app_name)
    }

    private fun setBtnClicks() = with(binding) {
        productAdapter.apply {
            onItemClick = { productId ->
                val action =
                    HomeFragmentDirections.actionHomeToProductDetailFragment(productId = productId)
                findNavController().navigate(action)
            }
            onAddToCartClick = { product ->
                cartViewModel.addToCart(product)
            }
        }
        toolbar.btnBack.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupDrawer() = with(binding.navigationView) {
        val headerView = getHeaderView(0)
        val userEmailTextView = headerView.findViewById<TextView>(R.id.userEmail)
        val userEmail = authViewModel.getUserEmail()
        userEmailTextView.text = userEmail

        val switchDarkMode = menu.findItem(R.id.nav_dark_mode).actionView as SwitchCompat
        switchDarkMode.isChecked = sharedPreferenceViewModel.isDarkModeEnabled()
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPreferenceViewModel.setDarkMode(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                sharedPreferenceViewModel.setDarkMode(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    logoutUser()
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateTo(directions: NavDirections) {
        findNavController().navigate(
            directions, navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = true)
                .build()
        )
    }

    private fun logoutUser() {
        authViewModel.logoutUser()
        navigateTo(HomeFragmentDirections.actionHomeToLoginFragment())
    }

    private fun setApiObserver() {
        lifecycleScope.launch {
            productViewModel.apiState.collect {
                handleApiState(it) { products ->
                    productAdapter.productsList = products
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }
}