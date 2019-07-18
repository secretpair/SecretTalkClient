package com.example.secretpairproject.config.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.secretpairproject.model.chat.ChatDAO
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.model.chatroom.ChatRoomDAO
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.model.friend.FriendDAO
import com.example.secretpairproject.model.friend.FriendDTO

@Database(entities = [FriendDTO::class, ChatRoomDTO::class, ChatDTO::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class RoomDatabaseConfig : RoomDatabase() {

    abstract fun friendDao(): FriendDAO
    abstract fun chatRoomDao(): ChatRoomDAO
    abstract fun chatDao() : ChatDAO

    companion object {

        private var INSTANCE: RoomDatabaseConfig? = null
        fun getInstance(context: Context): RoomDatabaseConfig? {
            return INSTANCE
                ?: synchronized(RoomDatabaseConfig::class) {
                    INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        RoomDatabaseConfig::class.java, "room.db"
                    ).build().also { INSTANCE = it }
                }
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }


}

