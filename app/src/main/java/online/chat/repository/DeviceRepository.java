package online.chat.repository;

import online.chat.network.DeviceApiService;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.LoginReq;
import online.chat.network.response.user.LoginRes;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * @author hieutt (tora262)
 */
public class DeviceRepository {
    private final DeviceApiService userApiService;

    @Inject
    public DeviceRepository(DeviceApiService userApiService) {
        this.userApiService = userApiService;
    }

    public Call<BaseResponse<LoginRes>> login(LoginReq loginReq) {
        return userApiService.login(loginReq);
    }
}
