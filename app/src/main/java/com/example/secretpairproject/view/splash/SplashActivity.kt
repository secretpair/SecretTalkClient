package com.example.secretpairproject.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.secretpairproject.R
import com.example.secretpairproject.util.SharePreferenceManager
import com.example.secretpairproject.view.main.MainActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SharePreferenceManager.put(this, "email", "kluge0221@gmail.com")



        val source = Single.just<String>(SharePreferenceManager.getString(this, "email"))
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        source.subscribe { it ->
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
