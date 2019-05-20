package com.example.secretpairproject.viewmodel.friend

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FriendViewModel(application: Application) : AndroidViewModel(application) {


    private val disposable: CompositeDisposable = CompositeDisposable()

    private val repository by lazy {
        FriendRepository(application)
    }
    private val friends: LiveData<List<FriendDTO>> by lazy {
        repository.getAllFriend()
    }

    fun getAllFriend() = friends

    fun getFriendByLikeName(keyword: String) {
        repository.getFriendByLikeName(keyword)
    }

    fun insertFriendByEmail(email: String) {

        disposable.add(
            repository.insertFriendByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }


        )
    }

    fun deleteFriendByEamil(friend : FriendDTO){

        disposable.add(
            repository.deleteFriend(friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ }

        )

    }


}