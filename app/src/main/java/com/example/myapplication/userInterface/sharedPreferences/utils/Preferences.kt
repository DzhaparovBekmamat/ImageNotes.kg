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

    fun saveUserName(name: String) {
        preferences.edit().putString("name", name).apply()
    }

    fun getUserName(): String {
        return preferences.getString("name", "Unknown")!!
    }

    fun saveUserSurname(surname: String) {
        preferences.edit().putString("surname", surname).apply()
    }

    fun getUserSurname(): String {
        return preferences.getString("surname", "Unknown")!!
    }

    fun savePhoneNumber(phone: Int) {
        preferences.edit().putInt("phone", phone).apply()
    }

    fun getPhoneNumber(): Int {
        return preferences.getInt("phone", 996554746002.toInt())
    }

    fun saveMarried() {
        preferences.edit().putBoolean("isMarried", true).apply()
    }

    fun getMarried(): Boolean {
        return preferences.getBoolean("isMarried", false)
    }
}