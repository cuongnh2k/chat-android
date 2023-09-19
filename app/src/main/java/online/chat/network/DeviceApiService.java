package online.chat.network;

import online.chat.network.base.BaseResponse;
import online.chat.network.request.device.ActiveDeviceReq;
import online.chat.network.request.device.LoginReq;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author hieutt (tora262)
 */
public interface DeviceApiService {

    @GET("/api/v1/device")
    Call<BaseResponse<String>> getListDevice(@Header("Authorization") String accessToken,
                                             @Header("User-Agent") String userAgent,
                                             @Query("isActivated") Boolean isActivated);

    @POST("/api/v1/device/active")
    Call<BaseResponse<String>> activeDevice(@Header("User-Agent") String userAgent,
                                            @Body ActiveDeviceReq req);

    @POST("/api/v1/device/login")
    Call<BaseResponse<String>> login(@Header("User-Agent") String userAgent,
                                     @Body LoginReq req);

    @POST("/api/v1/device/refresh-token")
    Call<BaseResponse<String>> refreshToken(@Header("Authorization") String refreshToken,
                                            @Header("User-Agent") String userAgent);
}
