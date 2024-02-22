package com.falcon.evCharger.setup.viewModel

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.setup.SetupFragment
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.onBoarding.LetsYouInFragment

import org.greenrobot.eventbus.EventBus

class SetupViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

//    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onSignInClick(view: View) {
        EventBus.getDefault().post(SetupFragment.ViewOnClick.SIGN_IN)
    }

}