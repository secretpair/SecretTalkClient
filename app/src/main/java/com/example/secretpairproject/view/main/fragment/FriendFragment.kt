package com.example.secretpairproject.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.config.*
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.viewmodel.friend.FriendViewModel
import kotlinx.android.synthetic.main.fragment_1friend.view.*


object FriendFragment : Fragment() {

    private val friendViewModel by lazy { ViewModelProviders.of(this).get(FriendViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_1friend, container, false)

        val adapter = FriendAdapter { email ->
            Toast.makeText(context!!.applicationContext, "${email}님", Toast.LENGTH_SHORT).show()
        }



        view.friend_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.friend_recycler_view.adapter = adapter


        friendViewModel.myData.observe(this, Observer {
            adapter.updateMyInfo(it)
            adapter.notifyDataSetChanged()

        })

        friendViewModel.birthDayData.observe(this, Observer {
            adapter.updateBirthDayList(it)
            adapter.notifyDataSetChanged()

        })

        friendViewModel.recommendData.observe(this, Observer {
            adapter.updateRecommendList(it)
            adapter.notifyDataSetChanged()
        })


        friendViewModel.normalFriendData.observe(this, Observer {
            adapter.updateFriendList(it)
            adapter.notifyDataSetChanged()
        })

        test()


        return view
    }


    fun test() {

        friendViewModel.insertFriend(FriendDTO("alstn225@naver.com", "배민수", "silvercong", " ", " ", " ", ME))
        friendViewModel.insertFriend(FriendDTO("$BIRTHDAY_HEADER", "오늘 생일인 친구", "", " ", " ", " ", BIRTHDAY_HEADER))
        friendViewModel.insertFriend(FriendDTO("$RECOMMEND_HEADER", "추천친구", "", " ", " ", " ", RECOMMEND_HEADER))
        friendViewModel.insertFriend(
            FriendDTO(
                "$RECOMMEND_FRIEND",
                "새로운 친구를 만나보세요!",
                "",
                " ",
                " ",
                " ",
                RECOMMEND_FRIEND
            )
        )
        friendViewModel.insertFriend(FriendDTO("$FRIEND_HEADER", "친구", "", " ", " ", " ", FRIEND_HEADER))
        friendViewModel.insertFriend(
            FriendDTO(
                "alstn211@naver.com",
                "김민수1",
                "silvercong",
                " ",
                " ",
                " ",
                FRIEND_FRIEND
            )
        )
        friendViewModel.insertFriend(FriendDTO("alstn212@naver.com", "김민수2", "", " ", " ", " ", FRIEND_FRIEND))
        friendViewModel.insertFriend(
            FriendDTO(
                "alstn213@naver.com",
                "김민수4",
                "silvercong",
                " ",
                " ",
                " ",
                FRIEND_FRIEND
            )
        )
        friendViewModel.insertFriend(FriendDTO("alstn214@naver.com", "배민수1", "", " ", " ", " ", FRIEND_FRIEND))
        friendViewModel.insertFriend(
            FriendDTO(
                "alstn215@naver.com",
                "배민수2",
                "silvercong",
                " ",
                " ",
                " ",
                FRIEND_FRIEND
            )
        )
        friendViewModel.insertFriend(
            FriendDTO(
                "alstn216@naver.com",
                "배민수3",
                "silvercong",
                " ",
                " ",
                " ",
                FRIEND_FRIEND
            )
        )

    }

}