package com.example.e_commerceapp.ui.fragments.authentication

import androidx.lifecycle.ViewModel
import com.example.e_commerceapp.data.model.AuthState
import com.example.e_commerceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _registerAuthState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerAuthState: StateFlow<AuthState> = _registerAuthState.asStateFlow()

    private val _loginAuthState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginAuthState: StateFlow<AuthState> = _loginAuthState.asStateFlow()

    fun resetLoginState() {
        _registerAuthState.value = AuthState.Idle
        _loginAuthState.value = AuthState.Idle
    }

    fun registerUser(email: String, password: String) {
        _registerAuthState.value = AuthState.Loading
        repository.registerUser(email, password) { isSuccess ->
            if (isSuccess) {
                _registerAuthState.value = AuthState.Success("Registration successful!")
            } else {
                _registerAuthState.value = AuthState.Error("Registration failed. Please try again.")
            }
        }
    }

    // Function to login user
    fun loginUser(email: String, password: String) {
        _loginAuthState.value = AuthState.Loading
        repository.loginUser(email, password) { isSuccess ->
            if (isSuccess) {
                _loginAuthState.value = AuthState.Success("Login successful!")
            } else {
                _loginAuthState.value = AuthState.Error("Invalid email or password.")
            }
        }
    }

    fun logoutUser() {
        repository.logoutUser()
    }

    fun isUserLoggedIn() = repository.isUserLoggedIn()

    fun getUserEmail(): String? {
        return repository.getUserEmail()
    }
}