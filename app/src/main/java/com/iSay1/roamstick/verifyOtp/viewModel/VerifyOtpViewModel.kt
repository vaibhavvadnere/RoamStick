package com.iSay1.roamstick.verifyOtp.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.falcon.evCharger.signUp.SignUpFragment
import com.google.gson.Gson
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.model.request.CreateUserRequest
import com.iSay1.roamstick.data.model.request.VerifyOtpRequest
import com.iSay1.roamstick.data.model.response.CreateUserResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.verifyOtp.VerifyOtpFragment
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOtpViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    var strDigitOne: String = ""
    var strDigitTwo: String = ""
    var strDigitThree: String = ""
    var strDigitFour: String = ""

    //    private val sharePrefRepo = SharePrefRepo.getInstance()
    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onVerifyClick(view: View) {
        EventBus.getDefault().post(VerifyOtpFragment.ViewOnClick.VERIFY)
    }

    fun afterDigitOneChanged(s: CharSequence) {
        strDigitOne = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_FIRST_DIGIT)

    }

    fun afterDigitTwoChanged(s: CharSequence) {
        strDigitTwo = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_SECOND_DIGIT)
    }

    fun afterDigitThreeChanged(s: CharSequence) {
        strDigitThree = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_THIRD_DIGIT)
    }

    fun afterDigitFourChanged(s: CharSequence) {
        strDigitFour = s.toString()
        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.ENTERED_FOURTH_DIGIT)
    }


    fun verifyUsers(
        email: String
    ) {
        if (mainRepo != null) {

            val strOTP = strDigitOne + strDigitTwo + strDigitThree + strDigitFour
            val verifyOtpRequest = VerifyOtpRequest()

            if (strOTP.length == 4) {
                verifyOtpRequest.email = email
                verifyOtpRequest.otp = strOTP
            } else {
                return
            }

            mainRepo!!.verifyOtp(verifyOtpRequest).enqueue(object : Callback<CreateUserResponse> {
                override fun onResponse(
                    call: Call<CreateUserResponse>, response: Response<CreateUserResponse>
                ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "verifyOtpLogs", ":Success:" + Gson().toJson(response.body())
                        )
                        Toast.makeText(
                            mActivity, response.body()?.message, Toast.LENGTH_SHORT
                        ).show()

                        if (response.body()?.success!!) {
                            EventBus.getDefault().post(response.body())
                        } else {

                            Toast.makeText(
                                mActivity, response.body()?.message, Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)
                        }

                    } else {
                        Log.e("verifyOtpLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)

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

                override fun onFailure(call: Call<CreateUserResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("verifyOtpLogs", ":Failed:" + t.message)
                    Toast.makeText(
                        mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT
                    ).show()

                    EventBus.getDefault().post(VerifyOtpFragment.UpdateEvent.VERIFY_OTP_FAILED)

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
            verifyUsers(email)
        }
    }
}