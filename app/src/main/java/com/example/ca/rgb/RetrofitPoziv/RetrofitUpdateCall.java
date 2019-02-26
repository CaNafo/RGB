package com.example.ca.rgb.RetrofitPoziv;

import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.Interfaces.APIupdateServisi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUpdateCall {

    private static final String ROOT_URL = "http://192.168.1.5/";

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

    public static APIupdateServisi getApi() {
        return getRetrofitInstance().create(APIupdateServisi.class);
    }
}
