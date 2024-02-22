package com.iSay1.roamstick.login.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.login.LoginFragment
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import org.greenrobot.eventbus.EventBus

class LoginFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onLoginClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.LOGIN)
    }
}