package com.example.secretpairproject.base

import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView


class ItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        Log.e("추가호출","헤이")
        val view = holder!!.itemView
        val alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(1000)
        alphaAnimator.interpolator = DecelerateInterpolator()
        alphaAnimator.start()
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        Log.e("삭제호출","헤이")
        val view = holder!!.itemView
        val alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(1000)
        alphaAnimator.interpolator = DecelerateInterpolator()
        alphaAnimator.start()
        return true
    }


    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        return super.animateMove(holder, fromX, fromY, toX, toY)
    }
}
