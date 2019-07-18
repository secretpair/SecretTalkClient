package com.example.secretpairproject.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.example.secretpairproject.R
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.view.main.adapter.ChatRoomAdapter
import com.example.secretpairproject.viewmodel.main.ChatRoomViewModel
import kotlinx.android.synthetic.main.fragment_2chat.*
import kotlinx.android.synthetic.main.fragment_2chat.view.*
import java.util.*
import kotlin.collections.ArrayList


object ChatRoomFragment : Fragment() {

    private val chatRoomViewModel by lazy { ViewModelProviders.of(this).get(ChatRoomViewModel::class.java) }
    private val list: ArrayList<ChatRoomDTO> by lazy { arrayListOf<ChatRoomDTO>() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_2chat, container, false)
        val adapter = ChatRoomAdapter(list, context!!)
        view.chatroom_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.chatroom_recycler_view.adapter = adapter

        chatRoomViewModel.getChatRoomList().observe(this, Observer {
            if(it!=null){
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }

        })

        test()
        return view
    }

    private fun test() {
        chatRoomViewModel.insertChatRoom(
            ChatRoomDTO("1", "silvercong", "신체기관같은거ㅋㅋㅋㅋㅋㅋㅋ", Date(), "", "", 5, 2)
        )
        chatRoomViewModel.insertChatRoom(
            ChatRoomDTO("2", "임순구", "닥쳐", Date(), "", "순대", 0, 2)
        )
        chatRoomViewModel.insertChatRoom(
            ChatRoomDTO("3", "김경태,남궁맹,동구,정준희", "바까놈", Date(), "", "", 40, 5)
        )
    }
}
