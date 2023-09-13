package com.example.learnandroid.repository;

import com.example.learnandroid.data.network.UserApiService;
import com.example.learnandroid.data.network.base.BaseResponse;
import com.example.learnandroid.data.network.request.user.LoginReq;
import com.example.learnandroid.data.network.response.user.LoginRes;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * @author hieutt (tora262)
 */
public class UserRepository {
    private final UserApiService userApiService;

    @Inject
    public UserRepository(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    public Call<BaseResponse<LoginRes>> login(LoginReq loginReq) {
        return userApiService.login(loginReq);
    }
}
