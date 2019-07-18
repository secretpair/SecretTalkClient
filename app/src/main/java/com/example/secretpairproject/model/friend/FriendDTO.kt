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
) : Comparable<FriendDTO> {

    override fun compareTo(other: FriendDTO): Int {
        if (viewType - other.viewType != 0) return viewType - other.viewType
        else {
            return if (customName.isNotBlank()) {
                if (other.customName.isNotBlank()) {
                    customName.compareTo(other.customName)
                } else {
                    customName.compareTo(other.name)
                }
            } else {
                if (other.customName.isNotBlank()) {
                    name.compareTo(other.customName)
                } else {
                    name.compareTo(other.name)
                }


            }

        }
    }

    override fun equals(other: Any?): Boolean = email == (other as FriendDTO).email


    override fun hashCode(): Int {
        return super.hashCode()
    }
}