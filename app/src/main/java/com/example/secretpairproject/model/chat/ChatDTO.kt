package com.example.secretpairproject.model.chat

import androidx.room.*
import com.example.secretpairproject.config.room.DateTimeConverter
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import java.util.*


@Entity(
    tableName = "chat",
    foreignKeys = [ForeignKey(
        entity = ChatRoomDTO::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("roomId")
    )]
)
data class ChatDTO(
    @PrimaryKey val id: String,
    val roomId: String,
    val sender: String,
    val type: Int,
    val sendDate: Date,
    val profile: String,
    val unReadCount: Int,
    var isRead: Boolean,
    val content: String
) {

}