package com.theost.retrofitapp.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String API_BASE_URL = "https://predictor.yandex.net/api/v1/";
    public static final String API_KEY = "pdct.1.1.20220313T131708Z.36c9b04125f377e8.fa3b131fc1daa5a73b58886387ab5babd326fd3b";
    public static final String API_LANGUAGE = "en";
    public static final Integer API_SUGGESTIONS_LIMIT = 3;

    private static Api mInstance;

    public static Api getInstance() {
        if (mInstance == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(loggingInterceptor)
                    .build();

            mInstance = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api.class);
        }

        return mInstance;
    }

}
