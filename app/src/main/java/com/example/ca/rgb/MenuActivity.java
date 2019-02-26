package com.example.ca.rgb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.R;
import com.example.ca.rgb.RetrofitPoziv.RetrofitOdgovor;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);


        playBtn.setOnClickListener(menuActionListener);
        scoreBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);

    }


   View.OnClickListener menuActionListener = new View.OnClickListener() {

       @Override
       public void onClick(View v) {
           Button button = (Button)v;
           String action = String.valueOf(button.getTag());
           switch (action){
               case "playBtn":
                   startActivity(new Intent(MenuActivity.this, PlayActivity.class));
                   break;
               case "scoreBtn":
                    getRetrofitObject();
                   break;
               case "exitBtn":
                   finish();
                   break;
           }
       }
   };


    void getRetrofitObject() {
        APIogovor apiService =  RetrofitOdgovor.getClient().create(APIogovor.class);
        Call<RetrofitResponse> call = apiService.getDistrictDetails("","");
        call.enqueue(new Callback<RetrofitResponse>() {
            @Override
            public void onResponse(Call<RetrofitResponse> call, Response<RetrofitResponse> response) {
                ArrayList<HashMap<String, String>> tmpList = response.body().getDatat();
                Log.e(TAG+"tmpList---", tmpList.toString());

            }

            @Override
            public void onFailure(Call<RetrofitResponse> call, Throwable t) {
                Log.e(TAG+"--", t.toString());
            }

        });
    }


    private void addNewScore(){
        APIservisi api = null;
        Call<String> call;
        call = api.setQuery("Neko",15);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    System.out.println("dadsadasdas");
                }else{
                    System.out.println("NIJEdadsadasdas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }

}
