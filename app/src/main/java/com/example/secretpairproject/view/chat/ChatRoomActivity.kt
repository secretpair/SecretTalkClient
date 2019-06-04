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
import com.example.secretpairproject.model.chatroom.ChatRoomDTO
import com.example.secretpairproject.view.chat.adapter.ChatAdapter
import com.example.secretpairproject.view.main.adapter.ChatRoomAdapter
import com.example.secretpairproject.viewmodel.main.ChatViewModel
import com.example.secretpairproject.viewmodel.main.ChatViewModelFactory
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


    private fun initWidget() {


        var initCheck = true

        //recycler view init
        val adapter = ChatAdapter(list, applicationContext)
        chat_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chat_recycler_view.adapter = adapter

        chat_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = chat_recycler_view.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount

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

        test()
    }


    private fun sendTextMessage(message: String) {

        val id = "$roomId//${Date().time}//${message.hashCode()}"
        log(id)
        val chat = ChatDTO(id, roomId, myEmail, myName, TEXT, Date(), "", userCount - 1, true, message)
        chatViewModel.insertChat(roomId, chat)


    }

    private fun visiblePreview(data: ChatDTO) {
        chat_room_preview_container.visibility = View.VISIBLE
    }

    private fun invisiblePreview() {
        chat_room_preview_container.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(null)
    }

    private fun test() {

        val time1 = Date()
        val time2 = Date(time1.time + 60000)
        val time3 = Date(time2.time + 1)
        val time4 = Date(time3.time + 60000)
        val time5 = Date(time4.time + 1)
        val time6 = Date(time5.time + 1)
        val time7 = Date(time6.time + 60000)

        val idd = 'a';
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 1}", "1", "alstn225@naver.com", "배민수", TEXT, time1, "", 1, true, "안녕하소")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 2}", "1", "alstn224@naver.com", "silvercong", TEXT, time2, "", 0, true, "그렇소!")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 3}", "1", "alstn225@naver.com", "배민수", TEXT, time3, "", 1, true, "애플 워치4 삼삼")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 4}", "1", "alstn224@naver.com", "silvercong", TEXT, time4, "", 0, true, "제시")
        )

        chatViewModel.insertChat(
            "1",
            ChatDTO(
                "asdsd+${idd + 5}",
                "1",
                "alstn225@naver.com", "배민수",
                TEXT,
                time5,
                "",
                1,
                true,
                "붕어빵3개랑 펩시콜라 1.25L로 가능?"
            )
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 6}", "1", "alstn225@naver.com", "배민수", TEXT, time6, "", 1, true, "???")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 7}", "1", "alstn224@naver.com", "silvercong", TEXT, time7, "", 0, true, "안됨")
        )

        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 8}", "1", "alstn224@naver.com", "silvercong", TEXT, time7, "", 0, true, "안됨")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 9}", "1", "alstn224@naver.com", "silvercong", TEXT, time7, "", 0, true, "안됨")
        )
        chatViewModel.insertChat(
            "1",
            ChatDTO("asdsd+${idd + 10}", "1", "alstn224@naver.com", "silvercong", TEXT, time7, "", 0, true, "안됨")
        )
    }

}
