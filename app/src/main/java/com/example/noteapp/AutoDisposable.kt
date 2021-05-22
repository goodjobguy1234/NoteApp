package com.example.noteapp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AutoDisposable: LifecycleObserver {
    lateinit var compositeDisposables: CompositeDisposable

    fun bindTo(lifecycle: Lifecycle){
        lifecycle.addObserver(this)
        compositeDisposables = CompositeDisposable()
    }

    fun addDisposable(disposable: Disposable){

        if(::compositeDisposables.isInitialized){
            compositeDisposables.add(disposable)
        }
        else{
            throw Exception("This object must be bound to a lifecycle before registering a disposable")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        compositeDisposables.dispose()
    }


}