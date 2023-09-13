package com.example.learnandroid.data.network;

import com.example.learnandroid.data.network.base.BaseResponse;
import com.example.learnandroid.data.network.request.user.LoginReq;
import com.example.learnandroid.data.network.response.user.LoginRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author hieutt (tora262)
 */
public interface UserApiService {

    @POST("api/v1/user/login")
    Call<BaseResponse<LoginRes>> login(@Body LoginReq loginReq);
}
