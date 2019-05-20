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
    private lateinit var friendViewModel: FriendViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_1friend, container, false)

        val adapter = FriendAdapter(list) { email ->
            Toast.makeText(context!!.applicationContext, "${email}님", Toast.LENGTH_SHORT).show()
        }

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel::class.java)
        friendViewModel.getAllFriend().observe(this, Observer {
            list.clear()
            list.addAll(it!!)
            adapter.notifyDataSetChanged()
        })
        testFunction()
        view.friend_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.friend_recycler_view.adapter = adapter


        return view
    }

    private fun testFunction() {

        val email = "alstn224@naver.com"
        friendViewModel.insertFriendByEmail(email+"1")
        friendViewModel.insertFriendByEmail(email+"2")
        friendViewModel.insertFriendByEmail(email+"3")
        friendViewModel.insertFriendByEmail(email+"4")
        friendViewModel.insertFriendByEmail(email+"5")
        friendViewModel.insertFriendByEmail(email+"6")
        friendViewModel.insertFriendByEmail(email+"7")
        friendViewModel.insertFriendByEmail(email+"8")
        friendViewModel.insertFriendByEmail(email+"9")

    }

}