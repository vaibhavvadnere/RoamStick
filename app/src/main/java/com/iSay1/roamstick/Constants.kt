package com.iSay1.roamstick

object Constants {
    const val IS_TESTING_MODE = false

    const val REQUEST = "Request"
    const val RESPONSE = "Response"
    private const val PROD = "https://app-ca-webapi.azurewebsites.net/"
    private const val DEV = "https://carealertdev.azurewebsites.net/"
    //    const val BASE_URL: String = BuildConfig.BASE_URL

    const val BASE_URL: String = DEV;

    //    const val BASE_URL: String = PROD;

    const val TOTAL_SCREEN = 3

    //Shared Preferences
    const val FALCON_EV_CHARGER_SHARED_DATA = "FalconEvCharger_SharedData"

    const val PLATFORM = "Platform"
    const val BEARER = "Bearer "
    const val X_CUSTOMHEADER_VALUE = "rhtcpoinvsp"

    enum class ThirdPartyDeviceStatus(val step: Int) {
        UNKNOWN(1),
        VERIFIED(2),
        INVALID(3)
    }

    enum class ThirdPartyDeviceCategories(val category: Int) {
        BLOOD_PRESSURE_MONITOR(1),
        PULSE_OXIMETER(2),
        CALL_BUTTON_KKM_B2(3),
        MAGNETIC_DOOR_SENSOR(4),
        CALL_BUTTON_KKM_K21(5),
        INFRARED_THERMOMETER(6),
        WEIGHT_SCALE(7)
    }

    enum class VerificationSteps(val step: Int) {
        STEP_ONE(1),
        STEP_TWO(2),
        STEP_THREE(3)
    }

    enum class UserState(val state: Int) {
        EMAIL(1),
        LOCATION(2),
        SURVEY_QUESTION(3),
        DEVICE(4),
        COMPLETED(5),
        OTP(6)
    }
}