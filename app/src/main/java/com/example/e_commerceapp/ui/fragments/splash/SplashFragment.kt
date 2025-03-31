package com.example.e_commerceapp.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.databinding.FragmentSplashBinding
import com.example.e_commerceapp.ui.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (authViewModel.isUserLoggedIn() == true) {
            loadProducts()
            findNavController().navigate(SplashFragmentDirections.actionSplashToHomeFragment())
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashToRegisterFragment())
        }
    }
}