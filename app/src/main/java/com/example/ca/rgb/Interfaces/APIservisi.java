package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by user on 25.5.2018..
 */

public interface APIservisi
{
    @GET("/RGB/score_add.php")
    Call<String> setQuery(@Query("ID") int ID,@Query("name") String name,@Query("avatar") String avatar);


}
