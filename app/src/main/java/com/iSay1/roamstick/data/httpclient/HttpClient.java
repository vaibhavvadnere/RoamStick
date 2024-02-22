package com.iSay1.roamstick.data.httpclient;


import static com.iSay1.roamstick.Constants.BASE_URL;

import android.util.Log;

import com.iSay1.roamstick.Constants;
import com.iSay1.roamstick.data.repositry.SharePrefRepo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpClient {

    private static OkHttpClient getHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.MINUTES)
                .connectTimeout(10, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                       /* SharedPreferences CareAlert_SharedData = CareAlertApplication.getApplicationInstance().getSharedPreferences("CareAlert_SharedData", 0);
                        String auth_token = CareAlert_SharedData.getString(Constants.AUTH_TOKEN, "");*/

                        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();

//                        String auth_token = sharePrefRepo.getString(Constants.AUTH_TOKEN);

//                        Log.e("auth_token_Log", ":" + auth_token);
                        Log.e("auth_token_Log", ":" + Constants.X_CUSTOMHEADER_VALUE);

                        Request request = chain.request()
                                .newBuilder()
                                /*.addHeader(Constants.AUTHORIZTION, Constants.BEARER + auth_token)
                                .addHeader(Constants.X_CUSTOMHEADER, Constants.X_CUSTOMHEADER_VALUE)
                                .addHeader(Constants.LANGUAGE_CODE, LocaleManager.getLanguageCode())*/
                                .build();
                        return chain.proceed(request);
                    }
                }).addInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);

                    Log.e("addInterceptorLogs", ":" + response.code());
                    if (response.code() != 200) {

                        SharePrefRepo sharePrefRepo = SharePrefRepo.getInstance();
                        boolean isFirebaseLogsEnable = true;

                        Log.e("addInterceptorLogs", " : Event :" + isFirebaseLogsEnable);

                        if (isFirebaseLogsEnable) {
//                            firebaseLog(Constants.REQUEST, request.toString());
//                            firebaseLog(Constants.RESPONSE, response.toString());
//                            publishLog(Constants.API_ERROR);

                            Log.e("addInterceptorLogs", " : Event :" + response.code());
                            Log.e("addInterceptorLogs", " : req :" + request.toString());
                            Log.e("addInterceptorLogs", " : res :" + response.toString());

                        }
                    }
                    return response;
                }).build();

        return okHttpClient;

    }

    public static Api getHttpApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(Api.class);
    }
}
