package com.iSay1.roamstick.util

import android.content.Context
import android.net.ConnectivityManager

object NetConnection {
    @JvmStatic
    fun checkConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null) {
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return true
            } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
        }
        return false
    }
}