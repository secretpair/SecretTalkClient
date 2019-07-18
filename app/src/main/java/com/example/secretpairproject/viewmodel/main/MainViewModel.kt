package com.example.secretpairproject.viewmodel.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application) : AndroidViewModel(application) {


    val categoryTitle: MutableLiveData<String> = MutableLiveData()
    val topIconState: MutableLiveData<Int> = MutableLiveData()


}