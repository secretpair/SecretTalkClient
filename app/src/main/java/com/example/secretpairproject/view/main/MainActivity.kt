package com.example.secretpairproject.view.main

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.secretpairproject.R
import com.example.secretpairproject.base.BaseActivity
import com.example.secretpairproject.extension.disableShiftMode
import com.example.secretpairproject.view.main.adapter.MainBottomNavViewPagerAdapter
import com.example.secretpairproject.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    lateinit var pagerAdapter: MainBottomNavViewPagerAdapter
    lateinit var mainViewModel: MainViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.categoryTitle.value = "친구"
        mainViewModel.topIconState.value = 0
        mainViewModel.categoryTitle.observe(this, Observer {
            main_category_title.text = it.toString()
        })
        mainViewModel.topIconState.observe(this, Observer { state ->
            when (state) {

                0 -> {
                    main_search_btn.visibility = View.VISIBLE
                    main_friend_add_btn.visibility = View.VISIBLE
                    main_chat_add_btn.visibility = View.INVISIBLE
                }
                1 -> {
                    main_search_btn.visibility = View.VISIBLE
                    main_friend_add_btn.visibility = View.INVISIBLE
                    main_chat_add_btn.visibility = View.VISIBLE
                }
                2 -> {
                    main_search_btn.visibility = View.INVISIBLE
                    main_friend_add_btn.visibility = View.INVISIBLE
                    main_chat_add_btn.visibility = View.INVISIBLE
                }
                3 -> {
                    main_search_btn.visibility = View.INVISIBLE
                    main_friend_add_btn.visibility = View.INVISIBLE
                    main_chat_add_btn.visibility = View.INVISIBLE
                }

            }
        })

        setBarTransparency()
        initFragmentPager()
        initBottomNavigation()
        main_view_pager.setCurrentItem(0, true)


    }


    private fun initBottomNavigation() {
        main_bottom_nav_view.disableShiftMode()
        main_bottom_nav_view.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav1 -> {
                    main_view_pager.currentItem = 0
                    mainViewModel.categoryTitle.value = "친구"
                    mainViewModel.topIconState.value = 0
                    return@setOnNavigationItemSelectedListener true;
                }
                R.id.nav2 -> {
                    main_view_pager.currentItem = 0
                    mainViewModel.categoryTitle.value = "채팅"
                    mainViewModel.topIconState.value = 1
                    return@setOnNavigationItemSelectedListener true;

                }
                R.id.nav3 -> {
                    main_view_pager.currentItem = 0
                    mainViewModel.categoryTitle.value = "정보"
                    mainViewModel.topIconState.value = 2
                    return@setOnNavigationItemSelectedListener true;

                }
                R.id.nav4 -> {
                    main_view_pager.currentItem = 0
                    mainViewModel.categoryTitle.value = "설정"
                    mainViewModel.topIconState.value = 3
                    return@setOnNavigationItemSelectedListener true;

                }
                else ->
                    return@setOnNavigationItemSelectedListener false


            }


        }


    }

    private fun initFragmentPager() {
        pagerAdapter = MainBottomNavViewPagerAdapter(supportFragmentManager)
        main_view_pager.adapter = pagerAdapter
    }


}
