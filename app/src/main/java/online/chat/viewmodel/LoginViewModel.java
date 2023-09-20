package online.chat.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.device.ActiveDeviceReq;
import online.chat.network.request.device.LoginReq;
import online.chat.network.response.device.LoginRes;
import online.chat.repository.DeviceRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author hieutt (tora262)
 */
@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final DeviceRepository deviceRepository;

    private final MutableLiveData<LoginRes> loginSuccess = new MutableLiveData<>();

    private final MutableLiveData<String> loginFail = new MutableLiveData<>();

    private final MutableLiveData<String> activeDeviceSuccess = new MutableLiveData<>();

    private final MutableLiveData<String> activeDeviceFail = new MutableLiveData<>();

    @Inject
    public LoginViewModel(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public void login(String userAgent, LoginReq req) {
        Call<BaseResponse> call = deviceRepository.login(userAgent, req);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                Timber.d(response.body().toString());
                BaseResponse res = response.body();
                if (res.isSuccess()) {
                    loginSuccess.setValue(new Gson().fromJson(new Gson().toJson(res.getData()), LoginRes.class));
                } else {
                    loginFail.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Timber.d(t);
                loginFail.setValue(t.getMessage());
            }
        });
    }

    public LiveData<LoginRes> loginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> loginFail() {
        return loginFail;
    }

    public void activeDevice(String userAgent, ActiveDeviceReq req) {
        Call<BaseResponse> call = deviceRepository.activeDevice(userAgent, req);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                Timber.d(response.body().toString());
                BaseResponse res = response.body();
                if (res.isSuccess()) {
                    activeDeviceSuccess.setValue("");
                } else {
                    activeDeviceFail.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Timber.d(t);
                activeDeviceFail.setValue(t.getMessage());
            }
        });
    }

    public LiveData<String> activeDeviceSuccess() {
        return activeDeviceSuccess;
    }

    public LiveData<String> activeDeviceFail() {
        return activeDeviceFail;
    }
}
