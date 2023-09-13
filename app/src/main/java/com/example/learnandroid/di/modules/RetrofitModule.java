package com.example.learnandroid.di.modules;

import com.example.learnandroid.data.network.UserApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
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
                .baseUrl("http://ec2-13-229-201-209.ap-southeast-1.compute.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    UserApiService userApiService(Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }

}
