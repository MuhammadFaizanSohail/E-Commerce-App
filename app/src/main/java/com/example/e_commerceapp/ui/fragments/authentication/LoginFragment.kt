package com.example.e_commerceapp.ui.fragments.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentLoginBinding
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.beGone
import com.example.e_commerceapp.utils.extensions.checkNetwork
import com.example.e_commerceapp.utils.extensions.fragmentBackPress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setBtnClick()
        setObserver()
        activity?.fragmentBackPress {
            setBackPress()
        }
    }

    private fun setBackPress() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginToRegisterFragment(),
            navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, inclusive = true)
                .build()
        )
    }

    private fun setToolbar() = with(binding.toolbar) {
        btnBack.setOnClickListener {
            setBackPress()
        }
        titleTextview.beGone()
    }

    private fun setBtnClick() = with(binding) {
        usernameEditText.addTextChangedListener(emailInputTextWatcher)
        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() = with(binding) {
        val email = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (!validateInputs(email, password)) return

        if (requireContext().checkNetwork()) {
            authViewModel.loginUser(email, password)
        } else {
            showError(R.string.no_internet)
        }
    }

    private val emailInputTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            binding.usernameEditText.error = null
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.loginAuthState.collect {
                    handleAuthState(it) {
                        findNavController().navigate(LoginFragmentDirections.actionLoginToHomeFragment())
                    }
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean = with(binding) {
        if (email.isEmpty()) {
            usernameEditText.error = getString(R.string.enter_email)
            usernameEditText.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            passwordEditText.error = getString(R.string.enter_password)
            passwordEditText.requestFocus()
            return false
        }
        if (password.length < 6) {
            passwordEditText.error = getString(R.string.password_too_short)
            passwordEditText.requestFocus()
            return false
        }
        return true
    }
}
