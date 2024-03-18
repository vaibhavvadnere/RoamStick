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
import com.iSay1.roamstick.data.model.request.AddPropertyRequest
import com.iSay1.roamstick.data.model.response.AddPropertyResponse
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.UpdatePropertyResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.property.AddPropertyFragment
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPropertyViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    var statesResponse: MutableLiveData<StatesResponse?>? = MutableLiveData()
    var caretakerDetailsResponse: MutableLiveData<CaretakerDetailsResponse?>? = MutableLiveData()

    /*var propertyTypesResponse: MutableLiveData<PropertyTypesResponse?>? = MutableLiveData()
    var amenitiesResponse: MutableLiveData<AmenitiesResponse?>? = MutableLiveData()
    var complimentariesResponse: MutableLiveData<ComplimentariesResponse?>? = MutableLiveData()
    var unitChargesTypesResponse: MutableLiveData<UnitChargesTypesResponse?>? = MutableLiveData()*/


    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    fun onSelectStateClicked(view: View) {
        EventBus.getDefault().post(AddPropertyFragment.ViewOnClick.GET_STATES)
    }

    //Function to handle Yes Click
    fun onCreatePropertyClick(view: View) {
        EventBus.getDefault().post(AddPropertyFragment.ViewOnClick.CREATE_PROPERTY)
    }

    fun onCaretakerClicked(view: View) {
        EventBus.getDefault().post(AddPropertyFragment.ViewOnClick.GET_CARETAKER)
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
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        statesResponse?.value = null

                        handleStatesResponse(response.body() as StatesResponse)

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

                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.HIDE_DIALOG)
                }

                override fun onFailure(call: Call<StatesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getStates()
        }
    }

    private fun handleStatesResponse(mStatesResponse: StatesResponse) {
        statesResponse?.value = mStatesResponse

        EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.STATES_DATA)
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
                            "getCareTakers", ":Success:" + Gson().toJson(response.body())
                        )

                        caretakerDetailsResponse?.value = null

                        handleCaretakersResponse(response.body() as CaretakerDetailsResponse)

                    } else {
                        Log.e("getCareTakers", ":FailedOnResponse:")/*val isFirebaseLogsEnable =
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

                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.HIDE_DIALOG)
                }


                override fun onFailure(call: Call<CaretakerDetailsResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getCaretakers()
        }
    }

    private fun handleCaretakersResponse(mCaretakerDetailsResponse: CaretakerDetailsResponse) {
        caretakerDetailsResponse?.value = mCaretakerDetailsResponse

        Log.e("getCareTakersConv", ":" + caretakerDetailsResponse?.value)
        EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.CARETAKER_DATA)
    }

    fun addProperty(
        addPropertyRequest: AddPropertyRequest
    ) {
        if (mainRepo != null) {

            mainRepo!!.addProperty(addPropertyRequest).enqueue(object : Callback<AddPropertyResponse> {
                override fun onResponse(
                    call: Call<AddPropertyResponse>, response: Response<AddPropertyResponse>
                ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "AddPropertyLogs", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()!!.success) {

                            Toast.makeText(
                                mActivity, "Property added successfully", Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(
                                mActivity, response.body()?.message, Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.ADD_PROPERTY_FAILED)

                        }


                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.ADD_PROPERTY_FAILED)

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

                override fun onFailure(call: Call<AddPropertyResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("loginLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.ADD_PROPERTY_FAILED)


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
            addProperty(addPropertyRequest)
        }
    }

    fun updateProperty(
        propertyID: String,
        addPropertyRequest: AddPropertyRequest
    ) {
        if (mainRepo != null) {

            mainRepo!!.updateProperty(propertyID, addPropertyRequest).enqueue(object : Callback<UpdatePropertyResponse> {
                override fun onResponse(
                    call: Call<UpdatePropertyResponse>, response: Response<UpdatePropertyResponse>
                ) {/*if (response.code() == 401) {
                            mActivity!!.refreshToken()
                        }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "AddPropertyLogs", ":Success:" + Gson().toJson(response.body())
                        )

                        if (response.body()!!.success) {

                            Toast.makeText(
                                mActivity, "Property updated successfully", Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(
                                mActivity, response.body()?.message, Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.UPDATE_PROPERTY_FAILED)

                        }


                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.UPDATE_PROPERTY_FAILED)

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

                override fun onFailure(call: Call<UpdatePropertyResponse>, t: Throwable) {
                    t.printStackTrace()


                    Log.e("loginLogs", ":Failed:" + t.message)
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(AddPropertyFragment.UpdateEvent.UPDATE_PROPERTY_FAILED)


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
            addProperty(addPropertyRequest)
        }
    }
}