package com.example.secretpairproject.model.chat

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.secretpairproject.config.room.RoomDatabaseConfig
import com.example.secretpairproject.model.chatroom.ChatRoomDAO
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import io.reactivex.Maybe


class ChatRepository(application: Application) {

    private val chatDAO: ChatDAO by lazy {
        val db = RoomDatabaseConfig.getInstance(application)!!
        db.chatDao()
    }

    //Only Local
    fun getLocalChatRoomList(roomId: String, page: Int): LiveData<List<ChatDTO>> {
        return chatDAO.getChatByRoomId(roomId, page)
    }


}
