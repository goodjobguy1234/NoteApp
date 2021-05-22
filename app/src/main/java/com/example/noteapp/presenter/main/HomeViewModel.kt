package com.example.noteapp.presenter.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.AutoDisposable
import com.example.noteapp.NoteDB.entity.NoteItem
import com.example.noteapp.repository.HomeRepo
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel() : ViewModel() {
    private lateinit var autoDisposable: AutoDisposable
    private val noteItem: MutableLiveData<List<NoteItem>> = MutableLiveData()
    private val toastMutableState: MutableLiveData<Boolean> = MutableLiveData(false)
    val toastState: LiveData<Boolean> = toastMutableState
    val liveDataNoteItem: LiveData<List<NoteItem>> = noteItem
    private lateinit var repoHome: HomeRepo

    fun init(repo: HomeRepo, disposable: AutoDisposable){
        repoHome = repo
        autoDisposable = disposable
    }

    fun getNoteItem(note: NoteItem? = null): NoteItem? {
        repoHome.getData (note) {
            return@getData
        }
         return null
    }

    fun getAllNote() {
        repoHome.getAllData {
            noteItem.value = it
        }
    }

    fun deleteNote(note: NoteItem) {
        repoHome.deleteData (note){
            toastMutableState.value = true
        }
    }

    fun insertNote(note: NoteItem) {
        repoHome.insertData(note) {
            toastMutableState.value = true
        }
    }

    fun updateNote(note: NoteItem) {
        repoHome.updateData(note) {
            toastMutableState.value = true
        }
    }

    fun resetToastState() {
        toastMutableState.value = false
    }

    override fun onCleared() {
        super.onCleared()
        autoDisposable.onDestroy()
    }
}