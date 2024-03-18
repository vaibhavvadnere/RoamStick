package com.iSay1.roamstick.login.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.model.request.LoginRequest
import com.iSay1.roamstick.data.model.response.LoginResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.login.LoginFragment
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragmentViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    fun login(loginRequest: LoginRequest?) {
        Log.e("loginRequestLog", ":" + Gson().toJson(loginRequest))

        if (mainRepo != null) {
            mainRepo!!.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "loginLogs", ":Success:" + Gson().toJson(response.body())
                        )
                        //                        Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                        if (response.body()?.message == "success") {
                            EventBus.getDefault().post(response.body())
                        } else {

                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT)
                                .show()

                            EventBus.getDefault().post(LoginFragment.UpdateEvent.LOGIN_FAILED)
                        }

                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
                        Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT)
                            .show()

                        EventBus.getDefault().post(LoginFragment.UpdateEvent.LOGIN_FAILED)

                        /*val isFirebaseLogsEnable =
                            sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)

                        if (isFirebaseLogsEnable) {
                            FireBaseApiLogs.firebaseLog(
                                Constants.REQUEST,
                                mainRepo!!.checkThirdPartyDeviceAvailability(macId).request()
                                    .toString()
                            )
                            FireBaseApiLogs.firebaseLog(Constants.RESPONSE, response.toString())
                            FireBaseApiLogs.publishLog(Constants.API_ERROR)
                        }*/
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("loginLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                        .show()

                    EventBus.getDefault()
                        .post(LoginFragment.UpdateEvent.LOGIN_FAILED)

                    /*
                      val isFirebaseLogsEnable =
                        sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)

                    if (isFirebaseLogsEnable) {
                        FireBaseApiLogs.firebaseLog(
                            Constants.REQUEST,
                            mainRepo!!.checkThirdPartyDeviceAvailability(macId).request().toString()
                        )
                        FireBaseApiLogs.firebaseLog(Constants.RESPONSE, t.message)
                        FireBaseApiLogs.publishLog(Constants.API_ERROR)
                    }*/
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            login(loginRequest)
        }
    }


    //Function to handle Yes Click
    fun onLoginClick(view: View) {
        EventBus.getDefault().post(LoginFragment.ViewOnClick.LOGIN)
    }
}