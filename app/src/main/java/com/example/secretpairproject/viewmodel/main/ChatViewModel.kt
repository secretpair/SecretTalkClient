package com.example.secretpairproject.viewmodel.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.secretpairproject.base.BaseDisposableViewModel
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.model.chatroom.ChatRoomRepository
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ChatViewModel(application: Application) : BaseDisposableViewModel(application) {

    private val chatRoomRepository = ChatRoomRepository(application)
    private val chatRoomData: LiveData<List<ChatRoomDTO>> by lazy {
        chatRoomRepository.getLocalChatRoomList()
    }

    fun getChatRoomList() = chatRoomData


    init {
        loadList()
    }

    fun loadList() {
        getChatRoomList()
    }


    fun insertChatRoom(data: ChatRoomDTO) {
        addDisposable(
            chatRoomRepository.insertLocalChatRoomList(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                })
    }


}
