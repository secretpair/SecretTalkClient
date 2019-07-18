package com.example.secretpairproject.view.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.config.*
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.util.SharePreferenceManager
import kotlinx.android.synthetic.main.recycler_item_chat_receive_head_message.view.*
import kotlinx.android.synthetic.main.recycler_item_chat_receive_middle_message.view.*
import kotlinx.android.synthetic.main.recycler_item_chat_send_head_message.view.*
import kotlinx.android.synthetic.main.recycler_item_chat_send_middle_message.view.*
import kotlinx.android.synthetic.main.recyclerview_item_chat_notification.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val list: MutableList<ChatDTO>, context: Context) :
    RecyclerView.Adapter<ChatAdapter.BaseViewHolder>() {

    val myEmail: String by lazy {
        SharePreferenceManager.getString(context.applicationContext, "email")
    }
    private val timeFormat = SimpleDateFormat("HH:mm")
    private val currentYearDateFormat = SimpleDateFormat("MM월 dd일")
    private val beforeYearDateFormat = SimpleDateFormat("YY년 MM월 dd일")

    override fun getItemViewType(position: Int): Int {
        val chatData = list[position]
        when (chatData.type) {
            TEXT -> {
                if ((position != 0 && list[position - 1].senderEamil != list[position].senderEamil && myEmail == chatData.senderEamil) || position == 0) {
                    return SEND_HEAD_TEXT_MESSAGE
                } else if ((position != 0 && list[position - 1].senderEamil != list[position].senderEamil && myEmail != chatData.senderEamil) || position == 0) {
                    return RECEIVE_HEAD_TEXT_MESSAGE
                } else if ((position != 0 && list[position - 1].senderEamil == list[position].senderEamil && myEmail == chatData.senderEamil)) {
                    return SEND_MIDDLE_TEXT_MESSAGE
                } else if ((position != 0 && list[position - 1].senderEamil == list[position].senderEamil && myEmail != chatData.senderEamil)) {
                    return RECEIVE_MIDDLE_TEXT_MESSAGE
                }
            }

            CHAT_DATE_MESSAGE -> {
                return CHAT_DATE_MESSAGE
            }

        }


        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View
        when (viewType) {
            SEND_HEAD_TEXT_MESSAGE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_chat_send_head_message, parent, false)
                return SendTextHeaderMessageViewHolder(view)
            }
            SEND_MIDDLE_TEXT_MESSAGE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_item_chat_send_middle_message, parent, false)
                return SendTextMiddleMessageViewHolder(view)
            }
            RECEIVE_HEAD_TEXT_MESSAGE -> {
                view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_item_chat_receive_head_message, parent, false)
                return ReceiveTextHeaderMessageViewHolder(view)
            }
            RECEIVE_MIDDLE_TEXT_MESSAGE -> {
                view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_item_chat_receive_middle_message, parent, false)
                return ReceiveTextMiddleMessageViewHolder(view)
            }
            CHAT_DATE_MESSAGE -> {
                view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_item_chat_notification, parent, false)
                return DateMessageViewHolder(view)

            }

            else -> return ReceiveTextHeaderMessageViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.recycler_item_chat_send_head_message,
                    parent,
                    false
                )
            )

        }


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

    fun layoutFirstItemChangeMarginView(position: Int) {


    }

    fun layoutLastItemChangeMarginView(position: Int) {

    }
    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: ChatDTO)
    }

    inner class SendTextHeaderMessageViewHolder(view: View) : BaseViewHolder(view) {

        override fun setView(data: ChatDTO) {
            itemView.text_send_head_message_body.text = data.content
            itemView.text_send_head_message_time.text = timeFormat.format(data.sendDate)

            if (data.unReadCount == 0) {
                itemView.text_send_head_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_send_head_message_unread_count.visibility = View.VISIBLE
                itemView.text_send_head_message_unread_count.text = "${data.unReadCount}"
            }

            if (list.size - 1 != adapterPosition && list[adapterPosition].sendDate.isEqualDayAndTime(list[adapterPosition + 1].sendDate)) {
                itemView.text_send_head_message_time.visibility = View.GONE
            } else {
                itemView.text_send_head_message_time.visibility = View.VISIBLE
            }

        }
    }

    inner class ReceiveTextHeaderMessageViewHolder(view: View) : BaseViewHolder(view) {

        override fun setView(data: ChatDTO) {
            itemView.text_receive_head_message_body.text = data.content
            itemView.text_receive_head_message_name.text = data.senderName
            itemView.text_receive_head_message_time.text = timeFormat.format(data.sendDate)

            if (data.unReadCount == 0) {
                itemView.text_receive_head_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_receive_head_message_unread_count.visibility = View.VISIBLE
                itemView.text_receive_head_message_unread_count.text = "${data.unReadCount}"
            }

            if (list.size - 1 != adapterPosition && list[adapterPosition].sendDate.isEqualDayAndTime(list[adapterPosition + 1].sendDate)) {
                itemView.text_receive_head_message_time.visibility = View.GONE
            } else {
                itemView.text_receive_head_message_time.visibility = View.VISIBLE
            }

        }
    }

    inner class SendTextMiddleMessageViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: ChatDTO) {
            itemView.text_send_middle_message_body.text = data.content
            itemView.text_send_middle_message_unread_count.text = "${data.unReadCount}"
            itemView.text_send_middle_message_time.text = timeFormat.format(data.sendDate)

            if (data.unReadCount == 0) {
                itemView.text_send_middle_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_send_middle_message_unread_count.visibility = View.VISIBLE
                itemView.text_send_middle_message_unread_count.text = "${data.unReadCount}"
            }



            if (list.size - 1 != adapterPosition && list[adapterPosition].sendDate.isEqualDayAndTime(list[adapterPosition + 1].sendDate)) {
                itemView.text_send_middle_message_time.visibility = View.GONE
            } else {
                itemView.text_send_middle_message_time.visibility = View.VISIBLE
            }


        }

    }

    inner class ReceiveTextMiddleMessageViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: ChatDTO) {
            itemView.text_receive_middle_message_body.text = data.content
            itemView.text_receive_middle_message_unread_count.text = "${data.unReadCount}"
            itemView.text_receive_middle_message_time.text = timeFormat.format(data.sendDate)

            if (data.unReadCount == 0) {
                itemView.text_receive_middle_message_unread_count.visibility = View.INVISIBLE
            } else {
                itemView.text_receive_middle_message_unread_count.visibility = View.VISIBLE
                itemView.text_receive_middle_message_unread_count.text = "${data.unReadCount}"
            }

            if (list.size - 1 != adapterPosition && list[adapterPosition].sendDate.isEqualDayAndTime(list[adapterPosition + 1].sendDate)) {
                itemView.text_receive_middle_message_time.visibility = View.GONE
            } else {
                itemView.text_receive_middle_message_time.visibility = View.VISIBLE
            }

        }

    }

    inner class DateMessageViewHolder(view: View) : BaseViewHolder(view) {
        override fun setView(data: ChatDTO) {
            if (data.sendDate.isEqualDay(Date()))
                itemView.chat_notification.text = currentYearDateFormat.format(data.sendDate)
            else {
                itemView.chat_notification.text = beforeYearDateFormat.format(data.sendDate)
            }
        }

    }


}