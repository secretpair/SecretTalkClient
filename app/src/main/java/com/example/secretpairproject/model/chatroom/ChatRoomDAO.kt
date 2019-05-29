package com.example.secretpairproject.model.chatroom

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface ChatRoomDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriend(vararg charRooms: ChatRoomDTO)

    @Update
    fun updateAllFriend(vararg charRooms: ChatRoomDTO)

    @Query("UPDATE chatRoom SET customName=:customName WHERE id=:id")
    fun updateCustomName(id: String, customName: String)

    @Query("UPDATE chatRoom SET id=:id, lastMessage=:lastMessage,lastDate=:lastDate, unReadCount=:unReadCount  WHERE id=:id")
    fun updateMessage(id: String, lastMessage: String, lastDate: Date, unReadCount: Int)

    @Delete
    fun deleteFriend(vararg charRooms: ChatRoomDTO)

    @Query("SELECT * FROM chatRoom  ORDER BY lastDate")
    fun getAllChatRoom(): LiveData<List<ChatRoomDTO>>


    @Query("SELECT * FROM chatRoom WHERE name LIKE '%' || :keyword || '%' ORDER BY lastDate")
    fun getChatRoomKeywordSearch(keyword: String): LiveData<List<ChatRoomDTO>>


}

