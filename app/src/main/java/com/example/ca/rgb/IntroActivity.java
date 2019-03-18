package com.example.ca.rgb;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getStars;

public class IntroActivity extends AppCompatActivity {
    VideoView view;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        (findViewById(R.id.closeBtn)).setOnClickListener(menuActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);

        view = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.testclip;
        view.setVideoURI(Uri.parse(path));
        view.setOnClickListener(replayActionListener);
        view.start();
    }

    View.OnClickListener replayActionListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            if(view.isPlaying()){
                view.pause();
            }else{
                view.start();
            }
        }
    };

    View.OnClickListener menuActionListener = new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            finish();
            startActivity(new Intent(IntroActivity.this, ButtonsActivity.class));
        }
    };

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(IntroActivity.this, ButtonsActivity.class));
    }

    @Override
    protected void onPause(){
        super.onPause();
        view.pause();
        if(getMusic(getApplicationContext()) == 1){
            mp.pause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        view.start();
        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
    }
}
