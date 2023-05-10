package com.example.myapplication.dataBase.app

import android.app.Application
import androidx.room.Room
import com.example.myapplication.dataBase.noteDataBase.NoteDatabase

/**
 * Author: Dzhaparov Bekmamat
 */
class App : Application() {
    companion object {
        lateinit var database: NoteDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this, NoteDatabase::class.java, "database"
        ).allowMainThreadQueries().build()
    }

}