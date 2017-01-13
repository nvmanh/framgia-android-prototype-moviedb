package com.nvmanh.themoviedb.data.source.remote;

import com.nvmanh.themoviedb.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class RequestHelper {
    public static APIService getRequest() {
        return getRequest(true);
    }

    public static APIService getRequest(boolean isUseAuthentication) {
        HttpLoggingInterceptor interceptor = getHttpLoggingInterceptor();
        OkHttpClient client;
        if (isUseAuthentication) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            client = httpClient.addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        }

        Retrofit retrofit =
                new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(APIService.BASE_URL)
                        .client(client)
                        .build();
        return retrofit.create(APIService.class);
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }
}