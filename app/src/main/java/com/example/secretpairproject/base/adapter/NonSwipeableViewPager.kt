package com.example.secretpairproject.base.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager


/**
 * Created by baeminsu on 06/09/2018.
 */

class NonSwipeableViewPager : ViewPager {


    constructor(context: Context) : super(context) {
        setMyScroller()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setMyScroller()
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private fun setMyScroller() {
        try {

            val viewPager: Class<*> = ViewPager::class.java
            val scroller = viewPager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, MyScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    class MyScroller(context: Context) : Scroller(context) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }


}
