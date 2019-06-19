package com.example.secretpairproject.view.chat

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import android.view.WindowManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.base.BaseActivity
import com.example.secretpairproject.config.*
import com.example.secretpairproject.model.chat.ChatDTO
import com.example.secretpairproject.model.chat.ChatSocketManager
import com.example.secretpairproject.util.RecyclerViewPositionHelper
import com.example.secretpairproject.view.chat.adapter.ChatAdapter
import com.example.secretpairproject.viewmodel.main.ChatViewModel
import com.example.secretpairproject.viewmodel.main.ChatViewModelFactory
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_chat_room.*
import java.util.*

class ChatRoomActivity : BaseActivity() {


    private val roomId: String by lazy {
        intent.getStringExtra("roomId")
    }
    private val myEmail: String by lazy {
        intent.getStringExtra("email")
    }

    private val myName: String by lazy {
        intent.getStringExtra("name")
    }

    private val listHelper by lazy {
        RecyclerViewPositionHelper.createHelper(chat_recycler_view)
    }
    private val userCount = 2

    private val chatViewModel: ChatViewModel by lazy {
        ViewModelProviders.of(
            this,
            ChatViewModelFactory(application, roomId)
        ).get(ChatViewModel::class.java)
    }
    private val list: ArrayList<ChatDTO> by lazy { arrayListOf<ChatDTO>() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        setBarTransparency()
        initWidget()


    }


    val receiveTextMessageListener = Emitter.Listener {
        val listType = object : TypeToken<ArrayList<ChatDTO>>() {
        }.type

        val parser = JsonParser()
        val gson = Gson()
        for (json in it) {
            val jsonObject: JsonObject = parser.parse(json.toString()) as JsonObject
            val chat = gson.fromJson(jsonObject, ChatDTO::class.java)
            chatViewModel.insertChat(chat.roomId, chat)

            if (chat.senderEamil != myEmail && listHelper.findLastVisibleItemPosition() != listHelper.getItemCount() - 1) {
                visiblePreview(chat)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        ChatSocketManager.socket.emit(CHAT_ROOM_JOIN, roomId)
        ChatSocketManager.registerSocketListener(RECEIVE_TEXT_MESSAGE, receiveTextMessageListener)
        ChatSocketManager.makeConnection()
    }

    private fun initWidget() {


        var initCheck = true

        //recycler view init
        val adapter = ChatAdapter(list, applicationContext)
        chat_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chat_recycler_view.adapter = adapter

        chat_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)




                if (listHelper.findLastVisibleItemPosition() == listHelper.getItemCount() - 1) {
                    invisiblePreview()
                }

                val layoutManager = chat_recycler_view.layoutManager as LinearLayoutManager

                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val firstVisible = layoutManager.findFirstVisibleItemPosition()

                adapter.layoutLastItemChangeMarginView(lastVisible)
                adapter.layoutLastItemChangeMarginView(firstVisible)
            }
        })


        chatViewModel.getChatList().observe(this, Observer {
            var prev = ""
            val newList = ArrayList<ChatDTO>()
            for (i in 0 until it.size) {
                val now = it[i].sendDate.getYearMonthDayStr()
                if (now != prev) {
                    prev = now
                    newList.add(
                        ChatDTO(now, "", "", "", CHAT_DATE_MESSAGE, it[i].sendDate, "", 0, false, "")
                    )
                }
                newList.add(it[i])
            }
            list.clear()
            list.addAll(newList)

            if (initCheck) {
                initCheck = false
                chat_recycler_view.scrollToPosition(list.size - 1)

            }
            adapter.notifyDataSetChanged()
        })


        //messageBox, toolBox init
        chat_recycler_view.post {
            chat_room_send_box.post {
                chat_room_top_box.post {
                    chat_room_preview_container.post {

                        val sendBaseSize = chat_room_send_box.height
                        val topBaseSize = chat_room_top_box.height
                        val previewBaseSize = chat_room_preview_container.height
                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

                        val globalListener = ViewTreeObserver.OnGlobalLayoutListener {
                            val r = Rect();
                            chat_room_root_layout.getWindowVisibleDisplayFrame(r)


                            val diff = chat_room_root_layout.rootView.height - (r.bottom - r.top)

                            val sendBox = chat_room_send_box.layoutParams as ConstraintLayout.LayoutParams
                            val topBox = chat_room_top_box.layoutParams as ConstraintLayout.LayoutParams
                            val previewBox = chat_room_preview_container.layoutParams as ViewGroup.MarginLayoutParams



                            if (diff > 210) {
                                sendBox.height = sendBaseSize
                                topBox.height = topBaseSize
                                previewBox.height = previewBaseSize


                            } else {
                                sendBox.height = 0
                                topBox.height = 0
                                previewBox.height = 0


                            }
                            chat_room_send_box.layoutParams = sendBox
                            chat_room_top_box.layoutParams = topBox
                            chat_room_preview_container.layoutParams = previewBox


                        }


                        val recyclerViewListener =
                            View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                                val lastPosition =
                                    (chat_recycler_view.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                                chat_recycler_view.scrollToPosition(lastPosition)
                            }

                        chat_recycler_view.addOnLayoutChangeListener(recyclerViewListener)
                        chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(globalListener)
                    }
                }
            }
        }


        chat_room_add_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (chat_room_add_edit_text.text.toString().trim().isNotEmpty()) {
                    chat_room_send_btn.setImageResource(R.drawable.send_active)
                } else {
                    chat_room_send_btn.setImageResource(R.drawable.send_non_active)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        chat_room_back_btn.setOnClickListener { finish() }

        chat_room_send_btn.setOnClickListener {
            it.isEnabled = false

            val message = (chat_room_add_edit_text as EditText).text.toString()
            if (message.isBlank()) {
                it.isEnabled = true
                return@setOnClickListener
            }
            sendTextMessage(message)
            chat_room_add_edit_text.text.clear()
            chat_recycler_view.scrollToPosition(list.size - 1)
            it.isEnabled = true
        }


        chatViewModel.loadList(roomId)

        //preview init
        chat_room_preview_profile.post {
            val previewParam = chat_room_preview_profile.layoutParams as ConstraintLayout.LayoutParams
            previewParam.width = chat_room_preview_profile.height
            chat_room_preview_profile.layoutParams = previewParam
        }

        chat_room_preview_container.setOnClickListener {
            invisiblePreview()
            if (list.size > 1)
                chat_recycler_view.smoothScrollToPosition(list.size - 1)
        }

    }


    private fun sendTextMessage(message: String) {

        val id = "$roomId//${Date().time}//${message.hashCode()}"
        log(id)
        val chat = ChatDTO(id, roomId, myEmail, myName, TEXT, Date(), "", userCount - 1, true, message)

        val gson = Gson()
        val strJSON = gson.toJson(chat)
        ChatSocketManager.socket.emit(SEND_TEXT_MESSAGE, strJSON)
        chat_recycler_view.scrollToPosition((chat_recycler_view.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition())
        invisiblePreview()
//        chatViewModel.insertChat(roomId, chat)


    }

    private fun visiblePreview(data: ChatDTO) {
        chat_room_preview_container.visibility = View.VISIBLE
    }

    private fun invisiblePreview() {
        chat_room_preview_container.visibility = View.INVISIBLE
    }


    override fun onStop() {
        super.onStop()
        ChatSocketManager.unRegisterSocketListener()
        ChatSocketManager.socket.emit(CHAT_ROOM_LEAVE, roomId)
        ChatSocketManager.disconnection()
        chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(null)
    }

}
