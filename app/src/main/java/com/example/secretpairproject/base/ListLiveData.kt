package com.example.secretpairproject.base

import androidx.lifecycle.MutableLiveData


class ListLiveData<T>(val list: ArrayList<T> = ArrayList()) : MutableLiveData<ArrayList<T>>() {


    fun add(item: T) {
        list.add(item)
    }

    fun addAll(addList: List<T>) {
        list.addAll(addList)
    }

    fun clear() {
        list.clear()
    }

    fun remove(item: T) {
        list.remove(item)
    }

    fun notifyChange() {
        value = list

    }

}