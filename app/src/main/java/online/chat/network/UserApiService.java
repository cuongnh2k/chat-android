package online.chat.network;

import online.chat.network.base.BaseResponse;
import online.chat.network.request.user.ActiveUserReq;
import online.chat.network.request.user.RegisterReq;
import online.chat.network.request.user.UpdateUserReq;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author hieutt (tora262)
 */
public interface UserApiService {
    @GET("/api/v1/user")
    Call<BaseResponse> getDetailUser(@Header("Authorization") String accessToken,
                                     @Header("User-Agent") String userAgent);

    @GET("/api/v1/user/new-password")
    Call<BaseResponse> getNewPassword(@Header("User-Agent") String userAgent,
                                      @Query("email") String email);

    @PATCH("/api/v1/user")
    Call<BaseResponse> updateUser(@Header("Authorization") String accessToken,
                                  @Header("User-Agent") String userAgent,
                                  @Body UpdateUserReq req);

    @POST("/api/v1/user/active")
    Call<BaseResponse> activeUser(@Header("User-Agent") String userAgent,
                                  @Body ActiveUserReq req);

    @POST("/api/v1/user/active")
    Call<BaseResponse> register(@Header("User-Agent") String userAgent,
                                @Body RegisterReq req);
}
