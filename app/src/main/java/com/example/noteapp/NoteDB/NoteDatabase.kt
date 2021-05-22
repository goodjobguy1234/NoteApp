package com.example.noteapp.NoteDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.NoteDB.entity.NoteItem

@Database(entities = [NoteItem::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao() :NoteDao
}