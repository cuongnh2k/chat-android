package online.chat.repository;

import javax.inject.Inject;

import online.chat.network.DeviceApiService;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.device.ActiveDeviceReq;
import online.chat.network.request.device.LoginReq;
import retrofit2.Call;

/**
 * @author hieutt (tora262)
 */
public class DeviceRepository {
    private final DeviceApiService deviceApiService;

    @Inject
    public DeviceRepository(DeviceApiService deviceApiService) {
        this.deviceApiService = deviceApiService;
    }

    public Call<BaseResponse<String>> getListDevice(String accessToken, String userAgent, Boolean isActivated) {
        return deviceApiService.getListDevice(accessToken, userAgent, isActivated);
    }

    public Call<BaseResponse<String>> activeDevice(String userAgent, ActiveDeviceReq req) {
        return deviceApiService.activeDevice(userAgent, req);
    }

    public Call<BaseResponse<String>> login(String userAgent, LoginReq req) {
        return deviceApiService.login(userAgent, req);
    }

    public Call<BaseResponse<String>> refreshToken(String refreshToken, String userAgent) {
        return deviceApiService.refreshToken(refreshToken, userAgent);
    }
}
