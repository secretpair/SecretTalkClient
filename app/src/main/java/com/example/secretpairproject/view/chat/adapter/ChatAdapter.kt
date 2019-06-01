package com.example.secretpairproject.view.chat.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.secretpairproject.config.TEXT
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.config.RECEIVE_TEXT_MESSAGE
import com.example.secretpairproject.config.SEND_TEXT_MESSAGE
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.util.SharePreferenceManager
import kotlinx.android.synthetic.main.recycler_item_chat_receive_message.view.*
import kotlinx.android.synthetic.main.recycler_item_chat_send_message.view.*
import java.text.SimpleDateFormat

class ChatAdapter(private val list: MutableList<ChatDTO>, context: Context) :
    RecyclerView.Adapter<ChatAdapter.BaseViewHolder>() {

    val myEmail: String by lazy {
        SharePreferenceManager.getString(context.applicationContext, "email")
    }
    private val timeFormat = SimpleDateFormat("HH:mm")

    override fun getItemViewType(position: Int): Int {
        val chatData = list[position]
        when (chatData.type) {
            TEXT -> {
                if (myEmail == chatData.senderEamil) {
                    return SEND_TEXT_MESSAGE
                } else if (myEmail != chatData.senderEamil) {
                    return RECEIVE_TEXT_MESSAGE
                }
            }
        }


        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View
        if (viewType == SEND_TEXT_MESSAGE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_chat_send_message, parent, false)
            return SendTextMessageViewHolder(view)
        } else if (viewType == RECEIVE_TEXT_MESSAGE) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_chat_receive_message, parent, false)
            return ReceiveTextMessageViewHolder(view)
        }
        return ReceiveTextMessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_item_chat_send_message,
                parent,
                false
            )
        )


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {


        holder.setView(list[position])

    }


    fun addChat(chat: ChatDTO) {
        list.add(chat)
        list.sort()
    }

    fun addAllChat(vararg lists: ChatDTO) {
        list.addAll(lists)
        list.sort()
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: ChatDTO)
    }

    inner class SendTextMessageViewHolder(view: View) : BaseViewHolder(view) {

        override fun setView(data: ChatDTO) {


            itemView.text_send_message_body.text = data.content
            itemView.text_send_message_time.text = timeFormat.format(data.sendDate)

            if (data.unReadCount == 0) {
                itemView.text_send_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_send_message_unread_count.visibility = View.VISIBLE
                itemView.text_send_message_unread_count.text = "${data.unReadCount}"
            }

        }
    }

    inner class ReceiveTextMessageViewHolder(view: View) : BaseViewHolder(view) {

        override fun setView(data: ChatDTO) {


            if (adapterPosition != 0 && (list[adapterPosition - 1].senderEamil == data.senderEamil)) {
                itemView.text_receive_message_profile.visibility = View.GONE
                itemView.text_receive_message_name.visibility = View.GONE
            }

            itemView.text_receive_message_body.text = data.content
            itemView.text_receive_message_time.text = timeFormat.format(data.sendDate)
            itemView.text_receive_message_name.text = data.senderName

            if (data.unReadCount == 0) {
                itemView.text_receive_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_receive_message_unread_count.visibility = View.VISIBLE
                itemView.text_receive_message_unread_count.text = "${data.unReadCount}"
            }

        }
    }
}