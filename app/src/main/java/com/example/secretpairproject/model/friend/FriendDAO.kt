package com.example.secretpairproject.model.friend

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FriendDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFriend(vararg friends: FriendDTO)

    @Update
    fun updateAllFriend(vararg friends: FriendDTO)

    @Query("UPDATE friend SET customName=:customName WHERE email=:email")
    fun updateCustomName(email: String, customName: String)

    @Delete
    fun deleteFriend(vararg friends: FriendDTO)


    @Query("SELECT * FROM friend ORDER BY viewType")
    fun getAllFriends(): LiveData<List<FriendDTO>>

    @Query("SELECT * FROM friend WHERE name LIKE '%' || :keyword || '%' ")
    fun getNameLikeFriend(keyword: String): LiveData<List<FriendDTO>>

    @Query("SELECT * FROM friend WHERE viewType=0")
    fun getFriendMe(): LiveData<List<FriendDTO>>



}