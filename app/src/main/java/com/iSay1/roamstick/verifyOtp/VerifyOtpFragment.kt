package com.iSay1.roamstick.verifyOtp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.response.CreateUserResponse
import com.iSay1.roamstick.data.model.response.LoginData
import com.iSay1.roamstick.data.model.response.PropertyDetailsResponse
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.LogInFragmentBinding
import com.iSay1.roamstick.databinding.VerifyOtpFragmentBinding
import com.iSay1.roamstick.verifyOtp.viewModel.VerifyOtpViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class VerifyOtpFragment : HomeBaseFragment() {

    private lateinit var verifyOtpFragmentBinding: VerifyOtpFragmentBinding

    private val verifyOtpViewModel: VerifyOtpViewModel by activityViewModels()

    //Class to Handle all the button click
    enum class ViewOnClick {
        VERIFY, SIGN_UP, SCAN_QR_BARCODE,
    }

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    enum class UpdateEvent {
        VERIFY_OTP_FAILED, ENTERED_FIRST_DIGIT, ENTERED_SECOND_DIGIT, ENTERED_THIRD_DIGIT, ENTERED_FOURTH_DIGIT
    }

    var loginData: LoginData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        verifyOtpFragmentBinding = VerifyOtpFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { verifyOtpViewModel.init(it) }

        verifyOtpFragmentBinding.viewModel = verifyOtpViewModel

        return verifyOtpFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null) {

            if (bundle.containsKey(Constants.USER_DATA)) {
                loginData = bundle.getParcelable(Constants.USER_DATA)
                Log.e("loginDataLogs", " : prop :" + loginData)
            }
        }
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

            ViewOnClick.VERIFY -> {


                Log.e("onInClick", ":clicked  CONTINUE:")

                showDialog()

                verifyOtpViewModel.verifyUsers(loginData!!.Email!!)

            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.ENTERED_FIRST_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit2.requestFocus()
            }

            UpdateEvent.ENTERED_SECOND_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit3.requestFocus()

            }

            UpdateEvent.ENTERED_THIRD_DIGIT -> {
                verifyOtpFragmentBinding.otpDigit4.requestFocus()

            }

            UpdateEvent.ENTERED_FOURTH_DIGIT -> {
                mActivity?.hideKeyboard()
            }

            UpdateEvent.VERIFY_OTP_FAILED -> {
                hideDialog()
            }

            else -> {

            }
        }
    }

    @SuppressLint("LongLogTag")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(createUserResponse: CreateUserResponse) {

        hideDialog()

        Log.e("LoginResponseLogs", ":" + Gson().toJson(createUserResponse))

        sharePrefRepo.putBoolean(Constants.IS_LOGGED_IN, true)

        sharePrefRepo.putString(Constants.USER_ID, createUserResponse.data.Id)
        sharePrefRepo.putString(Constants.EMAIL, createUserResponse.data.Email)
        sharePrefRepo.putString(Constants.USER_TYPE, createUserResponse.data.UserType)
        sharePrefRepo.putString(Constants.LAST_NAME, createUserResponse.data.LastName)
        sharePrefRepo.putString(Constants.FIRST_NAME, createUserResponse.data.FirstName)

        mActivity?.navController?.navigate(R.id.action_verified_dashboard)
    }
}

