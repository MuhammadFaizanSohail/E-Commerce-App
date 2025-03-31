package com.example.e_commerceapp.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ui.component.dialog.ErrorDialog
import com.example.e_commerceapp.ui.component.dialog.ProgressDialog
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

var toast: Toast? = null

fun Context.showToast(msg: String) {
    cancelToast()
    toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    toast?.show()
}

fun cancelToast() {
    toast?.cancel()
}

fun Context.loadImage(imgProduct: ImageView, imageUrl: String?, shimmerLayout: ShimmerFrameLayout) {
    Picasso.get().load(imageUrl).into(imgProduct, object : Callback {
        override fun onSuccess() {
            shimmerLayout.stopShimmer()
            shimmerLayout.beGone()
            imgProduct.beVisible()
        }

        override fun onError(e: Exception?) {
            shimmerLayout.stopShimmer()
            shimmerLayout.beGone()
            imgProduct.setImageResource(R.drawable.placeholder_image)
        }
    })
}


fun Activity.setInsets(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.updatePadding(bottom = systemBars.bottom)
        WindowInsetsCompat.CONSUMED
    }
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme()
    windowInsetsController.isAppearanceLightNavigationBars = !isDarkTheme()
}


fun Context.checkNetwork(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

fun Context.createProgressDialog(): ProgressDialog = ProgressDialog(this).apply {
    setCancelable(false)
}

fun Context.createErrorDialog(): ErrorDialog = ErrorDialog(this).apply {
    setCancelable(false)
}

fun View.beVisible() {
    this.visibility = View.VISIBLE
}

fun View.beGone() {
    this.visibility = View.GONE
}

fun Activity.isDarkTheme(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

fun FragmentActivity.fragmentBackPress(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback()
            }
        })
}
