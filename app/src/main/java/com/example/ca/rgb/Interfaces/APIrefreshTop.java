package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIrefreshTop {
    @GET("/RGB/update_top_rank.php")
    Call<String> setQuery(@Query("ID") int ID,
                          @Query("points") int points);
}
