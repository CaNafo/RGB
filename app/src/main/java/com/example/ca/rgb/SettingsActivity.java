package com.example.ca.rgb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.MenuActivity.music;
import static com.example.ca.rgb.MenuActivity.sound;

public class SettingsActivity extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(music == 1){
            mp.start();
            ((Button)findViewById(R.id.musicBtn)).setText("Music: ON");
        }else{
            ((Button)findViewById(R.id.musicBtn)).setText("Music: OFF");
        }

        if(sound == 1){
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: ON");
        }else{
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: OFF");
        }

        Button editBtn = findViewById(R.id.editNameBtn);
        Button musicBtn = findViewById(R.id.musicBtn);
        Button soundBtn = findViewById(R.id.soundBtn);

        editBtn.setOnClickListener(menuActionListener);
        musicBtn.setOnClickListener(menuActionListener);
        soundBtn.setOnClickListener(menuActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-2037874631253623/3277176861");
    }

    View.OnClickListener menuActionListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            String s = "";
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            switch (action){
                case "editBtn":
                    startActivity(new Intent(SettingsActivity.this, UserActivity.class));
                    break;
                case "musicBtn":
                    s = String.valueOf(button.getText());
                    settings = getApplicationContext().getSharedPreferences("Music", 0);
                    editor = settings.edit();
                    if(s.equalsIgnoreCase("Music: ON")){
                        music = 2;
                        mp.pause();
                        editor.putInt("Music", 2);
                        button.setText("Music: OFF");
                    }else{
                        music = 1;
                        mp.start();
                        editor.putInt("Music", 1);
                        button.setText("Music: ON");
                    }
                    editor.apply();
                    break;
                case "soundBtn":
                    s = String.valueOf(button.getText());
                    settings = getApplicationContext().getSharedPreferences("Sound", 0);
                    editor = settings.edit();
                    if(s.equalsIgnoreCase("Sound: ON")){
                        editor.putInt("Sound", 2);
                        button.setText("Sound: OFF");
                    }else{
                        editor.putInt("Sound", 1);
                        button.setText("Sound: ON");
                    }
                    editor.apply();
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
        if(music == 1){
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(music == 1){
            mp.start();
        }
    }
}
