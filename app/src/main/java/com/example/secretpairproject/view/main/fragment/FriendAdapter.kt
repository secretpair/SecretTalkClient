package com.example.secretpairproject.view.main.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.model.friend.FriendDTO
import kotlinx.android.synthetic.main.recycler_item_friend.view.*

class FriendAdapter(
    private val list: List<FriendDTO>
    , private val click: (String) -> Unit
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FriendViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_friend, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holderFriend: FriendViewHolder, position: Int) {
        val item = list[position]

        holderFriend.itemView.setOnClickListener { click }

        holderFriend.itemView.friend_name_text_view.text = item.name

        holderFriend.itemView.friend_state_text_view.text = item.stateMessage

        if (item.profile.isNotBlank()) {
            //이미지 처리
        } else {

        }
    }

    inner class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}




