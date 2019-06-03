package com.example.secretpairproject.view.chat

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import android.view.WindowManager
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


            if (list.size == 0) {
                var prev: String = ""
                if (it != null && it.size > 0) {
                    val newList = ArrayList<ChatDTO>()
                    for (i in 0 until it.size) {
                        val now = it[i].sendDate.getYearMonthDayStr()
                        if (now != prev) {
                            prev = now
                            newList.add(ChatDTO(now, "", "", "", CHAT_DATE_MESSAGE, it[i].sendDate, "", 0, false, ""))
                        }
                        newList.add(it[i])
                    }
                    list.addAll(newList)
                }
            } else {
                if (it != null && it.size > 0) {
                    var prev: String = ""
                    val currentLastDate = list[0].sendDate.getYearMonthDayStr()
                    val startDate = it[0].sendDate.getYearMonthDayStr()
                    val endDate = it[it.size - 1].sendDate.getYearMonthDayStr()

                    //새로 가져온 데이터들의 전송 날짜가 현재의 가장 과거와 날짜가 일치 할 때
                    if ((startDate == list[list.size - 1].sendDate.getYearMonthDayStr()) && (endDate == list[list.size - 1].sendDate.getYearMonthDayStr())) {
                        list.addAll(1, it)
                    }
                    //새로 가져온 데이터들의 전송 날짜가 현재의 가장 과거와 일치하는게 없을 때
                    //새로 가져온 데이터들의 전송 날짜가 현재의 가장 과거와 일부 일치하고 일부는 일치하지 않을 떄
                    else {

                        val newList = ArrayList<ChatDTO>()
                        for (i in 0 until it.size) {
                            if (list[0].type == CHAT_DATE_MESSAGE && currentLastDate == it[i].sendDate.getYearMonthDayStr()) {
                                newList.add(list.removeAt(0))
                            }
                            val now = it[i].sendDate.getYearMonthDayStr()
                            if (now != prev) {
                                prev = now
                                newList.add(
                                    ChatDTO(
                                        now,
                                        "",
                                        "",
                                        "",
                                        CHAT_DATE_MESSAGE,
                                        it[i].sendDate,
                                        "",
                                        0,
                                        false,
                                        ""
                                    )
                                )
                            }
                            newList.add(it[i])
                        }
                        list.addAll(0, newList)
                    }
                }
            }
            adapter.notifyDataSetChanged()
        })


        chat_room_send_box.post {
            chat_room_top_box.post {
                val sendBaseSize = chat_room_send_box.height
                val topBaseSize = chat_room_top_box.height
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                val globalListener = ViewTreeObserver.OnGlobalLayoutListener {
                    val r = Rect();
                    chat_room_root_layout.getWindowVisibleDisplayFrame(r)

                    val diff = chat_room_root_layout.rootView.height - (r.bottom - r.top)
                    val sendBox = chat_room_send_box.layoutParams as ConstraintLayout.LayoutParams
                    val topBox = chat_room_top_box.layoutParams as ConstraintLayout.LayoutParams

                    if (diff > 210) {
                        sendBox.height = sendBaseSize
                        topBox.height = topBaseSize
                    } else {
                        sendBox.height = 0
                        topBox.height = 0
                    }
                    chat_room_send_box.layoutParams = sendBox
                    chat_room_top_box.layoutParams = topBox

                }
                chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(globalListener)
            }
        }

        chat_room_add_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (chat_room_add_edit_text.text.toString().trim().isNotEmpty()) {
                    chat_room_send_btn.setImageResource(R.drawable.send_active)
                    chat_room_send_btn.isEnabled = true
                } else {
                    chat_room_send_btn.setImageResource(R.drawable.send_non_active)
                    chat_room_send_btn.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        chat_room_back_btn.setOnClickListener { finish() }
        chatViewModel.loadList(roomId)


    }

    override fun onDestroy() {
        chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(null)
        super.onDestroy()
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
