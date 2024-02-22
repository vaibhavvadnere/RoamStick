package com.iSay1.roamstick.bottomNavigation.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.bottomNavigation.MenuFragment
import com.iSay1.roamstick.dashboard.DashboardFragment
import com.iSay1.roamstick.login.LoginFragment
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.verifyOtp.VerifyOtpFragment
import org.greenrobot.eventbus.EventBus

class BottomNavigationMenuViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }
}