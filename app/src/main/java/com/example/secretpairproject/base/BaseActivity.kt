package com.example.secretpairproject.base


import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    fun log(message: String) {
        Log.e(this.javaClass.name, message)
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


    fun getStatusBarHeight(): Int {

        var statusHeight = 0
        val screenSizeType =
            (applicationContext.resources.configuration.screenLayout) or Configuration.SCREENLAYOUT_SIZE_MASK


        if (screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            val resourceId = applicationContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

            if (resourceId > 0) {
                statusHeight = applicationContext.getResources().getDimensionPixelSize(resourceId);
            }
        }

        return statusHeight;
    }


}
