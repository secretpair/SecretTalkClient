package com.example.secretpairproject.viewmodel.friend

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.secretpairproject.base.BaseDisposableViewModel
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FriendViewModel(application: Application) : BaseDisposableViewModel(application) {

    private val _myData = MutableLiveData<FriendDTO>()
    private val _middleData = MutableLiveData<List<FriendDTO>>()
    private val _normalFriendList = MutableLiveData<List<FriendDTO>>()


    val myData: LiveData<FriendDTO> get() = _myData
    val middleData: LiveData<List<FriendDTO>> get() = _middleData
    val normalFriendList: LiveData<List<FriendDTO>> get() = _normalFriendList


    private val friendRepository by lazy {
        FriendRepository(application)
    }

    fun getMyInfo() {

        addDisposable(
            friendRepository.getLocalMyInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _myData.postValue(it)
                })


    }

    fun getBetweenMeAndFriendList() {
        addDisposable(
            friendRepository.getBetweenMeAndFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _middleData.postValue(it)
                }


        )
    }

    fun getNormalFriendList() {
        addDisposable(
            friendRepository.getLocalNormalFriend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _normalFriendList.postValue(it)
                }
        )
    }


    fun getLocalNormalFriendSearch(keyword: String) {
        addDisposable(
            friendRepository.getLocalNormalFriendSearch(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }
        )
    }


    fun insertFriend(friendDTO: FriendDTO) {
        addDisposable(friendRepository.insertFriend(friendDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMyInfo()
                getBetweenMeAndFriendList()
                getNormalFriendList()
            })
    }

    fun deleteFriend(friendDTO: FriendDTO) {
        addDisposable(
            friendRepository.deleteFriend(friendDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getMyInfo()
                    getBetweenMeAndFriendList()
                    getNormalFriendList()
                }
        )
    }


}