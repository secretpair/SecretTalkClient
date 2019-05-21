package com.example.secretpairproject.model.friend

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.secretpairproject.config.FRIEND_FRIEND
import com.example.secretpairproject.config.ME


class FriendRepository(application: Application) {

    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }


    fun getLocalMyInfo(): FriendDTO {
        return friendDao.getMyInfo(ME)
    }

    fun getLocalNormalFriend(): List<FriendDTO> {
        return friendDao.getNormalFriendList(FRIEND_FRIEND)
    }

    fun getLocalNormalFriendSearch(keyword: String): List<FriendDTO> {
        return friendDao.getNameLikeFriend(FRIEND_FRIEND, keyword)
    }

}
