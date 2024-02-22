package com.iSay1.roamstick.data.api;


import com.iSay1.roamstick.data.httpclient.HttpClient;
import com.iSay1.roamstick.data.model.response.Post;

import retrofit2.Call;

public class ApiServiceImpl implements ApiService {

    @Override
    public Call<Post> createPost(String phone_number) {
        return HttpClient.getHttpApi().createPost(phone_number);
    }


}
