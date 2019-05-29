package com.example.secretpairproject.model.chatroom

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.secretpairproject.config.room.DateTimeConverter
import java.util.*


@Entity(tableName = "chatRoom")
data class ChatRoomDTO(
    @PrimaryKey val id: String,
    val name: String,
    val lastMessage: String,
    val lastDate: Date,
    val profile: String,
    val customName: String,
    val unReadCount: Int,
    val userCount:Int
) {

}