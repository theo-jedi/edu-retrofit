package com.theost.retrofitapp.retrofit;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("predict.json/complete")
    Single<PredictedTextDto> getPredictedText(
            @Query("q") String text,
            @Query("lang") String language,
            @Query("limit") Integer limit,
            @Query("key") String key
    );

}
