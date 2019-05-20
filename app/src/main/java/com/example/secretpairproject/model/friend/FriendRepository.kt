package com.example.secretpairproject.model.friend

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Flowable


class FriendRepository(application: Application) {


    private val friendDao: FriendDAO by lazy {
        val db = FriendRoomDatabase.getInstance(application)!!
        db.friendDao()
    }

    private val friends: LiveData<List<FriendDTO>> by lazy {



        friendDao.loadAllFriends()
    }

    fun getAllFriend() = friends

    private fun insertFriend(friend: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.insertFriend(friend) }
    }

    fun deleteFriend(vararg deleteFriends: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.deleteFriend(*deleteFriends) }
    }

    fun getFriendByLikeName(keyword: String): LiveData<List<FriendDTO>> {
        return friendDao.loadNameLikeFriend(keyword)
    }

    fun insertFriendByEmail(email: String) : Flowable<Unit> {
        val friend = FriendDTO(email, "배민수", "너는 참 나쁘다...", "프로필 경로", "뮤직", " ")
        return insertFriend(friend)

    }

    fun updateAllFriend(vararg updateFriend: FriendDTO): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.updateAllFriend(*updateFriend) }
    }

    fun updateCustomName(email: String, customName: String): Flowable<Unit> {
        return Flowable.fromCallable { friendDao.updateCustomName(email, customName) }

    }


}
