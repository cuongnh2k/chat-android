package online.chat.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.RegisterReq;
import online.chat.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author hieutt (tora262)
 */
@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;

    private final MutableLiveData<String> liveData = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String userAgent, RegisterReq req) {
        Call<BaseResponse> call = userRepository.register(userAgent, req);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                Timber.d(response.body().toString());
                BaseResponse res = response.body();
                if (!res.isSuccess()) {
                    errorMessageLiveData.setValue(response.body().getMessage());
                }
                else {
                    liveData.setValue("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Timber.d(t);
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public LiveData<Object> getLiveData() {
        return liveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }
}
