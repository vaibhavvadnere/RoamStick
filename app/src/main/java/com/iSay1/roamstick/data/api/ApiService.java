package com.iSay1.roamstick.data.api;

import com.iSay1.roamstick.data.model.response.Post;

import retrofit2.Call;

public interface ApiService {

    Call<Post> createPost(String phone_number);
}


