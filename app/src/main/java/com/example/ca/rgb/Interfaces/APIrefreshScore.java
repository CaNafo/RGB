package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIrefreshScore {
    @GET("/RGB/refresh_score.php")
    Call<String> setQuery(@Query("ID") int ID,
                          @Query("avatar") String avatar,
                          @Query("timeAttack") int timeAttack,
                          @Query("classic") int classic,
                          @Query("timeattackHard") int timeattackHard,
                          @Query("classicHard") int classicHard,
                          @Query("timeAttack8") int timeAttack8,
                          @Query("classic8") int classic8,
                          @Query("timeAttack8Hard") int timeAttack8Hard,
                          @Query("classic8Hard") int classic8hard);
}
