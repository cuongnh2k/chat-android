package online.chat.network;

import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.LoginReq;
import online.chat.network.response.user.LoginRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author hieutt (tora262)
 */
public interface UserApiService {

    @POST("/api/v1/device/login")
    Call<BaseResponse<LoginRes>> login(@Body LoginReq loginReq);
}
