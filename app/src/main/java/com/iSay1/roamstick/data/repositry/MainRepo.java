package com.iSay1.roamstick.data.repositry;


import com.iSay1.roamstick.data.api.ApiHelper;
import com.iSay1.roamstick.data.model.response.Post;

import retrofit2.Call;

public class MainRepo {
    private final ApiHelper mApiHelper;

    public MainRepo(ApiHelper mApiHelper) {
        this.mApiHelper = mApiHelper;
    }


    public Call<Post> creteUser(String phnNumber) {
        return mApiHelper.createPost(phnNumber);
    }

}
