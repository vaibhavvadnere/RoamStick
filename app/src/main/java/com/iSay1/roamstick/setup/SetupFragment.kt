package com.falcon.evCharger.setup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.setup.viewModel.SetupViewModel
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.SetupFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SetupFragment : HomeBaseFragment() {

    private lateinit var setupFragmentBinding: SetupFragmentBinding

    private val setupViewModel: SetupViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        CARETAKER_CLICKED, SHARE_LOCATION_CLICKED, LOGOUT_USER
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setupFragmentBinding = SetupFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { setupViewModel.init(it) }

        setupFragmentBinding.viewModel = setupViewModel



        return setupFragmentBinding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)


    }

    override fun connectionAvailable() {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(viewOnClick: ViewOnClick) {
        when (viewOnClick) {
            ViewOnClick.CARETAKER_CLICKED -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                mActivity?.navController?.navigate(R.id.action_setup_frag_caretakers)
            }

            ViewOnClick.LOGOUT_USER -> {
                Log.e("onSignInClick", ":clicked  SIGN_IN:")
                SharePrefRepo.getInstance().clearSharePref()
                mActivity?.navController?.navigate(R.id.action_logout)
            }

            else -> {

            }
        }
    }


}

