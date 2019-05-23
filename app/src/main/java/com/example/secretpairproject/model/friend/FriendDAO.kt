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


    @Query("SELECT * FROM friend WHERE viewType=:me ORDER BY viewType")
    fun getMyInfo(me: Int): FriendDTO


    @Query("SELECT * FROM friend WHERE viewType=:normalFriend ORDER BY viewType")
    fun getNormalFriendList(normalFriend: Int): List<FriendDTO>


    @Query("SELECT * FROM friend WHERE viewType=:normalFriend AND name LIKE '%' || :keyword || '%' ORDER BY viewType")
    fun getNameLikeFriend(normalFriend: Int, keyword: String): List<FriendDTO>

    @Query("SELECT * FROM friend ORDER BY viewType")
    fun getAllInfo(): List<FriendDTO>

    @Query("SELECT* FROM friend WHERE viewType >= :birthDayHeader AND viewType <= :friendHeader ORDER BY viewType")
    fun getBetweenMeAndFriendList(birthDayHeader: Int, friendHeader: Int): List<FriendDTO>

}

