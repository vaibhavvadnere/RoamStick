package com.iSay1.roamstick.data.httpclient;


import com.iSay1.roamstick.data.model.response.Post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("/api/Account/CreateUser")
    Call<Post> createPost(@Field("PhoneNumber") String phone_number);

}
