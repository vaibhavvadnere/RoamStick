package com.iSay1.roamstick.onBoarding.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.onBoarding.LetsYouInFragment
import org.greenrobot.eventbus.EventBus

class LetsYouInFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onSignInClick(view: View) {
        EventBus.getDefault().post(LetsYouInFragment.ViewOnClick.SIGN_IN)
    }

}