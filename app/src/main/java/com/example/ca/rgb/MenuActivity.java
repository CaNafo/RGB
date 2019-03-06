package com.example.ca.rgb;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getName;
import static com.example.ca.rgb.StaticMethods.getSound;
import static com.example.ca.rgb.StaticMethods.setMusic;
import static com.example.ca.rgb.StaticMethods.setSound;


public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();
    private AdView mAdView;
    public static MediaPlayer mp;
    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        profileButton = findViewById(R.id.profileBtn);
        profileButton.setOnClickListener(profileOnClickListener);


        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true);

        if (getMusic(getApplicationContext()) != 2) {
            mp.start();
            setMusic(getApplicationContext(), 1);
        }

        if (getSound(getApplicationContext()) != 2) {
            setSound(getApplicationContext(), 1);
        }

        Button playBtn = findViewById(R.id.playBtn);

        Button exitBtn = findViewById(R.id.exitBtn);
        Button helpBtn = findViewById(R.id.helpBtn);
        Button settingsBtn = findViewById(R.id.settingsBtn);

        String myName = getName(getApplicationContext());
        profileButton.setText(myName);

        if (!(myName.length() > 0))
            startActivity(new Intent(MenuActivity.this, UserActivity.class));

        playBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);
        helpBtn.setOnClickListener(menuActionListener);
        settingsBtn.setOnClickListener(menuActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);

    }


    View.OnClickListener menuActionListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String action = String.valueOf(button.getTag());
            String s = "";
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            switch (action) {
                case "playBtn":
                    startActivity(new Intent(MenuActivity.this, ButtonsActivity.class));
                    break;
                case "scoreBtn":
                    startActivity(new Intent(MenuActivity.this, ScoreActivity.class));
                    break;
                case "settingsBtn":
                    startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
                    break;
                case "exitBtn":
                    finish();
                    break;
                case "helpBtn":
                    startActivity(new Intent(MenuActivity.this, HelpActivity.class));
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        mp.stop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getMusic(getApplicationContext()) == 1) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAvatar();
        if (getMusic(getApplicationContext()) == 1) {
            mp.start();
        }
        String myName = getName(getApplicationContext());
        profileButton.setText(myName);
    }

    View.OnClickListener profileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
        }
    };

    private void setAvatar() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("avatar", 0);
        String avatar = settings.getString("avatar", "avatar1");

        switch (avatar){
            case "avatar1":
                profileButton.setBackgroundResource(R.drawable.avatar1_big);
                break;
            case "avatar2":
                profileButton.setBackgroundResource(R.drawable.avatar2_big);
                break;
            case "avatar3":
                profileButton.setBackgroundResource(R.drawable.avatar3_big);
                break;
            case "avatar4":
                profileButton.setBackgroundResource(R.drawable.avatar4_big);
                break;
            case "avatar5":
                profileButton.setBackgroundResource(R.drawable.avatar5_big);
                break;
            case "avatar6":
                profileButton.setBackgroundResource(R.drawable.avatar6_big);
                break;
            case "avatar7":
                profileButton.setBackgroundResource(R.drawable.avatar7_big);
                break;
            case "avatar8":
                profileButton.setBackgroundResource(R.drawable.avatar8_big);
                break;
            case "avatar9":
                profileButton.setBackgroundResource(R.drawable.avatar9_big);
                break;
            case "avatar10":
                profileButton.setBackgroundResource(R.drawable.avatar1a_big);
                break;
        }
    }
}
