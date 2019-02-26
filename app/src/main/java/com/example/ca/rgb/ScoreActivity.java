package com.example.ca.rgb;

import com.example.ca.rgb.R;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIogovor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getRetrofitObject();// Get from the SharedPreferences

        TextView textView = findViewById(R.id.myScoreTxt);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("score", 0);
        int myScore = settings.getInt("score", 0);

        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");
        textView.setText(myName+":       "+myScore);

    }

    void getRetrofitObject() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://192.168.1.5/")
                .build();

        APIogovor scalarService = retrofit.create(APIogovor.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/score_read.php");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseString = response.body();
                    try {
                        JSONObject object = new JSONObject(responseString);
                        JSONArray Jarray  = object.getJSONArray("data");
                        String name = "";
                        String score = "";
                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            JSONObject Jasonobject = Jarray.getJSONObject(i);
                            name += Jasonobject.get("name") + "\n";
                            score += Jasonobject.get("score")+"\n";
                        }
                        TextView textView = findViewById(R.id.nameTxt);
                        textView.setText(name);
                        textView = findViewById(R.id.scoreTxt);
                        textView.setText(score);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                }else{
                    //System.out.println(response.body() + "ETOOOOO");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
