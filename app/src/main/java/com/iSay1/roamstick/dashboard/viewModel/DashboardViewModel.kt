package com.iSay1.roamstick.dashboard.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.dashboard.DashboardFragment
import com.iSay1.roamstick.login.LoginFragment
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.verifyOtp.VerifyOtpFragment
import org.greenrobot.eventbus.EventBus

class DashboardViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onAddPropertyClick(view: View) {
        EventBus.getDefault().post(DashboardFragment.ViewOnClick.ADD_PROPERTY)
    }
}