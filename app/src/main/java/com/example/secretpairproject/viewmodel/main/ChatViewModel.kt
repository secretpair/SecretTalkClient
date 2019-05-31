package com.example.secretpairproject.viewmodel.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.secretpairproject.base.BaseDisposableViewModel
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.model.chat.ChatRepository
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.model.chatroom.ChatRoomRepository
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ChatViewModel(application: Application, roomId: String) : BaseDisposableViewModel(application) {

    private val chatRepository = ChatRepository(application)
    private val chatData: LiveData<List<ChatDTO>> by lazy {
        chatRepository.getLocalChatRoomList(roomId, 1)
    }

    fun getChatList() = chatData


    init {
        loadList()
    }

    fun loadList() {
        getChatList()
    }


    fun insertChat(roomId: String, data: ChatDTO) {
        addDisposable(
            chatRepository.insertChat(roomId, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { })
    }


}
