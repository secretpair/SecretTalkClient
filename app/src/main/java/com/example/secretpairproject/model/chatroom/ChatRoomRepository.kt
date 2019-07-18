package com.example.secretpairproject.model.chatroom

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.secretpairproject.config.room.RoomDatabaseConfig
import io.reactivex.Flowable
import io.reactivex.Maybe


class ChatRoomRepository(application: Application) {

    private val chatRoomDAO: ChatRoomDAO by lazy {
        val db = RoomDatabaseConfig.getInstance(application)!!
        db.chatRoomDao()
    }

    //Only Local
    fun getLocalChatRoomList(): LiveData<List<ChatRoomDTO>> {
        return chatRoomDAO.getAllChatRoom()
    }


//
//
//    //Local + Network
fun insertLocalChatRoomList(data: ChatRoomDTO): Maybe<Unit> {
    return Maybe.fromCallable { chatRoomDAO.insertFriend(data) }

}

}
