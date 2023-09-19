package online.chat.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import online.chat.network.DeviceApiService;
import online.chat.network.FileApiService;
import online.chat.network.UserApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hieutt (tora262)
 */
@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {
    @Singleton
    @Provides
    Retrofit provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl("http://13.229.201.209")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    DeviceApiService deviceApiService(Retrofit retrofit) {
        return retrofit.create(DeviceApiService.class);
    }

    @Singleton
    @Provides
    FileApiService fileApiService(Retrofit retrofit) {
        return retrofit.create(FileApiService.class);
    }

    @Singleton
    @Provides
    UserApiService userApiService(Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }

}
