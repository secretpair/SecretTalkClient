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
import com.example.secretpairproject.config.TEXT
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
            ChatViewModelFactory(application, "1")
        ).get(ChatViewModel::class.java)
    }
    private val list: ArrayList<ChatDTO> by lazy { arrayListOf<ChatDTO>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        setBarTransparency()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        initWidget()

    }


    private fun initWidget() {


        val adapter = ChatAdapter(list, applicationContext)
        chat_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chat_recycler_view.adapter = adapter

        chatViewModel.getChatList().observe(this, Observer {
            Log.e("치키라우", "바지 치키라우")
            if (it != null) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }

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
                        topBox.height = 0;
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
        test()

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
