package com.example.ca.rgb.Interfaces;

import com.example.ca.rgb.RetrofitPoziv.RetrofitOdgovor;
import com.example.ca.rgb.RetrofitResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIogovor {
    @FormUrlEncoded
    @POST("/RGB/score_read.php")
    Call<RetrofitResponse> getDistrictDetails(@Field("target") String target, @Field("action") String action);

}