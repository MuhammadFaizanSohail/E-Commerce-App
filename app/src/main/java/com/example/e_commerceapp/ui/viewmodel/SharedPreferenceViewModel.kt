package com.example.e_commerceapp.ui.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedPreferenceViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun isDarkModeEnabled() : Boolean{
        return sharedPreferences.getBoolean("dark_mode", false)
    }

    fun setDarkMode(value: Boolean) {
        sharedPreferences.edit { putBoolean("dark_mode", value) }
    }

}