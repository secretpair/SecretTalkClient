package com.example.secretpairproject.model.friend

import android.app.Application
import com.example.secretpairproject.config.BIRTHDAY_HEADER
import com.example.secretpairproject.config.FRIEND_FRIEND
import com.example.secretpairproject.config.FRIEND_HEADER
import com.example.secretpairproject.config.ME
import io.reactivex.Flowable


class FriendRepository(application: Application) {

    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }


    fun getLocalMyInfo(): Flowable<FriendDTO> {
        return Flowable.fromCallable { friendDao.getMyInfo(ME) }
    }

    fun getLocalNormalFriend(): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getNormalFriendList(FRIEND_FRIEND) }
    }

    fun getLocalNormalFriendSearch(keyword: String): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getNameLikeFriend(FRIEND_FRIEND, keyword) }
    }

    fun getBetweenMeAndFriendList(): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getBetweenMeAndFriendList(BIRTHDAY_HEADER, FRIEND_HEADER) }
    }

    fun insertFriend(friendDTO: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.insertFriend(friendDTO) }
    }

    fun deleteFriend(friendDTO: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.deleteFriend(friendDTO) }
    }

}
