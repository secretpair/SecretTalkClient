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
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.viewmodel.friend.FriendViewModel
import kotlinx.android.synthetic.main.fragment_1friend.view.*


object FriendFragment : Fragment() {


    private val list: ArrayList<FriendDTO> by lazy { arrayListOf<FriendDTO>() }
    private val friendViewModel by lazy { ViewModelProviders.of(this).get(FriendViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_1friend, container, false)

        val adapter = FriendAdapter { email ->
            Toast.makeText(context!!.applicationContext, "${email}님", Toast.LENGTH_SHORT).show()
        }

        view.friend_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.friend_recycler_view.adapter = adapter



        friendViewModel.myData.observe(this, Observer {
            adapter.updateMe(it)
        })

        friendViewModel.birthDayFriendList.observe(this, Observer {

        })
        friendViewModel.normalFriendList.observe(this, Observer {
            adapter.updateNormalFriend(it)
        })




        return view
    }


    fun test(){


    }

}