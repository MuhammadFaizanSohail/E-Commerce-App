package com.example.e_commerceapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.e_commerceapp.data.model.ApiState
import com.example.e_commerceapp.data.model.AuthState
import com.example.e_commerceapp.data.model.Product
import com.example.e_commerceapp.ui.component.dialog.ErrorDialog
import com.example.e_commerceapp.ui.component.dialog.ExitDialog
import com.example.e_commerceapp.ui.component.dialog.ProgressDialog
import com.example.e_commerceapp.ui.fragments.authentication.AuthViewModel
import com.example.e_commerceapp.ui.fragments.cart.CartViewModel
import com.example.e_commerceapp.ui.viewmodel.SharedPreferenceViewModel
import com.example.e_commerceapp.ui.fragments.productdetail.ProductDetailViewModel
import com.example.e_commerceapp.ui.fragments.home.ProductViewModel
import com.example.e_commerceapp.ui.fragments.order.OrderViewModel
import com.example.e_commerceapp.utils.extensions.createErrorDialog
import com.example.e_commerceapp.utils.extensions.createProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    protected var progressDialog: ProgressDialog? = null
    protected val authViewModel: AuthViewModel by activityViewModels()
    protected val sharedPreferenceViewModel: SharedPreferenceViewModel by activityViewModels()
    protected val productViewModel: ProductViewModel by activityViewModels()
    protected val productDetailViewModel: ProductDetailViewModel by activityViewModels()
    protected val cartViewModel: CartViewModel by activityViewModels()
    protected val orderViewModel: OrderViewModel by activityViewModels()
    protected var errorDialog: ErrorDialog? = null
    protected val exitDialog by lazy {
        activity?.let { ExitDialog(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = requireContext().createProgressDialog()
        errorDialog = requireContext().createErrorDialog()
    }

    protected fun handleAuthState(state: AuthState, onSuccess: () -> Unit) {
        when (state) {
            is AuthState.Idle -> Unit
            is AuthState.Loading -> progressDialog?.show()
            is AuthState.Error -> {
                progressDialog?.dismiss()
                errorDialog?.apply {
                    setErrorMessage(state.error)
                    show()
                }
            }

            is AuthState.Success -> {
                progressDialog?.dismiss()
                loadProducts()
                authViewModel.resetLoginState()
                onSuccess.invoke()
            }
        }
    }

    protected fun handleApiState(apiState: ApiState, onSuccess: (MutableList<Product>) -> Unit) {
        when (apiState) {
            is ApiState.Loading -> {
                progressDialog?.show()
            }

            is ApiState.Error -> {
                errorDialog?.let {
                    it.setErrorMessage(apiState.errorMessage)
                    it.show()
                }
            }

            is ApiState.Success -> {
                progressDialog?.dismiss()
                onSuccess.invoke(apiState.products.toMutableList())
            }
        }
    }

    protected fun loadProducts() {
        productViewModel.fetchProducts()
    }

    protected fun showError(messageRes: Int) {
        errorDialog?.apply {
            setErrorMessage(getString(messageRes))
            show()
        }
    }
}