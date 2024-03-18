package com.iSay1.roamstick.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.SplashFragmentBinding

class SplashFragment : HomeBaseFragment() {

    private lateinit var splashFragmentBinding: SplashFragmentBinding

    private val splashTimeout: Long = 2000

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        splashFragmentBinding = SplashFragmentBinding.inflate(inflater, container, false)

        /*mActivity?.let { splashFragmentViewModel.init(it) }

        splashFragmentBinding.viewModel = splashFragmentViewModel*/

        return splashFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (sharePrefRepo.getBoolean(Constants.IS_LOGGED_IN)) {
            mActivity?.navController?.navigate(R.id.action_dashboard)
        } else {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    mActivity?.navController?.navigate(R.id.action_lets_in)
                }, splashTimeout
            )
        }
    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        //        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        //        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}

