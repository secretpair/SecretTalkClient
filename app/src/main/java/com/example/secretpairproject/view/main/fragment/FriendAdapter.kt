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

class FriendAdapter(private val click: (String) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.BaseViewHolder>() {


    private val list: MutableList<FriendDTO> = ArrayList()

    private val myInfo: MutableList<FriendDTO> = ArrayList()
    private val middleData: MutableList<FriendDTO> = ArrayList()
    private val friendList: MutableList<FriendDTO> = ArrayList()


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

    override fun getItemCount(): Int {
        list.clear()
        list.addAll(myInfo)
        list.addAll(middleData)
        list.addAll(friendList)

        return list.size

    }

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

            if (data.stateMessage.isNotEmpty()) {
                itemView.friend_state_text_view.visibility = View.VISIBLE
                itemView.friend_state_text_view.text = data.stateMessage
            } else {
                itemView.friend_state_text_view.visibility = View.GONE
            }

            if (data.profile.isNotBlank()) {
                //이미지 처리
            } else {

            }

        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: FriendDTO) {
            itemView.friend_header_title.text = data.name
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: FriendDTO)
    }


    fun updateMiddleList(middleList: List<FriendDTO>) {
        middleData.clear()
        middleData.addAll(middleList)
    }

    fun updateMe(me: FriendDTO) {
        myInfo.clear()
        myInfo.add(me)
    }

    fun updateNormalFriend(friends: List<FriendDTO>) {
        friendList.clear()
        friendList.addAll(friends)
    }


}




