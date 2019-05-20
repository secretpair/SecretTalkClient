package com.example.secretpairproject.model.friend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FriendDTO::class], version = 1, exportSchema = false)
abstract class FriendRoomDatabase : RoomDatabase() {

    abstract fun friendDao(): FriendDAO

    companion object {

        private var INSTANCE: FriendRoomDatabase? = null
        fun getInstance(context: Context): FriendRoomDatabase? {
            return INSTANCE ?: synchronized(FriendRoomDatabase::class) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FriendRoomDatabase::class.java, "friend.db"
                ).build().also { INSTANCE = it }
            }
        }

        fun destroyInstance () {
            INSTANCE = null
        }

    }


}

