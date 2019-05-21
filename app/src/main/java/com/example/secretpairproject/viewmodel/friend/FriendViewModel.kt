package com.example.secretpairproject.viewmodel.friend

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.model.friend.FriendRepository
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class FriendViewModel(application: Application) : AndroidViewModel(application) {


    private val repository by lazy {
        FriendRepository(application)
    }
    private val disposable: CompositeDisposable = CompositeDisposable()


    fun getFriendAllInfo(): LiveData<List<FriendDTO>> {
        return repository.getLocalAllInfo()
    }


    fun insertFriend(email: String, viewType: Int) {
        val friend = FriendDTO(email, "배민수", "너는 참 나쁘다...", "프로필 경로", "뮤직", " ", viewType)
        disposable.add(
            repository.insertFriend(friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
        )
    }

    fun insertFriend(email: String, name: String, viewType: Int) {
        val friend = FriendDTO(email, name, "너는 참 나쁘다...", "프로필 경로", "뮤직", " ", viewType)
        disposable.add(
            repository.insertFriend(friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
        )
    }


    fun deleteFriendByEmail(friend: FriendDTO) {
        disposable.add(
            repository.deleteFriend(friend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }

        )

    }


}