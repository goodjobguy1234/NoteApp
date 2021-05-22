package com.example.noteapp.NoteDB.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class NoteItem(
        @PrimaryKey(autoGenerate = true)
        val uid: Long = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "date_time")
        var date_time: String = "",
        @ColumnInfo(name = "content")
        var content: String = "",
        @ColumnInfo(name = "isFav")
        var isFav: Boolean = false
)
