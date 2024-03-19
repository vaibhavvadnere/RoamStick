package com.iSay1.roamstick.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.base.HomeBaseFragment
import com.iSay1.roamstick.data.model.request.LoginRequest
import com.iSay1.roamstick.data.model.response.LoginResponse
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.databinding.LogInFragmentBinding
import com.iSay1.roamstick.login.viewModel.LoginFragmentViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginFragment : HomeBaseFragment(), MainActivity.onBackPressListener {

    private lateinit var logInFragmentBinding: LogInFragmentBinding

    private val loginFragmentViewModel: LoginFragmentViewModel by activityViewModels()

    val sharePrefRepo: SharePrefRepo = SharePrefRepo.getInstance()

    //Class to Handle all the button click
    enum class ViewOnClick {
        LOGIN, SIGN_UP, SCAN_QR_BARCODE,
    }

    //Class to Handle all update events
    enum class UpdateEvent {
        LOGIN_SUCCESS, LOGIN_FAILED, SCAN_QR_BARCODE,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        logInFragmentBinding = LogInFragmentBinding.inflate(inflater, container, false)

        mActivity?.let { loginFragmentViewModel.init(it) }

        logInFragmentBinding.viewModel = loginFragmentViewModel

        mActivity?.registerOnBackPress(this)

        if (Constants.IS_TESTING_MODE) {/*logInFragmentBinding.edtEmail.setText("amruta.ecs@gmail.com")
            logInFragmentBinding.edtPassword.setText("Atr2024@123#")*/
            logInFragmentBinding.edtEmail.setText("vaibhavvadnere@gmail.com")
            logInFragmentBinding.edtPassword.setText("abc123")
        }

        return logInFragmentBinding.root
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

            ViewOnClick.LOGIN -> {
                Log.e("onInClick", ":clicked  LOGIN:")
                if (logInFragmentBinding.checkBoxTermsAndConditions.isChecked) {
                    showDialog()

                    var loginRequest: LoginRequest = LoginRequest()

                    loginRequest.email = logInFragmentBinding.edtEmail.text.toString()
                    loginRequest.password = logInFragmentBinding.edtPassword.text.toString()
                    loginFragmentViewModel.login(loginRequest)
                } else Toast.makeText(mActivity, R.string.accept_the_terms_first, Toast.LENGTH_SHORT).show()
            }

            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(loginResponse: LoginResponse) {

        hideDialog()

        Log.e("LoginResponseLogs", ":" + Gson().toJson(loginResponse))

        sharePrefRepo.putBoolean(Constants.IS_LOGGED_IN, true)

        sharePrefRepo.putString(Constants.USER_ID, loginResponse.data.Id)
        sharePrefRepo.putString(Constants.EMAIL, loginResponse.data.Email)
        sharePrefRepo.putString(Constants.USER_TYPE, loginResponse.data.UserType)
        sharePrefRepo.putString(Constants.LAST_NAME, loginResponse.data.LastName)
        sharePrefRepo.putString(Constants.FIRST_NAME, loginResponse.data.FirstName)

        mActivity?.navController?.navigate(R.id.action_login_dashboard)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(updateEvent: UpdateEvent) {
        when (updateEvent) {

            UpdateEvent.LOGIN_FAILED -> {
                Log.e("UpdateEventLog", ":clicked  LOGIN_FAILED:")

                hideDialog()
            }

            else -> {

            }
        }
    }

    override fun onBackPress() {
        mActivity?.navController?.navigate(R.id.action_login_back_press)
    }
}

