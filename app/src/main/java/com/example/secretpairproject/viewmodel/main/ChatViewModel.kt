package com.example.secretpairproject.viewmodel.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.secretpairproject.base.BaseDisposableViewModel
import com.example.secretpairproject.base.ListLiveData
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.model.chat.ChatRepository
import com.example.secretpairproject.view.chat.adapter.ChatAdapter
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.google.gson.reflect.TypeToken


class ChatViewModel(application: Application, val roomId: String) : BaseDisposableViewModel(application) {

    private val chatRepository = ChatRepository(application)

    private val chatData: ListLiveData<ChatDTO> = ListLiveData()

    fun getChatList() = chatData


    @SuppressLint("CheckResult")
    fun loadList(roomId: String) {
        chatRepository.getLocalChatRoomList(roomId, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                chatData.addAll(it)
                chatData.notifyChange()
            }
    }

    @SuppressLint("CheckResult")
    fun getPrevChatLoad(roomId: String, pages: Int) {
        chatRepository.getLocalChatRoomList(roomId, pages)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                chatData.addAll(it)
                chatData.notifyChange()
            }

    }

    fun insertChat(roomId: String, data: ChatDTO) {
        addDisposable(
            chatRepository.insertChat(roomId, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    chatData.add(data)
                    chatData.notifyChange()

                })
    }

}

class ChatViewModelFactory(val application: Application, val roomId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(application, roomId) as T
    }


}