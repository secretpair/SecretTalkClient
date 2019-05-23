package com.example.secretpairproject.view.main.fragment

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

    private val myInfo: MutableList<FriendDTO>
    private val birthDayList: MutableList<FriendDTO>
    private val recommendList: MutableList<FriendDTO>
    private val friendList: MutableList<FriendDTO>

    private val visibleMap: HashMap<String, List<FriendDTO>>
    private val invisibleMap: HashMap<String, List<FriendDTO>>


    init {
        myInfo = ArrayList()
        recommendList = ArrayList()
        friendList = ArrayList()
        birthDayList = ArrayList()

        visibleMap = HashMap()
        invisibleMap = HashMap()

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

        list.addAll(myInfo)

        list.addAll(birthDayList)
        list.addAll(recommendList)
        list.addAll(friendList)

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
        list.addAll(header)
    }

    fun updateRecommend(recommlist: List<FriendDTO>) {
        recommendList.clear()
        recommendList.addAll(recommlist)
    }

    fun updateBirthday(birthList: List<FriendDTO>) {
        birthDayList.clear()
        birthDayList.addAll(birthList)
    }

    fun updateMe(me: FriendDTO) {
        myInfo.clear()
        myInfo.add(me)
    }

    fun updateNormalFriend(friends: List<FriendDTO>) {
        friendList.clear()
        friendList.addAll(friends)
    }

    private fun changeItemUpdate(viewType: Int, updateList: List<FriendDTO>) {


        if(visibleMap.containsKey("$viewType")){
            //보이는 상태면

        }else{
            //안보이는 상태면
        }


    }


}




