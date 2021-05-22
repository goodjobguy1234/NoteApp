package com.example.noteapp.repository

import android.content.Context
import android.util.Log
import com.example.noteapp.AutoDisposable
import com.example.noteapp.NoteDB.entity.NoteItem

object HomeRepo {
    lateinit var noteImpl: NoteDbIMPL
    lateinit var disposable: AutoDisposable
    fun init(mContext: Context, autoDisposable: AutoDisposable) {
        noteImpl = NoteDbIMPL(mContext)
        disposable = autoDisposable
    }

    fun getData(note: NoteItem? = null, callBack:(NoteItem) -> Unit){
        disposable.addDisposable(
                noteImpl.getData(note)?.subscribe({
            callBack(it)
        }, {})!!
        )
    }

    fun getAllData(callBack:(List<NoteItem>) -> Unit) {
      disposable.addDisposable(
              noteImpl.getAllDate()?.subscribe({
                  print("getData")
                  callBack(it)
              }, {})!!
      )
    }

    fun deleteData(note: NoteItem?, callBack:() -> Unit) {
        disposable.addDisposable(
                noteImpl.deleteData(note)?.subscribe({
                    callBack()
                }, {})!!
        )
    }

    fun updateData(note: NoteItem?,callBack:() -> Unit) {
        disposable.addDisposable(
                noteImpl.updateData(note)?.subscribe({
                    callBack()
                }, {})!!
        )
    }

    fun insertData(note: NoteItem?,callBack:() -> Unit) {
        disposable.addDisposable(
                noteImpl.insertData(note)?.subscribe({
                    Log.d("Insertion", "Item Insert")
                    callBack()
                }, { print("Insert Fail")})!!
        )
    }
}