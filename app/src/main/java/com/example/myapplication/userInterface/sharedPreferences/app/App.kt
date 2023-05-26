package com.example.myapplication.userInterface.sharedPreferences.app

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.example.myapplication.dataBase.noteDataBase.NoteDatabase
import com.example.myapplication.userInterface.sharedPreferences.utils.Preferences

/**
 * Author: Dzhaparov Bekmamat
 */
class App : Application() {
    companion object {
        lateinit var prefs: Preferences
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        val preferences: SharedPreferences =
            this.applicationContext.getSharedPreferences("settings", MODE_PRIVATE)
        prefs = Preferences(preferences)
        database = Room.databaseBuilder(
            this, NoteDatabase::class.java, "database"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}