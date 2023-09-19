package online.chat.repository;

import javax.inject.Inject;

import online.chat.network.UserApiService;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.ActiveUserReq;
import online.chat.network.request.user.RegisterReq;
import online.chat.network.request.user.UpdateUserReq;
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

    public Call<BaseResponse<String>> getDetailUser(String accessToken, String userAgent) {
        return userApiService.getDetailUser(accessToken, userAgent);
    }

    public Call<BaseResponse<String>> getNewPassword(String userAgent, String email) {
        return userApiService.getNewPassword(userAgent, email);
    }

    public Call<BaseResponse<String>> updateUser(String accessToken, String userAgent, UpdateUserReq req) {
        return userApiService.updateUser(accessToken, userAgent, req);
    }

    public Call<BaseResponse<String>> activeUser(String userAgent, ActiveUserReq req) {
        return userApiService.activeUser(userAgent, req);
    }

    public Call<BaseResponse<String>> register(String userAgent, RegisterReq req) {
        return userApiService.register(userAgent, req);
    }
}
