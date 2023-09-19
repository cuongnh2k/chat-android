package online.chat.repository;

import online.chat.network.UserApiService;
import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.LoginReq;
import online.chat.network.response.user.LoginRes;

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
