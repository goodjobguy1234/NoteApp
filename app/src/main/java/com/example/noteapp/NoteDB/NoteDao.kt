package com.example.noteapp.NoteDB

import androidx.room.*
import com.example.noteapp.NoteDB.entity.NoteItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface NoteDao {
    @Insert
    fun insertNote(vararg note: NoteItem): Completable?

    @Delete
    fun deleteNote(note: NoteItem): Completable?

    @Query("SELECT * FROM notes")
    fun getAllNote(): Observable<List<NoteItem>>?

    @Update
    fun updateNote(vararg note: NoteItem): Completable?

    @Query("SELECT * FROM notes WHERE uid == :mUid")
    fun getNote(mUid: Long): Single<NoteItem>?


}