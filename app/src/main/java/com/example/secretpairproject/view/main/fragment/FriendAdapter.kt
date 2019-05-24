package com.example.secretpairproject.view.main.fragment

import android.util.Log
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

class FriendAdapter(private val click: (String) -> Unit) :
    RecyclerView.Adapter<FriendAdapter.BaseViewHolder>() {

    private val list: MutableList<FriendDTO> = ArrayList()

    private val headerList: MutableList<FriendDTO>
    private val myInfo: MutableList<FriendDTO>
    private val birthDayList: MutableList<FriendDTO>
    private val recommendList: MutableList<FriendDTO>
    private val friendList: MutableList<FriendDTO>

    private val visibleMap: HashMap<String, MutableList<FriendDTO>>
    private val invisibleMap: HashMap<String, MutableList<FriendDTO>>


    init {
        myInfo = ArrayList()
        recommendList = ArrayList()
        friendList = ArrayList()
        birthDayList = ArrayList()
        headerList = ArrayList()

        visibleMap = HashMap()
        invisibleMap = HashMap()

        visibleMap["$HEADER"] = headerList
        visibleMap["$ME"] = myInfo
        visibleMap["$FRIEND_HEADER"] = friendList
        visibleMap["$BIRTHDAY_HEADER"] = birthDayList
        visibleMap["$RECOMMEND_HEADER"] = recommendList
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
            viewType % MODULATION_IS_HEADER == 0 -> {
                val collapseCheck = SharePreferenceManager.getBoolean(parent.context, "$viewType")

                if (collapseCheck) {
                    val tmpList = visibleMap.remove("$viewType")
                    invisibleMap["$viewType"] = tmpList!!
                }


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
        list.addAll(headerList)
        list.addAll(myInfo)
        for (item in visibleMap.keys) {
            visibleMap[item]?.let { list.addAll(it) }
        }
        list.sort()
        return list.size

    }

    override fun onBindViewHolder(holderFriend: BaseViewHolder, position: Int) {
        val item = list[position]
        holderFriend.setView(item)


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
            itemView.friend_header_title.text = data.name
        }
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun setView(data: FriendDTO)
    }


    fun loadHeader(header: List<FriendDTO>) {
        changeItemUpdate(HEADER, *header.toTypedArray())
    }

    fun updateRecommend(recommlist: List<FriendDTO>) {
        changeItemUpdate(RECOMMEND_HEADER, *recommlist.toTypedArray())
    }

    fun updateBirthday(birthList: List<FriendDTO>) {
        changeItemUpdate(BIRTHDAY_HEADER, *birthList.toTypedArray())
    }

    fun updateMe(me: FriendDTO) {
        changeItemUpdate(ME, me)
    }

    fun updateNormalFriend(friends: List<FriendDTO>) {
        changeItemUpdate(FRIEND_HEADER, *friends.toTypedArray())
    }

    private fun changeItemUpdate(viewType: Int, vararg updateList: FriendDTO) {


        if (visibleMap.containsKey("$viewType")) {
            //보이는 상태면

            val mapList = visibleMap["$viewType"]
            for (item in updateList) {
                val index = mapList!!.indexOf(item)
                if (index != -1) {
                    mapList[index] = item
                    list[list.indexOf(item)] = item
                }
            }

        } else {

            val mapList = invisibleMap["$viewType"]
            for (item in updateList) {
                mapList!![mapList.indexOf(item)] = item
            }
        }
    }


}




