package com.example.secretpairproject.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BaseDisposableViewModel(application: Application) : AndroidViewModel(application) {


    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
