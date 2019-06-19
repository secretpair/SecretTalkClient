package com.example.secretpairproject.util

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import java.lang.NullPointerException


class RecyclerViewPositionHelper(recycler: RecyclerView) {

    private val recyclerView by lazy {
        recycler
    }
    private val layoutManager by lazy {
        recycler.layoutManager
    }


    companion object {

        fun createHelper(recyclerView: RecyclerView): RecyclerViewPositionHelper {
            return RecyclerViewPositionHelper(recyclerView)
        }
    }

    fun getItemCount(): Int {
        if (layoutManager == null) {
            return 0;
        }
        return layoutManager!!.itemCount
    }


    fun findFirstVisibleItemPosition(): Int {
        val child = findOneVisibleChild(
            0, layoutManager!!.childCount,
            completelyVisible = false,
            acceptPartiallyVisible = true
        )
        return if (child == null) {
            RecyclerView.NO_POSITION
        } else {
            recyclerView.getChildAdapterPosition(child)
        }

    }

    fun findFirstCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(
            0, layoutManager!!.childCount,
            completelyVisible = true,
            acceptPartiallyVisible = false
        )

        return if (child == null) {
            RecyclerView.NO_POSITION
        } else {
            recyclerView.getChildAdapterPosition(child)
        }
    }


    fun findLastVisibleItemPosition(): Int {
        val child = findOneVisibleChild(
            layoutManager!!.childCount - 1, -1,
            completelyVisible = false,
            acceptPartiallyVisible = true
        )
        return if (child == null) {
            RecyclerView.NO_POSITION
        } else {
            recyclerView.getChildAdapterPosition(child)
        }

    }

    fun findLastCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(
            layoutManager!!.childCount - 1, -1,
            completelyVisible = true,
            acceptPartiallyVisible = false
        )

        return if (child == null) {
            RecyclerView.NO_POSITION
        } else {
            recyclerView.getChildAdapterPosition(child)
        }
    }


    fun findOneVisibleChild(
        fromIndex: Int,
        toIndex: Int,
        completelyVisible: Boolean,
        acceptPartiallyVisible: Boolean
    ): View? {

        val helper
                : OrientationHelper = if (layoutManager!!.canScrollVertically()) {
            OrientationHelper.createVerticalHelper(layoutManager)
        } else {
            OrientationHelper.createHorizontalHelper(layoutManager)
        }

        val start = helper.startAfterPadding
        val end = helper.endAfterPadding
        val next: Int = if (toIndex > fromIndex) 1
        else 0

        var partiallyVisible: View? = null;

        var i = fromIndex
        while (i != toIndex) {
            val child: View? = layoutManager!!.getChildAt(i)
            val childStart: Int = helper.getDecoratedStart(child)
            val childEnd: Int = helper.getDecoratedEnd(child)

            if (childStart >= start && childEnd <= end) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child
                    }
                } else {
                    return child
                }
            }
            i += next
        }
        return partiallyVisible
    }


}