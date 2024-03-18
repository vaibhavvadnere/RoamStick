package com.iSay1.roamstick

object Constants {
    const val IS_TESTING_MODE = true

    const val REQUEST = "Request"
    const val RESPONSE = "Response"
//    private const val PROD = "https://app-ca-webapi.azurewebsites.net/"

    private const val DEV = "https://roamstick.com/"
    const val BASE_URL: String = BuildConfig.BASE_URL

//    const val BASE_URL: String = DEV;

    //    const val BASE_URL: String = PROD;

    const val TOTAL_SCREEN = 3

    //Shared Preferences
    const val ROOM_STICK_SHARED_DATA = "RoomStick_SharedData"
    const val IS_FIREBASE_LOGS_ENABLE = "is_firebase_logs_enable"

    const val PLATFORM = "Platform"
    const val BEARER = "Bearer "
    const val X_CUSTOMHEADER_VALUE = "rhtcpoinvsp"

    //User fields
    const val EMAIL = "EMAIL"
    const val USER_ID = "USER_ID"
    const val USER_TYPE = "USER_TYPE"
    const val IS_LOGGED_IN = "IS_LOGGED_IN"
    const val FIRST_NAME = "FIRST_NAME"
    const val LAST_NAME = "LAST_NAME"


    const val VERTICAL = "vertical"
    const val HORIZONTAL = "horizontal"
    const val SELECTIVE = "selective"
    const val DETAILED = "detailed"
    const val MODAL_BOTTOM_SHEET = "ModalBottomSheet"

    const val SUCCESS = "success"
    const val MESSAGE = "message"
    const val USER_DATA = "user_data"
    const val PROPERTY_DATA = "property_data"
    const val PROPERTY_ID = "property_id"
    const val PROPERTY_TYPE = "property_type"
    const val CARETAKER_DATA = "caretaker_data"


    enum class ThirdPartyDeviceStatus(val step: Int) {
        UNKNOWN(1), VERIFIED(2), INVALID(3)
    }

    enum class ThirdPartyDeviceCategories(val category: Int) {
        BLOOD_PRESSURE_MONITOR(1), PULSE_OXIMETER(2), CALL_BUTTON_KKM_B2(3), MAGNETIC_DOOR_SENSOR(4), CALL_BUTTON_KKM_K21(5), INFRARED_THERMOMETER(6), WEIGHT_SCALE(
            7
        )
    }

    enum class VerificationSteps(val step: Int) {
        STEP_ONE(1), STEP_TWO(2), STEP_THREE(3)
    }

    enum class UserState(val state: Int) {
        EMAIL(1), LOCATION(2), SURVEY_QUESTION(3), DEVICE(4), COMPLETED(5), OTP(6)
    }
}