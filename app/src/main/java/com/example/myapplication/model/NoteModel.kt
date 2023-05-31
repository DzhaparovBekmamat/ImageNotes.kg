package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Dzhaparov Bekmamat
 */
@Entity(tableName = "noteModel")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String?,
    var description: String?,
    var date: String?
)