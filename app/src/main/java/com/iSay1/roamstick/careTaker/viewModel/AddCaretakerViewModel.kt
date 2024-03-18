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
import com.iSay1.roamstick.careTaker.AddCaretakerFragment
import com.iSay1.roamstick.data.api.ApiHelper
import com.iSay1.roamstick.data.api.ApiServiceImpl
import com.iSay1.roamstick.data.model.request.AddCareTakerRequest
import com.iSay1.roamstick.data.model.request.AddPropertyRequest
import com.iSay1.roamstick.data.model.response.AddPropertyResponse
import com.iSay1.roamstick.data.model.response.AmenitiesResponse
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.ComplimentariesResponse
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.PropertyTypesResponse
import com.iSay1.roamstick.data.model.response.UnitChargesTypesResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.property.AddPropertyFragment
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCaretakerViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    var statesResponse: MutableLiveData<StatesResponse?>? = MutableLiveData()

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onAddCaretakerClicked(view: View) {
        EventBus.getDefault().post(AddCaretakerFragment.ViewOnClick.ADD_CARETAKER)
    }

    fun onSelectStateClicked(view: View) {
        EventBus.getDefault().post(AddCaretakerFragment.ViewOnClick.GET_STATES)
    }

    fun getStates() {
        statesResponse?.value = null
        if (mainRepo != null) {

            mainRepo!!.states.enqueue(object : Callback<StatesResponse?> {
                override fun onResponse(
                    call: Call<StatesResponse?>, response: Response<StatesResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getStates", ":Success:" + Gson().toJson(response.body())
                        )

                        statesResponse?.value = null

                        handleStatesResponse(response.body() as StatesResponse)

                    } else {
                        Log.e("getStates", ":FailedOnResponse:")/*val isFirebaseLogsEnable =
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

                    EventBus.getDefault().post(AddCaretakerFragment.UpdateEvent.HIDE_DIALOG)
                }

                override fun onFailure(call: Call<StatesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddCaretakerFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getStates()
        }
    }

    private fun handleStatesResponse(mStatesResponse: StatesResponse) {
        statesResponse?.value = mStatesResponse

        EventBus.getDefault().post(AddCaretakerFragment.UpdateEvent.STATES_DATA)
    }


    fun addCareTaker(
        addCareTakerRequest: AddCareTakerRequest
    ) {
        if (mainRepo != null) {

            mainRepo!!.addCareTaker(addCareTakerRequest)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>, response: Response<BaseResponse>
                    ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                        if (response.isSuccessful) {
                            Log.e(
                                "AddPropertyLogs", ":Success:" + Gson().toJson(response.body())
                            )

                            if (response.body()!!.success) {

                                Toast.makeText(
                                    mActivity, "Caretaker added successfully", Toast.LENGTH_SHORT
                                ).show()

                                EventBus.getDefault().post(response.body())
                            } else {
                                Toast.makeText(
                                    mActivity, response.body()?.message, Toast.LENGTH_SHORT
                                ).show()

                                EventBus.getDefault()
                                    .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)

                            }


                        } else {
                            Log.e("loginLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                            EventBus.getDefault()
                                .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)

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

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        t.printStackTrace()


                        Log.e("loginLogs", ":Failed:" + t.message)
                        Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                            .show()

                        EventBus.getDefault()
                            .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)


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
            addCareTaker(addCareTakerRequest)
        }
    }

    fun updateCaretaker(
        careTakerId: String?,
        addCareTakerRequest: AddCareTakerRequest
    ) {
        if (mainRepo != null) {

            mainRepo!!.updateCaretaker(careTakerId, addCareTakerRequest)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>, response: Response<BaseResponse>
                    ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                        if (response.isSuccessful) {
                            Log.e(
                                "AddCaretakerLogs", ":Success:" + Gson().toJson(response.body())
                            )

                            if (response.body()!!.success) {

                                Toast.makeText(
                                    mActivity, "Caretaker updated successfully", Toast.LENGTH_SHORT
                                ).show()

                                EventBus.getDefault().post(response.body())
                            } else {
                                Toast.makeText(
                                    mActivity, response.body()?.message, Toast.LENGTH_SHORT
                                ).show()

                                EventBus.getDefault()
                                    .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)
                            }


                        } else {
                            Log.e("AddCaretakerLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                            EventBus.getDefault()
                                .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)

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

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        t.printStackTrace()


                        Log.e("AddCaretakerLogs", ":Failed:" + t.message)
                        Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                            .show()

                        EventBus.getDefault()
                            .post(AddCaretakerFragment.UpdateEvent.ADD_CARETAKER_FAILED)


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
            addCareTaker(addCareTakerRequest)
        }
    }

}