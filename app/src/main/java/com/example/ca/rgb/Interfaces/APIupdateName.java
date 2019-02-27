package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIupdateName {
    @GET("/RGB/name_update.php")
    Call<String> setQuery(@Query("ID") int ID,
                          @Query("name") String name);
}
