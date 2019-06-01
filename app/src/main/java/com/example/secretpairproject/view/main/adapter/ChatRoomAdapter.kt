package com.example.secretpairproject.view.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.view.chat.ChatRoomActivity
import kotlinx.android.synthetic.main.recycler_item_chatroom.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatRoomAdapter(private val list: MutableList<ChatRoomDTO>, context: Context) :
    RecyclerView.Adapter<ChatRoomAdapter.BaseViewHolder>() {

    private val normalDateFormat = SimpleDateFormat("MM월 dd일")
    private val todayDateFormat = SimpleDateFormat("HH:mm")
    private val yesterDayDateFormat = SimpleDateFormat("어제")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_chatroom, parent, false)
        return ChatRoomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setView(list[position])
    }


    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: ChatRoomDTO)
    }


    inner class ChatRoomViewHolder(view: View) : BaseViewHolder(view) {


        override fun setView(data: ChatRoomDTO) {

            itemView.setOnClickListener {

                Intent(itemView.context, ChatRoomActivity::class.java).let {
                    it.putExtra("roomId", data.id)
                    itemView.context.startActivity(it)
                }
            }


            if (data.customName.isNotEmpty()) {
                itemView.chatroom_name.text = data.customName
            } else {
                itemView.chatroom_name.text = data.name
            }


            if (data.unReadCount > 999) {
                itemView.chatroom_unread_count.text = "999+"
            } else if (data.unReadCount > 0) {
                itemView.chatroom_unread_count.visibility = View.VISIBLE
                itemView.chatroom_unread_count.text = "${data.unReadCount}"
            } else {
                itemView.chatroom_unread_count.text = "${data.unReadCount}"
                itemView.chatroom_unread_count.visibility = View.INVISIBLE
            }

            itemView.chatroom_user_count.text = "${data.userCount}"
            if (data.userCount > 2) {
                itemView.chatroom_user_count.visibility = View.VISIBLE
            } else {
                itemView.chatroom_user_count.visibility = View.INVISIBLE
            }


            val calendar = Calendar.getInstance()
            calendar.time = data.lastDate
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

            if (day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                itemView.chatroom_date.text = todayDateFormat.format(data.lastDate).toString()
            } else if (day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1) {
                itemView.chatroom_date.text = yesterDayDateFormat.format(data.lastDate).toString()
            } else {
                itemView.chatroom_date.text = normalDateFormat.format(data.lastDate).toString()
            }



            itemView.chatroom_last_message.text = data.lastMessage


        }
    }


}




