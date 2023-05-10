package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Dzhaparov Bekmamat
 */
@Entity(tableName = "noteModel")
data class NoteModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val image: Int? = null,
)