package com.example.e_commerceapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.e_commerceapp.di.appmodule.AppEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = EntryPointAccessors.fromApplication(this, AppEntryPoint::class.java).getSharedPreferences()

        val isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}