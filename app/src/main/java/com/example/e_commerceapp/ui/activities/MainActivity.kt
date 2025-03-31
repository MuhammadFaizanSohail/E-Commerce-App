package com.example.e_commerceapp.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivityMainBinding
import com.example.e_commerceapp.utils.extensions.beGone
import com.example.e_commerceapp.utils.extensions.beVisible
import com.example.e_commerceapp.utils.extensions.setInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
    }
    private val navController by lazy { navHostFragment?.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setInsets(binding.root)
        setContentView(binding.root)
        setupNavigation()
        navigationBarClickListener()
    }


    private fun setupNavigation() = with(binding) {
        navController?.let { controller ->
            bottomNav.setupWithNavController(controller)
            controller.addOnDestinationChangedListener(this@MainActivity)
        }
    }

    private fun navigationBarClickListener() = with(binding) {
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    navigateToFragment(R.id.homeFragment)
                }

                R.id.menu_cart -> {
                    navigateToFragment(R.id.cartFragment)
                }
            }
            true
        }
    }

    private fun navigateToFragment(destinationFragmentId: Int) = with(binding) {
        if (navController?.currentDestination?.id != destinationFragmentId) {
            navController?.navigate(destinationFragmentId)
            disableNavTemporarily()
        }
    }
    private fun disableNavTemporarily() = with(binding.bottomNav) {
        menu.forEach { it.isEnabled = false }
        Handler(Looper.getMainLooper()).postDelayed({
            menu.forEach { it.isEnabled = true }
        }, 400)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        binding.bottomNav.apply {
            when (destination.id) {
                R.id.homeFragment -> showNav(R.id.menu_home)
                R.id.cartFragment -> showNav(R.id.menu_cart)
                else -> beGone()
            }
        }
    }

    private fun showNav(selectedId: Int) = with(binding.bottomNav) {
        beVisible()
        selectedItemId = selectedId
    }
}

