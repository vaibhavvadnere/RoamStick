package com.iSay1.roamstick

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.iSay1.roamstick.base.BaseActivity

class MainActivity : BaseActivity() {

    var navController: NavController? = null


    interface onBackPressListener {
        fun onBackPress()
    }

    private var monBackPressListener: onBackPressListener? = null

    fun registerOnBackPress(monBackPressListener: onBackPressListener?) {
        this.monBackPressListener = monBackPressListener
    }

    fun unRegisterOnBackPress() {
        monBackPressListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(this@MainActivity, R.id.my_nav_host_fragment)
    }

    @SuppressLint("LongLogTag")
    override fun onResume() {
        super.onResume()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun networkIsAvailable() {}


}

