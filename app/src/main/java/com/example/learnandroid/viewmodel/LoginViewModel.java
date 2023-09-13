package com.example.learnandroid.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.learnandroid.data.network.base.BaseResponse;
import com.example.learnandroid.data.network.request.user.LoginReq;
import com.example.learnandroid.data.network.response.user.LoginRes;
import com.example.learnandroid.repository.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author hieutt (tora262)
 */
@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;

    private final MutableLiveData<LoginRes> liveData = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    @Inject
    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(LoginReq loginReq) {
        Call<BaseResponse<LoginRes>> call = userRepository.login(loginReq);
        call.enqueue(new Callback<BaseResponse<LoginRes>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<LoginRes>> call, @NonNull Response<BaseResponse<LoginRes>> response) {
                Timber.d(response.body().toString());
                BaseResponse<LoginRes> res = response.body();
                if (res.isSuccess()) {
                    liveData.setValue(res.getData());
                } else {
                    errorMessageLiveData.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<LoginRes>> call, @NonNull Throwable t) {
                Timber.d(t);
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public LiveData<LoginRes> getLiveData() {
        return liveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }
}
