package com.example.secretpairproject.viewmodel.friend

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.secretpairproject.base.BaseDisposableViewModel
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository

class FriendViewModel(application: Application) : BaseDisposableViewModel(application) {

    private val _myData = MutableLiveData<FriendDTO>()
    private val _birthDayFriendList = MutableLiveData<List<FriendDTO>>()
    private val _normalFriendList = MutableLiveData<List<FriendDTO>>()


    val myData: LiveData<FriendDTO> get() = _myData
    val birthDayFriendList: LiveData<List<FriendDTO>> get() = _birthDayFriendList
    val normalFriendList: LiveData<List<FriendDTO>> get() = _normalFriendList


    private val friendRepository by lazy {
        FriendRepository(application)
    }

    fun getMyInfo() {
        _myData.postValue(friendRepository.getLocalMyInfo())
    }

    fun getNormalFriendList() {
        _normalFriendList.postValue(friendRepository.getLocalNormalFriend())
    }


}