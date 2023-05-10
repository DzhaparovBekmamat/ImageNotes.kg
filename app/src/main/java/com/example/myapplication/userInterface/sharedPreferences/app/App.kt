package com.example.myapplication.userInterface.sharedPreferences.app

import android.app.Application
import android.content.SharedPreferences
import com.example.myapplication.userInterface.sharedPreferences.utils.Preferences

/**
 * Author: Dzhaparov Bekmamat
 */
class App : Application() {
    private lateinit var preferences: SharedPreferences
    private lateinit var prefs: Preferences
    override fun onCreate() {
        super.onCreate()
        preferences = this.applicationContext.getSharedPreferences("settings", MODE_PRIVATE)
        prefs = Preferences(preferences)
    }
}