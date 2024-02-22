package com.iSay1.roamstick.property

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.dashboard.viewModel.DashboardViewModel
import com.iSay1.roamstick.databinding.AddPropertyFragmentBinding
import com.iSay1.roamstick.databinding.DashboardFragmentBinding
import com.iSay1.roamstick.databinding.LogInFragmentBinding
import com.iSay1.roamstick.databinding.PropertyFragmentBinding
import com.iSay1.roamstick.databinding.VerifyOtpFragmentBinding
import com.iSay1.roamstick.property.viewModel.AddPropertyViewModel
import com.iSay1.roamstick.property.viewModel.PropertyViewModel
import com.iSay1.roamstick.verifyOtp.viewModel.VerifyOtpViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PropertyFragment : HomeBaseFragment() {

    private lateinit var propertyFragmentBinding: PropertyFragmentBinding

    private val propertyViewModel: PropertyViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        CREATE_PROPERTY, SIGN_UP, SCAN_QR_BARCODE,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        propertyFragmentBinding = PropertyFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { propertyViewModel.init(it) }

        propertyFragmentBinding.viewModel = propertyViewModel

        return propertyFragmentBinding.root
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

            ViewOnClick.CREATE_PROPERTY -> {
                Log.e("onInClick", ":clicked  CONTINUE:")
                mActivity?.navController?.navigate(R.id.action_sign_in)
            }

            else -> {

            }
        }
    }
}

