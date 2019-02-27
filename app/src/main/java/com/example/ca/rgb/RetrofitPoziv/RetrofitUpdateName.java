package com.example.ca.rgb.RetrofitPoziv;

import com.example.ca.rgb.Interfaces.APIupdateName;
import com.example.ca.rgb.Interfaces.APIupdateServisi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpdateName {
    private static final String ROOT_URL = "http://rgb.dx.am/";

    static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build();

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static APIupdateName getApi() {
        return getRetrofitInstance().create(APIupdateName.class);
    }
}
