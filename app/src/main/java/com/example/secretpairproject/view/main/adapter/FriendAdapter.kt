package com.example.secretpairproject.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.secretpairproject.R
import com.example.secretpairproject.config.*
import com.example.secretpairproject.model.friend.FriendDTO
import com.example.secretpairproject.util.SharePreferenceManager
import kotlinx.android.synthetic.main.recycler_item_friend.view.*
import kotlinx.android.synthetic.main.recycler_item_friend_header.view.*
import kotlinx.android.synthetic.main.recycler_item_friend_me.view.*

class FriendAdapter(context: Context, private val click: (String) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.BaseViewHolder>() {


    private val mContext = context.applicationContext
    private val list: MutableList<FriendDTO> = ArrayList()
    private val visibleMap: MutableMap<String, MutableList<FriendDTO>> = HashMap()

    private val myInfo: MutableList<FriendDTO>
    private val birthDayList: MutableList<FriendDTO>
    private val recommendList: MutableList<FriendDTO>
    private val friendList: MutableList<FriendDTO>


    init {
        myInfo = ArrayList()
        recommendList = ArrayList()
        friendList = ArrayList()
        birthDayList = ArrayList()

        visibleMap["$FRIEND_HEADER"] = friendList
        visibleMap["$RECOMMEND_HEADER"] = recommendList
        visibleMap["$BIRTHDAY_HEADER"] = birthDayList


    }

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
            viewType % HEADER_MODULATION_CHECK_VALUE == HEADER -> {
                return HeaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_item_friend_header,
                        parent,
                        false
                    )
                )
            }
            else -> {
                FriendViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_item_friend,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        list.clear()
        list.addAll(myInfo)


        // if=> 닫혀있으면
        // else=> 열려있으면

        if (birthDayList.size > 1) {
            if (SharePreferenceManager.getBoolean(mContext, "$BIRTHDAY_HEADER")) {
                if (visibleMap.containsKey("$BIRTHDAY_HEADER")) {
                    visibleMap.remove("$BIRTHDAY_HEADER")
                }
            } else {
                visibleMap["$BIRTHDAY_HEADER"] = birthDayList
            }

        } else {
            if (visibleMap.containsKey("$BIRTHDAY_HEADER")) {
                visibleMap.remove("$BIRTHDAY_HEADER")
            }
        }

        if (recommendList.size > 1) {
            if (SharePreferenceManager.getBoolean(mContext, "$RECOMMEND_HEADER")) {
                if (visibleMap.containsKey("$RECOMMEND_HEADER")) {
                    visibleMap.remove("$RECOMMEND_HEADER")
                }
                if (recommendList.size > 0)
                    list.add(recommendList[0])
            } else {
                visibleMap["$RECOMMEND_HEADER"] = recommendList
            }
        } else {
            if (visibleMap.containsKey("$RECOMMEND_HEADER")) {
                visibleMap.remove("$RECOMMEND_HEADER")
            }
        }





        if (SharePreferenceManager.getBoolean(mContext, "$FRIEND_HEADER")) {
            if (visibleMap.containsKey("$FRIEND_HEADER")) {
                visibleMap.remove("$FRIEND_HEADER")
            }
            if (friendList.size > 0)
                list.add(friendList[0])
        } else {
            visibleMap["$FRIEND_HEADER"] = friendList
        }

        for (key in visibleMap.keys) {
            visibleMap[key]?.let { list.addAll(it) }
        }

        list.sort()
        return list.size

    }

    override fun onBindViewHolder(holderFriend: BaseViewHolder, position: Int) {
        val item = list[position]
        holderFriend.setView(item)
    }


    fun updateMyInfo(me: FriendDTO) {
        myInfo.clear()
        myInfo.add(me)
    }

    fun updateBirthDayList(list: List<FriendDTO>) {
        birthDayList.clear()
        birthDayList.addAll(list)
    }

    fun updateRecommendList(list: List<FriendDTO>) {
        recommendList.clear()
        recommendList.addAll(list)
    }

    fun updateFriendList(list: List<FriendDTO>) {
        friendList.clear()
        friendList.addAll(list)
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

            if (data.viewType == FRIEND_HEADER) {
                itemView.friend_header_title.text = "친구 ${friendList.size - 1}"
            } else {
                itemView.friend_header_title.text = data.name
            }


            if (SharePreferenceManager.getBoolean(mContext, "${data.viewType}")) {
                itemView.friend_header_btn.setImageResource(R.drawable.down_arrow)
            } else {
                itemView.friend_header_btn.setImageResource(R.drawable.up_arrow)
            }

            itemView.friend_header_btn.setOnClickListener {
                if (SharePreferenceManager.getBoolean(mContext, "${data.viewType}")) {
                    itemView.friend_header_btn.setImageResource(R.drawable.up_arrow)
                    SharePreferenceManager.put(mContext, "${data.viewType}", false)
                } else {
                    itemView.friend_header_btn.setImageResource(R.drawable.down_arrow)
                    SharePreferenceManager.put(mContext, "${data.viewType}", true)
                }
                notifyDataSetChanged()
            }


        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: FriendDTO)
    }


}




