package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ModeActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        if(MenuActivity.music == 1){
            MenuActivity.mp.start();
        }

        Button mode1Btn = findViewById(R.id.mode1Btn);
        Button mode2Btn = findViewById(R.id.mode2Btn);
        Button mode3Btn = findViewById(R.id.mode3Btn);
        Button mode4Btn = findViewById(R.id.mode4Btn);

        mode1Btn.setOnClickListener(modeActionListener);
        mode2Btn.setOnClickListener(modeActionListener);
        mode3Btn.setOnClickListener(modeActionListener);
        mode4Btn.setOnClickListener(modeActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-2037874631253623~2347238577");
    }

    View.OnClickListener modeActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            Intent intent = new Intent(ModeActivity.this, PlayActivity.class);
            Bundle b;

            switch (action){
                case "mode1":
                    b = new Bundle();
                    b.putInt("mode", 1); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "mode2":
                    b = new Bundle();
                    b.putInt("mode", 2); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "mode3":
                    b = new Bundle();
                    b.putInt("mode", 3); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "mode4":
                    b = new Bundle();
                    b.putInt("mode", 4); //Your id
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
        //startActivity(new Intent(ModeActivity.this, MenuActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MenuActivity.music == 1){
            MenuActivity.mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MenuActivity.music == 1){
            MenuActivity.mp.start();
        }
    }
}
