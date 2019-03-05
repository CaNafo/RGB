package com.example.ca.rgb.RetrofitPoziv;

import com.example.ca.rgb.DomainName;
import com.example.ca.rgb.Interfaces.APIservisi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 25.5.2018..
 */

public class RetrofitCall
{
    private static final String ROOT_URL = DomainName.getIstance();

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

    public static APIservisi getApi() {
        return getRetrofitInstance().create(APIservisi.class);
    }
}
