package com.falcon.evCharger.signUp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.falcon.evCharger.signUp.viewModel.SignUpFragmentViewModel
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.databinding.SignUpFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SignUpFragment : HomeBaseFragment() {

    private lateinit var signUpFragmentBinding: SignUpFragmentBinding

    private val signUpFragmentViewModel: SignUpFragmentViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        CONTINUE, SIGN_UP, SCAN_QR_BARCODE,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        signUpFragmentBinding = SignUpFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { signUpFragmentViewModel.init(it) }

        signUpFragmentBinding.viewModel = signUpFragmentViewModel

        return signUpFragmentBinding.root
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

            ViewOnClick.CONTINUE -> {
                Log.e("onInClick", ":clicked  CONTINUE:")
                mActivity?.navController?.navigate(R.id.action_verify_otp)
            }

            else -> {

            }
        }
    }


}

