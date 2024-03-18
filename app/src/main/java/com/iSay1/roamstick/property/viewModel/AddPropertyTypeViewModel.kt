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
import com.iSay1.roamstick.data.model.request.AddPropertyTypesRequest
import com.iSay1.roamstick.data.model.response.AddPropertyResponse
import com.iSay1.roamstick.data.model.response.AmenitiesResponse
import com.iSay1.roamstick.data.model.response.BaseResponse
import com.iSay1.roamstick.data.model.response.CaretakerDetailsResponse
import com.iSay1.roamstick.data.model.response.ComplimentariesResponse
import com.iSay1.roamstick.data.model.response.PropertyTypesResponse
import com.iSay1.roamstick.data.model.response.StatesResponse
import com.iSay1.roamstick.data.model.response.UnitChargesTypesResponse
import com.iSay1.roamstick.data.repositry.MainRepo
import com.iSay1.roamstick.data.repositry.SharePrefRepo
import com.iSay1.roamstick.property.AddPropertyTypeFragment
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPropertyTypeViewModel : ViewModel() {

    private var mainRepo: MainRepo? = null

    @SuppressLint("StaticFieldLeak")
    var mActivity: MainActivity? = null

    private val sharePrefRepo = SharePrefRepo.getInstance()

    var propertyTypesResponse: MutableLiveData<PropertyTypesResponse?>? = MutableLiveData()
    var amenitiesResponse: MutableLiveData<AmenitiesResponse?>? = MutableLiveData()
    var complimentariesResponse: MutableLiveData<ComplimentariesResponse?>? = MutableLiveData()
    var unitChargesTypesResponse: MutableLiveData<UnitChargesTypesResponse?>? = MutableLiveData()

    fun init(mainActivity: MainActivity) {
        mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
        mActivity = mainActivity
    }

    //Function to handle Yes Click
    fun onPropertyTypesClicked(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.GET_PROPERTY_TYPES)
    }

    fun onUnitChargesTypesClicked(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.GET_UNIT_CHARGES_TYPES)
    }

    fun onAmenitiesClicked(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.GET_AMENITIES)
    }

    fun onComplimentaryClicked(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.GET_COMPLIMENTARY)
    }

    fun onValidFromDateClick(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.VALID_FROM_DATE)
    }

    fun onValidToDateClick(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.VALID_TO_DATE)
    }

    fun onCreatePropertyTypeClick(view: View) {
        EventBus.getDefault().post(AddPropertyTypeFragment.ViewOnClick.ADD_PROPERTY_TYPE)
    }

    fun getPropertyTypes() {
        propertyTypesResponse?.value = null
        if (mainRepo != null) {

            mainRepo!!.propertyTypes.enqueue(object : Callback<PropertyTypesResponse?> {
                override fun onResponse(
                    call: Call<PropertyTypesResponse?>, response: Response<PropertyTypesResponse?>
                ) {/*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        propertyTypesResponse?.value = null

                        handlePropertyTypeResponse(response.body() as PropertyTypesResponse)

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

                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }


                override fun onFailure(call: Call<PropertyTypesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getPropertyTypes()
        }
    }

    private fun handlePropertyTypeResponse(mPropertyTypesResponse: PropertyTypesResponse) {
        propertyTypesResponse?.value = mPropertyTypesResponse

        Log.e("propertyTypesResponseConv", ":" + propertyTypesResponse?.value)
        EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.PROPERTY_TYPE_DATA)
    }

    fun getUnitChargesTypes() {
        unitChargesTypesResponse?.value = null
        if (mainRepo != null) {

            mainRepo!!.unitChargesTypes.enqueue(object : Callback<UnitChargesTypesResponse?> {
                override fun onResponse(
                    call: Call<UnitChargesTypesResponse?>, response: Response<UnitChargesTypesResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        unitChargesTypesResponse?.value = null

                        handleUnitChargesResponse(response.body() as UnitChargesTypesResponse)

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

                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }


                override fun onFailure(call: Call<UnitChargesTypesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getPropertyTypes()
        }
    }

    private fun handleUnitChargesResponse(mUnitChargesTypesResponse: UnitChargesTypesResponse) {
        unitChargesTypesResponse?.value = mUnitChargesTypesResponse

        Log.e("unitChargesTypesConv", ":" + unitChargesTypesResponse?.value)
        EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.UNIT_CHARGES_TYPES_DATA)
    }

    fun getAmenities() {
        amenitiesResponse?.value = null
        if (mainRepo != null) {

            mainRepo!!.aminities.enqueue(object : Callback<AmenitiesResponse?> {
                override fun onResponse(
                    call: Call<AmenitiesResponse?>, response: Response<AmenitiesResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        amenitiesResponse?.value = null

                        handleAmenitiesTypeResponse(response.body() as AmenitiesResponse)

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

                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }

                override fun onFailure(call: Call<AmenitiesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getAmenities()
        }
    }

    private fun handleAmenitiesTypeResponse(mAmenitiesResponse: AmenitiesResponse) {
        amenitiesResponse?.value = mAmenitiesResponse

        EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.AMINITIES_DATA)
    }

    fun getComplimentary() {
        complimentariesResponse?.value = null
        if (mainRepo != null) {

            mainRepo!!.complimentary.enqueue(object : Callback<ComplimentariesResponse?> {
                override fun onResponse(
                    call: Call<ComplimentariesResponse?>, response: Response<ComplimentariesResponse?>
                ) {

                    /*if (response.code() == 401) {
                        mActivity!!.refreshToken()
                    }*/

                    if (response.isSuccessful) {
                        Log.e(
                            "getPropertyTypes", ":Success:" + Gson().toJson(response.body())
                        )

                        complimentariesResponse?.value = null

                        handleComplimentariesResponse(response.body() as ComplimentariesResponse)

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

                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }

                override fun onFailure(call: Call<ComplimentariesResponse?>, t: Throwable) {
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
                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.HIDE_DIALOG)
                }
            })
        } else {
            mainRepo = MainRepo(ApiHelper(ApiServiceImpl()))
            getAmenities()
        }
    }

    private fun handleComplimentariesResponse(mComplimentariesResponse: ComplimentariesResponse) {
        complimentariesResponse?.value = mComplimentariesResponse

        EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.COMPLIMENTARIES_DATA)
    }

    fun addPropertyType(
        addPropertyTypesRequest: AddPropertyTypesRequest
    ) {
        if (mainRepo != null) {

            mainRepo!!.addPropertyTypes(addPropertyTypesRequest).enqueue(object : Callback<BaseResponse> {
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
                            EventBus.getDefault().post(response.body())
                        } else {
                            Toast.makeText(
                                mActivity, response.body()?.message, Toast.LENGTH_SHORT
                            ).show()

                            EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.ADD_PROPERTY_TYPES_FAILED)
                        }


                    } else {
                        Log.e("loginLogs", ":FailedOnResponse:")
//                            Toast.makeText(mActivity, response.body()?.message, Toast.LENGTH_SHORT).show()

                        EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.ADD_PROPERTY_TYPES_FAILED)

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
                    Toast.makeText(mActivity, R.string.something_went_wrong, Toast.LENGTH_SHORT).show()

                    EventBus.getDefault().post(AddPropertyTypeFragment.UpdateEvent.ADD_PROPERTY_TYPES_FAILED)


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
            addPropertyType(addPropertyTypesRequest)
        }
    }

}