package com.example.secretpairproject.model.chat

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ChatDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(vararg chats: ChatDTO)

    @Update
    fun updateChat(vararg chats: ChatDTO)


    @Query(" SELECT * FROM chat WHERE roomId=:roomId LIMIT :page*50 ")
    fun getChatByRoomId(roomId: String, page: Int): LiveData<List<ChatDTO>>


//    @Query("UPDATE chatRoom SET id=:id, lastMessage=:lastMessage,lastDate=:lastDate, unReadCount=:unReadCount  WHERE id=:id")
//    fun updateMessage(id: String, lastMessage: String, lastDate: Date, unReadCount: Int)
//
//    @Delete
//    fun deleteFriend(vararg charRooms: ChatRoomDTO)
//
//    @Query("SELECT * FROM chatRoom  ORDER BY lastDate")
//    fun getAllChatRoom(): LiveData<List<ChatRoomDTO>>
//
//
//    @Query("SELECT * FROM chatRoom WHERE name LIKE '%' || :keyword || '%' ORDER BY lastDate")
//    fun getChatRoomKeywordSearch(keyword: String): LiveData<List<ChatRoomDTO>>


}

