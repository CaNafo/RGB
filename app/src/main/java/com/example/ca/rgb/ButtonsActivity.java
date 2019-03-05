package com.example.ca.rgb;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getPoints;

public class ButtonsActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }

        setButtons();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    private void setButtons(){
        /*((Button)findViewById(R.id.threeButtons)).setOnClickListener(modeActionListener);

        if(getPoints(getApplicationContext()) > 150){
            ((Button)findViewById(R.id.fiveButtons)).setOnClickListener(modeActionListener);
        }else{
            ((Button)findViewById(R.id.fiveButtons)).setBackgroundColor(Color.parseColor("gray"));
            ((Button)findViewById(R.id.fiveButtons)).setOnClickListener(null);
        }

        if(getPoints(getApplicationContext()) > 500){
            ((Button)findViewById(R.id.eightButtons)).setOnClickListener(modeActionListener);
        }else{
            ((Button)findViewById(R.id.eightButtons)).setBackgroundColor(Color.parseColor("gray"));
            ((Button)findViewById(R.id.eightButtons)).setOnClickListener(null);
        }

        if(getPoints(getApplicationContext()) > 1000){
            ((Button)findViewById(R.id.tenButtons)).setOnClickListener(modeActionListener);
        }else{
            ((Button)findViewById(R.id.tenButtons)).setBackgroundColor(Color.parseColor("gray"));
            ((Button)findViewById(R.id.tenButtons)).setOnClickListener(null);
        }*/

        ((Button)findViewById(R.id.threeButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.fiveButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.eightButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.tenButtons)).setOnClickListener(null);
    }

    View.OnClickListener modeActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            Intent intent = new Intent(ButtonsActivity.this, ModeActivity.class);
            Bundle b;

            switch (action){
                case "3btns":
                    b = new Bundle();
                    b.putInt("mode", 1); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "5btns":
                    b = new Bundle();
                    b.putInt("mode", 3); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "8btns":
                    b = new Bundle();
                    b.putInt("mode", 5); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "10btns":
                    b = new Bundle();
                    b.putInt("mode", 7); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }
    }
}
