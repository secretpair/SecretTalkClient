package com.example.secretpairproject.model.friend

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Flowable


class FriendRepository(application: Application) {


    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }

    fun getLocalAllInfo() : LiveData<List<FriendDTO>>{
        return friendDao.getAllFriends()
    }


    fun getLocalMe(): LiveData<List<FriendDTO>> {
        return friendDao.getFriendMe()
    }

    fun getLocalAllFriend(): LiveData<List<FriendDTO>> {
        return friendDao.getAllFriends()
    }

    fun insertFriend(friend: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.insertFriend(friend) }
    }


    fun deleteFriend(vararg deleteFriends: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.deleteFriend(*deleteFriends) }
    }


}
