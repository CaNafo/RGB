package com.example.ca.rgb;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.R;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);
        Button editBtn = findViewById(R.id.editNameBtn);
        Button helpBtn = findViewById(R.id.helpBtn);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if(!(myName.length() > 0))
             startActivity(new Intent(MenuActivity.this, UserActivity.class));

        playBtn.setOnClickListener(menuActionListener);
        scoreBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);
        editBtn.setOnClickListener(editNameListener);
        helpBtn.setOnClickListener(menuActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-2037874631253623~2347238577");

    }


   View.OnClickListener menuActionListener = new View.OnClickListener() {

       @RequiresApi(api = Build.VERSION_CODES.M)
       @Override
       public void onClick(View v) {
           Button button = (Button)v;
           String action = String.valueOf(button.getTag());
           switch (action){
               case "playBtn":
                   startActivity(new Intent(MenuActivity.this, ModeActivity.class));
                   finish();
                   break;
               case "scoreBtn":
                   startActivity(new Intent(MenuActivity.this, ScoreActivity.class));
                    //addNewScore();
                   break;
               case "exitBtn":
                   finish();
                   break;
               case "helpBtn":
                   startActivity(new Intent(MenuActivity.this, HelpActivity.class));
                   finish();
                   break;
           }
       }
   };

    View.OnClickListener editNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MenuActivity.this, UserActivity.class));
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

}
