package com.iSay1.roamstick;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class RoamStickApplication extends Application {
    private static Application application;
    public static Context mContext;
    public MainActivity mActivity;

//    private static JobManager jobManager;
//    private static Provider provider;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mContext = this;

//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        /*ComponentName componentName = new ComponentName(
                mContext,
                MyFirebaseMessagingService.class);*/

        /*mContext.getPackageManager().setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/
    }

    public static Application getApplicationInstance() {
        return application;
    }

}
