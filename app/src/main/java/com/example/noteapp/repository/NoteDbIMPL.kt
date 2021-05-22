package com.example.noteapp.repository

import android.content.Context
import android.database.Observable
import androidx.room.Room
import com.example.noteapp.NoteDB.NoteDao
import com.example.noteapp.NoteDB.NoteDatabase
import com.example.noteapp.NoteDB.entity.NoteItem
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NoteDbIMPL(mcontext: Context) {
    private var applicationContext: Context? = null
    private var db: NoteDatabase? = null
    private var dbDao: NoteDao? = null

    init {
        applicationContext = mcontext
        applicationContext?.let {
            db = Room.databaseBuilder(
                it,
                NoteDatabase::class.java, "noteDB"
            ).build()
        }

        db?.let {
            dbDao = it.noteDao()
        }
    }

    fun getData(note: NoteItem? = null): Single<NoteItem>? {
        if (note != null)
            return dbDao?.getNote(note.uid)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())

        return null
    }

    fun getAllDate(): io.reactivex.Observable<List<NoteItem>>? {
        return dbDao?.getAllNote()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    fun updateData(note: NoteItem? = null): Completable? {
       return note?.let {
           dbDao?.updateNote(note)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun deleteData(note: NoteItem? = null): Completable? {
        return note?.let {
            dbDao?.deleteNote(note)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun insertData(note: NoteItem?): Completable? {
        return note?.let {
            dbDao?.insertNote(note)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
        }
    }
}