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

import static com.example.ca.rgb.StaticMethods.getMusic;

public class ModeActivity extends AppCompatActivity {
    private AdView mAdView;
    private int mode = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        Bundle b = getIntent().getExtras();
        if (b != null)
            mode = b.getInt("mode");

        setTitle();

        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }

        Button timeAttackBtn = findViewById(R.id.timeAttackBtn);
        Button classicBtn = findViewById(R.id.classicBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);

        if(mode == 7){
            timeAttackBtn.setOnClickListener(null);
            timeAttackBtn.setVisibility(View.GONE);
            classicBtn.setOnClickListener(hardActionListener);
            scoreBtn.setOnClickListener(hardActionListener);
        }else{
            timeAttackBtn.setOnClickListener(modeActionListener);
            classicBtn.setOnClickListener(modeActionListener);
            scoreBtn.setOnClickListener(modeActionListener);
        }

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    private void setTitle(){
        String s = "3 Colors";
        ScoreActivity scoreActivity = new ScoreActivity();
        scoreActivity.Jarray = null;
        if(mode == 1 || mode == 2){
            mode = 1;
            scoreActivity.tryReadScore("timeAttack");
        }else if(mode == 3 || mode == 4){
            mode = 3;
            scoreActivity.tryReadScore("timeattackHard");
            s = "5 Colors";
        }else if(mode == 5 || mode == 6){
            mode = 5;
            scoreActivity.tryReadScore("timeAttack8");
            s = "8 Colors";
        }else if(mode == 7){
            mode = 7;
            scoreActivity.tryReadScore("8Hard");
            s = "8 Colors HARD";
        }

        ((TextView)findViewById(R.id.textView7)).setText(s);
    }

    View.OnClickListener modeActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            Intent intentTimeAttack = new Intent(ModeActivity.this, PlayActivityTimeAttack.class);
            Intent intentClassic = new Intent(ModeActivity.this, PlayActivityClassic.class);
            Bundle b;

            switch (action){
                case "timeAttack":
                    b = new Bundle();
                    b.putInt("mode", mode); //Your id
                    intentTimeAttack.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intentTimeAttack);
                    break;
                case "classic":
                    ++mode;
                    b = new Bundle();
                    b.putInt("mode", mode); //Your id
                    intentClassic.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intentClassic);
                    break;
                case "score":
                    Intent intentScore = new Intent(ModeActivity.this, ScoreActivity.class);
                    b = new Bundle();
                    b.putInt("mode", mode); //Your id
                    intentScore.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intentScore);
                    break;
            }
        }
    };

    View.OnClickListener hardActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            Intent intentClassic = new Intent(ModeActivity.this, PlayActivityEightHard.class);

            switch (action){
                case "classic":
                    finish();
                    startActivity(intentClassic);
                    break;
                case "score":
                    Intent intentScore = new Intent(ModeActivity.this, ScoreActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("mode", mode); //Your id
                    intentScore.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intentScore);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ModeActivity.this, ButtonsActivity.class));
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

        ScoreActivity scoreActivity = new ScoreActivity();
        scoreActivity.Jarray = null;
        if(mode == 1 || mode == 2){
            mode = 1;
            scoreActivity.tryReadScore("timeAttack");
        }else if(mode == 3 || mode == 4){
            mode = 3;
            scoreActivity.tryReadScore("timeattackHard");
        }else if(mode == 5 || mode == 6){
            mode = 5;
            scoreActivity.tryReadScore("timeAttack8");
        }else if(mode == 7){
            mode = 7;
            scoreActivity.tryReadScore("8Hard");
        }
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }
    }
}
