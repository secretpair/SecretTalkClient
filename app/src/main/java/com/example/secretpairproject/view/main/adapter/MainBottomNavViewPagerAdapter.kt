package com.example.secretpairproject.view.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.secretpairproject.view.main.fragment.FriendFragment

class MainBottomNavViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    private val list = arrayListOf<Fragment>()

    init {
        list.add(FriendFragment)

    }


    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}