package online.chat.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import online.chat.network.base.BaseResponse;
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
    private final DeviceRepository userRepository;

    private final MutableLiveData<LoginRes> liveData = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    @Inject
    public LoginViewModel(DeviceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String userAgent, LoginReq req) {
        Call<BaseResponse<String>> call = userRepository.login(userAgent, req);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<String>> call, @NonNull Response<BaseResponse<String>> response) {
                Timber.d(response.body().toString());
//                BaseResponse<LoginRes> res = response.body();
//                if (res.isSuccess()) {
//                    liveData.setValue(res.getData());
//                } else {
//                    errorMessageLiveData.setValue(response.body().getMessage());
//                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<String>> call, @NonNull Throwable t) {
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
