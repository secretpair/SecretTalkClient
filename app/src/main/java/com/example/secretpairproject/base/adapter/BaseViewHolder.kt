package com.example.secretpairproject.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun setView()

}