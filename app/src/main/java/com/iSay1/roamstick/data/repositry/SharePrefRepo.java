package com.iSay1.roamstick.data.repositry;

import android.content.SharedPreferences;

import com.iSay1.roamstick.Constants;
import com.iSay1.roamstick.RoamStickApplication;

public class SharePrefRepo {
    private SharedPreferences mcareAlertSharedpref;
    private SharedPreferences.Editor mcareAlertSharedprefEditor;
    private static SharePrefRepo sharePrefRepo;

    private final String TERMS_CONDITION = "termsAndCondition";
    private final String PHONE_NUMBER = "phn_num";
    private final String EMAIL_ID = "email_id";
    private final String OTP_KEY = "OTP";
    private final String DEVICE_LOCATION = "device_location";
    private final String AUTH_TOKEN = "Auth_Token";
    private final String LAT = "latti";
    private final String LONGI = "longi";
    private final String LOCATION_NICK_NAME = "location_nickname";
    private final String CARING_NAME = "caring_name";
    private final String AGE = "age";
    private final String IMG_NAME = "img_name";
    private final String LOCATION_ID = "location_id";

    SharePrefRepo() {
        mcareAlertSharedpref = RoamStickApplication.getApplicationInstance().getSharedPreferences(Constants.ROOM_STICK_SHARED_DATA, 0);
        mcareAlertSharedprefEditor = mcareAlertSharedpref.edit();
    }

    public void clearSharePref() {
        mcareAlertSharedprefEditor.clear();
        mcareAlertSharedprefEditor.commit();
        mcareAlertSharedprefEditor.apply();
    }


    public static SharePrefRepo getInstance() {
        if (sharePrefRepo == null) {
            return sharePrefRepo = new SharePrefRepo();
        } else {
            return sharePrefRepo;
        }
    }

    public void setLocationId(int id) {
        mcareAlertSharedprefEditor.putInt(LOCATION_ID, id);
        mcareAlertSharedprefEditor.apply();
    }

    public int getLocationId() {
        return mcareAlertSharedpref.getInt(LOCATION_ID, -1);
    }

    public void setImageName(String img) {
        mcareAlertSharedprefEditor.putString(IMG_NAME, img);
        mcareAlertSharedprefEditor.apply();
    }

    public String getImgName() {
        return mcareAlertSharedpref.getString(IMG_NAME, null);
    }

    public void setAge(String age) {
        mcareAlertSharedprefEditor.putString(AGE, age);
        mcareAlertSharedprefEditor.apply();
    }

    public String getPersonAge() {
        return mcareAlertSharedpref.getString(AGE, null);
    }

    public void saveCaringPersonName(String name) {
        mcareAlertSharedprefEditor.putString(CARING_NAME, name);
        mcareAlertSharedprefEditor.apply();
    }

    public String getCaringPersonName() {
        return mcareAlertSharedpref.getString(CARING_NAME, null);
    }

    public void setFloatVale(String key, float value) {
        mcareAlertSharedprefEditor.putFloat(key, value);
        mcareAlertSharedprefEditor.apply();
        mcareAlertSharedprefEditor.commit();
    }

    public float getFloatVale(String key) {
        return mcareAlertSharedpref.getFloat(key, 0.0f);
    }

    public void setLocationNickName(String name) {
        mcareAlertSharedprefEditor.putString(LOCATION_NICK_NAME, name);
        mcareAlertSharedprefEditor.apply();
    }

    public String getLocationNickName() {
        return mcareAlertSharedpref.getString(LOCATION_NICK_NAME, null);
    }

    public void putString(String key, String value) {
        mcareAlertSharedprefEditor.putString(key, value);
        mcareAlertSharedprefEditor.apply();
    }

    public String getString(String key) {
        return mcareAlertSharedpref.getString(key, null);
    }

    public void putInt(String key, int value) {
        mcareAlertSharedprefEditor.putInt(key, value);
        mcareAlertSharedprefEditor.apply();
        mcareAlertSharedprefEditor.commit();
    }

    public int getInt(String key) {
        return mcareAlertSharedpref.getInt(key, 0);
    }

    public void putBoolean(String key, boolean value) {
        mcareAlertSharedprefEditor.putBoolean(key, value);
        mcareAlertSharedprefEditor.apply();
        mcareAlertSharedprefEditor.commit();
    }

    public void putTermsAndCondition(String s) {
        mcareAlertSharedprefEditor.putString(TERMS_CONDITION, s);
        mcareAlertSharedprefEditor.apply();
    }

    public void savePhoneNumber(String phone_number) {
        mcareAlertSharedprefEditor.putString(PHONE_NUMBER, phone_number);
        mcareAlertSharedprefEditor.apply();
    }

    public String getPhoneNumber() {
        return mcareAlertSharedpref.getString(PHONE_NUMBER, "");
    }

    public String getTermsAndCondition() {
        return mcareAlertSharedpref.getString(TERMS_CONDITION, "");
    }

    public void setEmailId(String email) {
        mcareAlertSharedprefEditor.putString(EMAIL_ID, email);
        mcareAlertSharedprefEditor.apply();
    }

    public void setOTP(String otp) {
        mcareAlertSharedprefEditor.putString(OTP_KEY, otp);
        mcareAlertSharedprefEditor.apply();
    }

    public void setDeviceLocation(String location) {
        mcareAlertSharedprefEditor.putString(DEVICE_LOCATION, location);
        mcareAlertSharedprefEditor.apply();
    }

    public void seAuthToken(String token) {
        mcareAlertSharedprefEditor.putString(AUTH_TOKEN, token);
        mcareAlertSharedprefEditor.apply();
    }

    public void setLatitude(String latitude) {
        mcareAlertSharedprefEditor.putString(LAT, latitude);
        mcareAlertSharedprefEditor.apply();
    }

    public String getLatitude() {
        return mcareAlertSharedpref.getString(LAT, "");
    }

    public void setLongitude(String Longitude) {
        mcareAlertSharedprefEditor.putString(LONGI, Longitude);
        mcareAlertSharedprefEditor.apply();
    }

    public String getLongitude() {
        return mcareAlertSharedpref.getString(LONGI, "");
    }

    public String getAuthToken() {
        return mcareAlertSharedpref.getString(AUTH_TOKEN, "");
    }

    public String getDeviceLocation() {
        return mcareAlertSharedpref.getString(DEVICE_LOCATION, "");
    }

    public String getOTP() {
        return mcareAlertSharedpref.getString(OTP_KEY, null);
    }

    public String getEmailId() {
        return mcareAlertSharedpref.getString(EMAIL_ID, "");
    }

    public boolean getBoolean(String key) {
        return mcareAlertSharedpref.getBoolean(key, false);
    }

}
