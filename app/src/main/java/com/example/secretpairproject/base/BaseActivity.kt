package com.example.secretpairproject.base

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {


    fun setBarTransparency() {

//        if (Build.VERSION.SDK_INT in 19..20) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
//            window.statusBarColor = Color.TRANSPARENT
//        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
    }

//    private fun setWindowFlag(bits: Int, on: Boolean) {
//        val win = window
//        val winParams = win.attributes
//        if (on) {
//            winParams.flags = winParams.flags or bits
//        } else {
//            winParams.flags = winParams.flags and bits.inv()
//        }
//        win.attributes = winParams
//    }


}
