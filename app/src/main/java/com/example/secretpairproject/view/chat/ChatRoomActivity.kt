package com.example.secretpairproject.view.chat

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.secretpairproject.R
import com.example.secretpairproject.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        setBarTransparency()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initWidget()

    }


    private fun initWidget() {

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

        chat_recycler_view
    }

    override fun onDestroy() {
        chat_room_root_layout.viewTreeObserver.addOnGlobalLayoutListener(null)
        super.onDestroy()
    }
}
