package com.example.myapplication.userInterface.fragment.fireStore

/**
 * Author: Dzhaparov Bekmamat
 */
data class NoteStore(
    var title: String,
    var description: String,
    var date: String
) {
    constructor() : this("", "", "")
}
