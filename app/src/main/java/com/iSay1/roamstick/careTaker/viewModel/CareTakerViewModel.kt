package com.iSay1.roamstick.careTaker.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iSay1.roamstick.Constants
import com.iSay1.roamstick.MainActivity
import com.iSay1.roamstick.R
import com.iSay1.roamstick.careTaker.CareTakerFragment
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.model.request.DeleteCaretakerRequest
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareTakerViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    var caretakerDetailsResponse: MutableLiveData<CaretakerDetailsResponse?>? = MutableLiveData()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onAddCaretakerClick(view: View) {
        EventBus.getDefault().post(CareTakerFragment.ViewOnClick.ADD_CARETAKER)
    }

    fun getCaretakers() {
        caretakerDetailsResponse?.value = null
        if (mainRepo != null) {

            val userId = sharePrefRepo.getString(Constants.USER_ID)

            mainRepo!!.getCaretakers(userId).enqueue(object : Callback<CaretakerDetailsResponse?> {
                override fun onResponse(
                    call: Call<CaretakerDetailsResponse?>, response: Response<CaretakerDetailsResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        caretakerDetailsResponse?.value = null

                        handleCaretakersResponse(response.body() as CaretakerDetailsResponse)

                    } else {

                        EventBus.getDefault().post(CareTakerFragment.UpdateEvent.HIDE_DIALOG)

                        Log.e("getPropertyTypes", ":FailedOnResponse:")/*val isFirebaseLogsEnable =
                                sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)

                            if (isFirebaseLogsEnable) {
                                FireBaseApiLogs.firebaseLog(
                                    Constants.REQUEST,
                                    mainRepo!!.getTpDevicesByLocationId(locationId)
                                        .request().toString()
                                )
                                FireBaseApiLogs.firebaseLog(
                                    Constants.RESPONSE,
                                    response.toString()
                                )
                                FireBaseApiLogs.publishLog(Constants.API_ERROR)*/
                    }

                }


                override fun onFailure(call: Call<CaretakerDetailsResponse?>, t: Throwable) {
                    val isFirebaseLogsEnable =
                        sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)
                    Log.e("TpDeviceByLocationFetch", ":Failed:" + t.message)

                    t.printStackTrace()

                    /*if (isFirebaseLogsEnable) {
                        FireBaseApiLogs.firebaseLog(
                            Constants.REQUEST,
                            mainRepo!!.getTpDevicesByLocationId(locationId).request()
                                .toString()
                        )
                        FireBaseApiLogs.firebaseLog(Constants.RESPONSE, t.message)
                        FireBaseApiLogs.publishLog(Constants.API_ERROR)
                    }*/
                    Toast.makeText(
                        mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT
                    ).show()
                    EventBus.getDefault().post(CareTakerFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getCaretakers()
        }
    }

    private fun handleCaretakersResponse(mCaretakerDetailsResponse: CaretakerDetailsResponse) {
        caretakerDetailsResponse?.value = mCaretakerDetailsResponse

        Log.e("propertyResponseConv", ":" + caretakerDetailsResponse?.value)
        EventBus.getDefault().post(CareTakerFragment.UpdateEvent.CARETAKER_DATA)
    }


    fun deleteCaretaker(deleteCaretakerRequest: DeleteCaretakerRequest?) {
        if (mainRepo != null) {

            mainRepo!!.deleteCareTaker(deleteCaretakerRequest).enqueue(object : Callback<BaseResponse?> {
                override fun onResponse(
                    call: Call<BaseResponse?>, response: Response<BaseResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e("deleteCaretaker", ":Success:" + Gson().toJson(response.body()))

                        EventBus.getDefault().post(CareTakerFragment.UpdateEvent.DELETE_CARETAKER_SUCCESSFUL)

                    } else {
                        Log.e("deleteCaretaker", ":FailedOnResponse:")/*val isFirebaseLogsEnable =
                                sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)

                            if (isFirebaseLogsEnable) {
                                FireBaseApiLogs.firebaseLog(
                                    Constants.REQUEST,
                                    mainRepo!!.getTpDevicesByLocationId(locationId)
                                        .request().toString()
                                )
                                FireBaseApiLogs.firebaseLog(
                                    Constants.RESPONSE,
                                    response.toString()
                                )
                                FireBaseApiLogs.publishLog(Constants.API_ERROR)*/

                        EventBus.getDefault().post(CareTakerFragment.UpdateEvent.HIDE_DIALOG)
                    }


                }


                override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                    val isFirebaseLogsEnable =
                        sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)
                    Log.e("deleteCaretaker", ":Failed:" + t.message)

                    t.printStackTrace()

                    /*if (isFirebaseLogsEnable) {
                        FireBaseApiLogs.firebaseLog(
                            Constants.REQUEST,
                            mainRepo!!.getTpDevicesByLocationId(locationId).request()
                                .toString()
                        )
                        FireBaseApiLogs.firebaseLog(Constants.RESPONSE, t.message)
                        FireBaseApiLogs.publishLog(Constants.API_ERROR)
                    }*/
                    Toast.makeText(
                        mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT
                    ).show()
                    EventBus.getDefault().post(CareTakerFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            deleteCaretaker(deleteCaretakerRequest)
        }
    }


}