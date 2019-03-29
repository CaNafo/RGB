package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getProfileFirst;
import static com.example.ca.rgb.StaticMethods.getSound;
import static com.example.ca.rgb.StaticMethods.setMusic;
import static com.example.ca.rgb.StaticMethods.setProfileFirst;
import static com.example.ca.rgb.StaticMethods.setSound;

public class SettingsActivity extends AppCompatActivity {

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(getMusic(getApplicationContext()) == 1){
            mp.start();
            ((Button)findViewById(R.id.musicBtn)).setText("Music: ON");
        }else{
            ((Button)findViewById(R.id.musicBtn)).setText("Music: OFF");
        }

        if(getSound(getApplicationContext()) == 1){
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: ON");
        }else{
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: OFF");
        }

        Button musicBtn = findViewById(R.id.musicBtn);
        Button soundBtn = findViewById(R.id.soundBtn);
        Button profileSettingsBtn = findViewById(R.id.profileSettingsBtn);

        musicBtn.setOnClickListener(menuActionListener);
        soundBtn.setOnClickListener(menuActionListener);
        profileSettingsBtn.setOnClickListener(menuActionListener);

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
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            String s = "";
            SharedPreferences settings;
            SharedPreferences.Editor editor;
            switch (action){
                case "musicBtn":
                    s = String.valueOf(button.getText());
                    settings = getApplicationContext().getSharedPreferences("Music", 0);
                    editor = settings.edit();
                    if(s.equalsIgnoreCase("Music: ON")){
                        mp.pause();
                        setMusic(getApplicationContext(), 2);
                        button.setText("Music: OFF");
                    }else{
                        mp.start();
                        setMusic(getApplicationContext(), 1);
                        button.setText("Music: ON");
                    }
                    break;
                case "soundBtn":
                    s = String.valueOf(button.getText());
                    settings = getApplicationContext().getSharedPreferences("Sound", 0);
                    editor = settings.edit();
                    if(s.equalsIgnoreCase("Sound: ON")){
                        setSound(getApplicationContext(), 2);
                        button.setText("Sound: OFF");
                    }else{
                        setSound(getApplicationContext(), 1);
                        button.setText("Sound: ON");
                    }
                    break;
                case "profileSettingsBtn":
                    if(getProfileFirst(getApplicationContext()) == 1){
                        finish();
                        startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                    }else{
                        setProfileFirst(getApplicationContext(), 1);
                        showAlertDialogButtonClicked();
                    }
                    break;
            }
        }
    };

    public void showAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle("Info");
        builder.setMessage("To check your profile you can simply click on your avatar in Main Menu.");

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getMusic(getApplicationContext()) == 1){
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
    }
}
