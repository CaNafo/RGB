package com.example.ca.rgb.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIodgovorTopRank {
    @GET()
    Call<String> getStringResponse(@Url String url);
}
