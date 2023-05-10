package com.example.myapplication.userInterface.sharedPreferences.utils

import android.content.SharedPreferences

/**
 * Author: Dzhaparov Bekmamat
 */
class Preferences(private val preferences: SharedPreferences) {
    fun saveBoardState() {
        preferences.edit().putBoolean("isShow", true).apply()
    }

    fun isBoardShow(): Boolean {
        return preferences.getBoolean("isShow", false)
    }
}