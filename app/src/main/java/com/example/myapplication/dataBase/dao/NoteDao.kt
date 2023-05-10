package com.example.myapplication.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.NoteModel

/**
 * Author: Dzhaparov Bekmamat
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteModel")
    fun getAllNotes(): List<NoteModel>

    @Insert
    fun setNote(model: NoteModel)

    @Delete
    fun deleteNote(model: NoteModel)
}