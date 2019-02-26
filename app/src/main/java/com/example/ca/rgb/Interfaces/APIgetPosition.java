package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIgetPosition {
    @GET()
    Call<String> getStringResponse(@Url String url,@Query("ID") int ID);
}
