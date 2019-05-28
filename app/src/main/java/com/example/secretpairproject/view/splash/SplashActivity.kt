package com.example.secretpairproject.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.secretpairproject.R
import com.example.secretpairproject.config.BIRTHDAY_HEADER
import com.example.secretpairproject.config.FRIEND_HEADER
import com.example.secretpairproject.config.ME
import com.example.secretpairproject.config.RECOMMEND_HEADER
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.util.SharePreferenceManager
import com.example.secretpairproject.view.main.MainActivity
import com.example.secretpairproject.viewmodel.friend.FriendViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val disposables by lazy {
        CompositeDisposable()
    }

    private val friendViewModel by lazy { ViewModelProviders.of(this).get(FriendViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SharePreferenceManager.put(this, "email", "kluge0221@gmail.com")
        val source = Single.just<String>(SharePreferenceManager.getString(this, "email"))
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        source.subscribe { it ->
            friendViewModel.insertFriend(FriendDTO("alstn225@naver.com", "배민수", "상태 메시지입니다.", " ", " ", " ", ME))
            friendViewModel.insertFriend(FriendDTO("$BIRTHDAY_HEADER", "오늘 생일인 친구", "", " ", " ", " ", BIRTHDAY_HEADER))
            friendViewModel.insertFriend(FriendDTO("$RECOMMEND_HEADER", "추천친구", "", " ", " ", " ", RECOMMEND_HEADER))
            friendViewModel.insertFriend(FriendDTO("$FRIEND_HEADER", "친구", "", " ", " ", " ", FRIEND_HEADER))
            if (it.isBlank()) {
                //TODO 로그인 이동
            } else {
                //TODO 토큰 유효성 체크
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }.apply {
            disposables.add(this)
        }


    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }
}
