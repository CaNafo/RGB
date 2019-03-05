package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIrefreshScore {
    @GET("/RGB/refresh_score.php")
    Call<String> setQuery(@Query("ID") int ID,
                          @Query("timeAttack") int timeAttack,
                          @Query("classic") int classic,
                          @Query("timeattackHard") int timeattackHard,
                          @Query("classicHard") int classicHard);
}
