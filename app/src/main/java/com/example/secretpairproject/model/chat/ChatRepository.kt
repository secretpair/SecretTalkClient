package com.example.secretpairproject.model.chat

import android.app.Application
import com.example.secretpairproject.config.room.RoomDatabaseConfig
import io.reactivex.Maybe


class ChatRepository(application: Application) {

    private val chatDAO: ChatDAO by lazy {
        val db = RoomDatabaseConfig.getInstance(application)!!
        db.chatDao()
    }


    fun insertChat(roomId: String, chat: ChatDTO): Maybe<Unit> {
        return Maybe.fromCallable { chatDAO.insertChat(chat) }

    }

    //Only Local
    fun getLocalChatRoomList(roomId: String, page: Int): Maybe<List<ChatDTO>> {
        return Maybe.fromCallable { chatDAO.getChatByRoomId(roomId, page) }
    }



}
