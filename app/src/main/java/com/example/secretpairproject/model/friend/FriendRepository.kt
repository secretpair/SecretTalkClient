package com.example.secretpairproject.model.friend

import android.app.Application
import com.example.secretpairproject.config.*
import io.reactivex.Flowable
import io.reactivex.Maybe


class FriendRepository(application: Application) {

    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }


    //Only Local
    fun getLocalMyInfo(): Maybe<FriendDTO> {
        return Maybe.fromCallable { friendDao.getMyInfo(ME) }
    }

    fun getLocalBirthDayList(): Maybe<List<FriendDTO>> {
        return Maybe.fromCallable { friendDao.getIncludeHeader(BIRTHDAY_HEADER, BIRTHDAY_FRIEND) }
    }

    fun getLocalRecommendData(): Maybe<List<FriendDTO>> {

        return Maybe.fromCallable { friendDao.getIncludeHeader(RECOMMEND_HEADER, RECOMMEND_FRIEND) }
    }

    fun getLocalNormalFriend(): Maybe<List<FriendDTO>> {
        return Maybe.fromCallable { friendDao.getIncludeHeader(FRIEND_HEADER, FRIEND_FRIEND) }
    }

    fun getLocalNormalFriendSearch(keyword: String): Maybe<List<FriendDTO>> {
        return Maybe.fromCallable { friendDao.getNameLikeFriend(FRIEND_HEADER, FRIEND_FRIEND, keyword) }
    }

    fun getLocalFriendByViewType(viewType: Int): Maybe<FriendDTO> {
        return Maybe.fromCallable { friendDao.getFriendByViewType(viewType) }
    }
    //Network


    //Local + Network
    fun insertFriend(friendDTO: FriendDTO): Maybe<Unit> {
        return Maybe.fromCallable { friendDao.insertFriend(friendDTO) }
    }

    fun deleteFriend(friendDTO: FriendDTO): Maybe<Unit> {
        return Maybe.fromCallable { friendDao.deleteFriend(friendDTO) }
    }

}
