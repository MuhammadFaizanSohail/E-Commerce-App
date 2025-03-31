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
import androidx.navigation.fragment.findNavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.FragmentRegisterBinding
import com.example.e_commerceapp.ui.fragments.BaseFragment
import com.example.e_commerceapp.utils.extensions.checkNetwork
import com.example.e_commerceapp.utils.extensions.fragmentBackPress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnClick()
        setObserver()
        activity?.fragmentBackPress {
            activity?.finishAffinity()
            exitProcess(0)
        }
    }

    private fun setBtnClick() = with(binding) {
        usernameEditText.addTextChangedListener(emailInputTextWatcher)
        buttonRegister.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (!validateInputs(email, password)) return@setOnClickListener

            if (requireContext().checkNetwork()) {
                authViewModel.registerUser(email, password)
            } else {
                errorDialog?.apply {
                    setErrorMessage(getString(R.string.no_internet))
                    show()
                }
            }
        }
        textViewLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterToLoginFragment())
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
                authViewModel.registerAuthState.collect {
                    handleAuthState(it) {
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterToHomeFragment())
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
