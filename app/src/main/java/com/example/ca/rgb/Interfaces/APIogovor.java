package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIogovor {
    @GET("/RGB/score_read.php")
    Call<String> setQuery(@Query("name") String name,
                          @Query("score") int score);
}
