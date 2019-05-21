package com.example.secretpairproject.model.friend

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "friend")
data class FriendDTO(
    @PrimaryKey val email: String,
    val name: String,
    val stateMessage: String,
    val profile: String,
    val music: String,
    val customName: String,
    val viewType: Int
)