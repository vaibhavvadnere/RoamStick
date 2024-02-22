package com.iSay1.roamstick.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.dashboard.viewModel.DashboardViewModel
import com.iSay1.roamstick.databinding.DashboardFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DashboardFragment : HomeBaseFragment() {

    private lateinit var dashboardFragmentBinding: DashboardFragmentBinding

    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        ADD_PROPERTY, SIGN_UP, SCAN_QR_BARCODE,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        dashboardFragmentBinding = DashboardFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { dashboardViewModel.init(it) }

        dashboardFragmentBinding.viewModel = dashboardViewModel

        return dashboardFragmentBinding.root
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

            ViewOnClick.ADD_PROPERTY -> {
                Log.e("onInClick", ":clicked  CREATE_PROPERTY:")
                //                mActivity?.navController?.navigate(R.id.action_dashboard_add_property)
            }

            else -> {

            }
        }
    }


}

