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

    private val friendRepository = FriendRepository(application)

    private val _myData = MutableLiveData<FriendDTO>()
    private val _birthDayData = MutableLiveData<List<FriendDTO>>()
    private val _recommendData = MutableLiveData<List<FriendDTO>>()


    private val _normalFriendData = MutableLiveData<List<FriendDTO>>()
    val myData: LiveData<FriendDTO> get() = _myData
    val birthDayData: LiveData<List<FriendDTO>> get() = _birthDayData
    val recommendData: LiveData<List<FriendDTO>> get() = _recommendData
    val normalFriendData: LiveData<List<FriendDTO>> get() = _normalFriendData

    init {
        getMyInfo()
        getBirthDayList()
        getRecommendData()
        getNormalFriendList()
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

    fun getBirthDayList() {
        addDisposable(
            friendRepository.getLocalBirthDayList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _birthDayData.postValue(it)
                }
        )
    }

    fun getRecommendData() {
        addDisposable(
            friendRepository.getLocalRecommendData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _recommendData.postValue(it)
                }
        )
    }


    fun getNormalFriendList() {
        addDisposable(
            friendRepository.getLocalNormalFriend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _normalFriendData.postValue(it)
                }
        )
    }

    fun insertFriend(friendDTO: FriendDTO) {
        addDisposable(friendRepository.insertFriend(friendDTO)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }

    fun deleteFriend(friendDTO: FriendDTO) {
        addDisposable(
            friendRepository.deleteFriend(friendDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getMyInfo()
                    getNormalFriendList()
                }
        )
    }


}