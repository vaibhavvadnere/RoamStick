package com.falcon.evCharger.signUp.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.signUp.SignUpFragment
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo

import org.greenrobot.eventbus.EventBus

class SignUpFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

//    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Continue Click
    fun onContinueClick(view: View) {
        EventBus.getDefault().post(SignUpFragment.ViewOnClick.CONTINUE)
    }
}