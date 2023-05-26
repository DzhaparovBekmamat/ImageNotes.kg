package com.example.myapplication.dataBase.dao

import androidx.room.*
import com.example.myapplication.model.NoteModel

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

    @Update
    fun update(model: NoteModel)
}