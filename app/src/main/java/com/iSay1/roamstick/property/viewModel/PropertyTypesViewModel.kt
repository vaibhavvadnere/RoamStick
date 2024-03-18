package com.iSay1.roamstick.property.viewModel

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
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.model.request.DeletePropertyTypesRequest
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.PropertyTypesDetailsResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.property.PropertyTypesFragment
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyTypesViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    var propertyTypesDetailsResponse: MutableLiveData<PropertyTypesDetailsResponse?>? = MutableLiveData()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onAddPropertyClick(view: View) {
        EventBus.getDefault().post(PropertyTypesFragment.ViewOnClick.ADD_PROPERTY_TYPE)
    }

    fun deletePropertyType(deletePropertyTypesRequest: DeletePropertyTypesRequest?) {
        if (mainRepo != null) {

            val userId = sharePrefRepo.getString(Constants.USER_ID)

            mainRepo!!.deletePropertyType(deletePropertyTypesRequest).enqueue(object : Callback<BaseResponse?> {
                override fun onResponse(
                    call: Call<BaseResponse?>, response: Response<BaseResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.DELETE_PROPERTY_TYPES_SUCCESS)

                    } else {
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

                        EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.HIDE_DIALOG)
                    }


                }


                override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                    val isFirebaseLogsEnable = sharePrefRepo.getBoolean(Constants.IS_FIREBASE_LOGS_ENABLE)
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
                    EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            deletePropertyType(deletePropertyTypesRequest)
        }
    }


    fun getPropertyTypesDetails(propertyID: String) {
        if (mainRepo != null) {

            propertyTypesDetailsResponse?.value = null

            mainRepo!!.getPropertyTypesDetails(propertyID).enqueue(object : Callback<PropertyTypesDetailsResponse?> {
                override fun onResponse(
                    call: Call<PropertyTypesDetailsResponse?>, response: Response<PropertyTypesDetailsResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        propertyTypesDetailsResponse?.value = null

                        propertyTypesResponse(response.body() as PropertyTypesDetailsResponse)

                    } else {
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

                    EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.HIDE_DIALOG)
                }


                override fun onFailure(call: Call<PropertyTypesDetailsResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getPropertyTypesDetails(propertyID)
        }
    }

    private fun propertyTypesResponse(mPropertyTypesDetailsResponse: PropertyTypesDetailsResponse) {
        propertyTypesDetailsResponse?.value = mPropertyTypesDetailsResponse

        Log.e("propertyResponseConv", ":" + propertyTypesDetailsResponse?.value)
        EventBus.getDefault().post(PropertyTypesFragment.UpdateEvent.PROPERTY_TYPES_DATA)
    }


}