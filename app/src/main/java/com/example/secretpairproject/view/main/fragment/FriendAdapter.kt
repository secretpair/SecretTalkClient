package com.example.secretpairproject.view.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.config.BIRTHDAY_HEADER
import com.example.secretpairproject.config.ME
import com.example.secretpairproject.config.MODULATION_IS_HEADER
import com.example.secretpairproject.model.friend.FriendDTO
import kotlinx.android.synthetic.main.recycler_item_friend.view.*
import kotlinx.android.synthetic.main.recycler_item_friend_header.view.*
import kotlinx.android.synthetic.main.recycler_item_friend_me.view.*
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class FriendAdapter(private val click: (String) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.BaseViewHolder>() {


    private val list: MutableList<FriendDTO> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when {
            viewType == ME -> MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_friend_me,
                    parent,
                    false
                )
            )
            viewType % MODULATION_IS_HEADER == 0 -> HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_friend_header, parent, false)
            )
            else -> FriendViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_friend,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holderFriend: BaseViewHolder, position: Int) {
        val item = list[position]
        holderFriend.setView(item)

    }

    inner class MyViewHolder(view: View) : BaseViewHolder(view) {

        override fun setView(data: FriendDTO) {

            itemView.setOnClickListener { click }

            itemView.friend_me_name_text_view.text = data.name

            itemView.friend_me_state_text_view.text = data.stateMessage

            if (data.profile.isNotBlank()) {
                //이미지 처리
            } else {

            }
        }
    }

    inner class FriendViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: FriendDTO) {

            itemView.setOnClickListener { click }

            itemView.friend_name_text_view.text = data.name

            itemView.friend_state_text_view.text = data.stateMessage

            if (data.profile.isNotBlank()) {
                //이미지 처리
            } else {

            }

        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: FriendDTO) {
            if (data.viewType == BIRTHDAY_HEADER)
                itemView.friend_header_title.text = data.name
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: FriendDTO)
    }


    fun addMiddleList(middleList: List<FriendDTO>) {
        list.addAll(middleList)
        list.sort()
    }

    fun updateMe(me: FriendDTO) {
        val index = list.indexOf(me)
        if (index != -1) {
            list[list.indexOf(me)] = me
        } else if (list.size == 0)
            list.add(me)

        list.sort()
    }

    fun updateNormalFriend(friends: List<FriendDTO>) {
        for (friend in friends) {
            val index = list.indexOf(friend)
            if (index != -1) {
                list[index] = friend
            } else {
                list.add(friend)
            }
        }
        list.sort()
    }


}




