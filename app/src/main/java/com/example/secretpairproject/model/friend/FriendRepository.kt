package com.example.secretpairproject.model.friend

import android.app.Application
import com.example.secretpairproject.config.*
import io.reactivex.Flowable


class FriendRepository(application: Application) {

    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }


    //Only Local
    fun getLocalMyInfo(): Flowable<FriendDTO> {
        return Flowable.fromCallable { friendDao.getMyInfo(ME) }
    }

    fun getLocalBirthDayList(): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getIncludeHeader(BIRTHDAY_HEADER, BIRTHDAY_FRIEND) }
    }

    fun getLocalRecommendData(): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getIncludeHeader(RECOMMEND_HEADER, RECOMMEND_FRIEND) }
    }

    fun getLocalNormalFriend(): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getIncludeHeader(FRIEND_HEADER, FRIEND_FRIEND) }
    }

    fun getLocalNormalFriendSearch(keyword: String): Flowable<List<FriendDTO>> {
        return Flowable.fromCallable { friendDao.getNameLikeFriend(FRIEND_HEADER, FRIEND_FRIEND, keyword) }
    }

    fun getLocalFriendByViewType(viewType: Int): Flowable<FriendDTO> {
        return Flowable.fromCallable { friendDao.getFriendByViewType(viewType) }
    }
    //Network


    //Local + Network
    fun insertFriend(friendDTO: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.insertFriend(friendDTO) }
    }

    fun deleteFriend(friendDTO: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.deleteFriend(friendDTO) }
    }

}
