package com.example.myapplication.userInterface.sharedPreferences.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(private val preferences: SharedPreferences) {
    companion object {
        private const val PREFERENCES_NAME = "MyPreferences"
        private const val PHONE_NUMBER_KEY = "phone_number"
        private const val NAME_KEY = "name"
        private const val LAST_NAME_KEY = "last_name"
        private const val PHOTO_KEY = "photo"

        fun saveUserData(
            context: Context,
            phoneNumber: String,
            name: String,
            lastName: String,
            photo: String
        ) {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            preferences.edit().apply {
                putString(PHONE_NUMBER_KEY, phoneNumber)
                putString(LAST_NAME_KEY, lastName)
                putString(NAME_KEY, name)
                putString(PHOTO_KEY, photo)
            }.apply()
        }

        fun getUserPhoneNumber(context: Context): String? {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            return preferences.getString(PHONE_NUMBER_KEY, "")
        }

        fun getUserName(context: Context): String? {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            return preferences.getString(NAME_KEY, "")
        }

        fun getUserLastName(context: Context): String? {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            return preferences.getString(LAST_NAME_KEY, "")
        }

        fun getUserPhoto(context: Context): String? {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            return preferences.getString(PHOTO_KEY, "")
        }

        fun clearUserData(context: Context) {
            val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
        }
    }

    fun saveBoardState() {
        preferences.edit().putBoolean("isShow", true).apply()
    }

    fun isBoardShow(): Boolean {
        return preferences.getBoolean("isShow", false)
    }
}
