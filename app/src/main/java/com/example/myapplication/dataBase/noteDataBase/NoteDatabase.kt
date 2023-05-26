package com.example.myapplication.dataBase.noteDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.NoteModel
import com.example.myapplication.dataBase.dao.NoteDao

/**
 * Author: Dzhaparov Bekmamat
 */
@Database(entities = [NoteModel::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao
}
