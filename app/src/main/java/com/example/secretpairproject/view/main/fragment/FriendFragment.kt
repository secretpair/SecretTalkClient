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


    private val list: ArrayList<FriendDTO> by lazy { arrayListOf<FriendDTO>() }
    private lateinit var friendViewModel: FriendViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_1friend, container, false)

        val adapter = FriendAdapter(list) { email ->
            Toast.makeText(context!!.applicationContext, "${email}님", Toast.LENGTH_SHORT).show()
        }

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel::class.java)
        friendViewModel.getFriendAllInfo().observe(this, Observer {
            list.clear()
            list.addAll(it!!)
            adapter.notifyDataSetChanged()
        })


        view.friend_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.friend_recycler_view.adapter = adapter

        testFunction()

        return view
    }

    private fun testFunction() {


        val email = "alstn224@naver.com"
        friendViewModel.insertFriend("kluge0221@gmail.com", ME)

        friendViewModel.insertFriend("BIRTHDAY_HEADER","오늘 생일인 친구", BIRTHDAY_HEADER)
        friendViewModel.insertFriend("RECOMMEND_HEADER","추천친구", RECOMMEND_HEADER)
        friendViewModel.insertFriend("RECOMMEND_FRIEND","새로운 친구를 만나보세요!", RECOMMEND_FRIEND)

        friendViewModel.insertFriend("FRIEND_HEADER","추천친구", FRIEND_HEADER)
        friendViewModel.insertFriend(email + "1", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "2", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "3", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "4", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "5", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "6", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "7", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "8", FRIEND_FRIEND)
        friendViewModel.insertFriend(email + "9", FRIEND_FRIEND)

    }

}