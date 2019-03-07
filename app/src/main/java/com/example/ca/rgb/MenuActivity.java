package com.example.ca.rgb;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getName;
import static com.example.ca.rgb.StaticMethods.getSound;
import static com.example.ca.rgb.StaticMethods.getStars;
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

        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true);

        if (getMusic(getApplicationContext()) != 2) {
            mp.start();
            setMusic(getApplicationContext(), 1);
        }

        if (getSound(getApplicationContext()) != 2) {
            setSound(getApplicationContext(), 1);
        }

        profileButton = findViewById(R.id.profileBtn);
        Button playBtn = findViewById(R.id.playBtn);
        Button rankBtn = findViewById(R.id.rankBtn);
        Button exitBtn = findViewById(R.id.exitBtn);
        Button helpBtn = findViewById(R.id.helpBtn);
        Button settingsBtn = findViewById(R.id.settingsBtn);

        String myName = getName(getApplicationContext());
        profileButton.setText(myName);

        if (!(myName.length() > 0))
            startActivity(new Intent(MenuActivity.this, UserActivity.class));

        profileButton.setOnClickListener(menuActionListener);
        playBtn.setOnClickListener(menuActionListener);
        rankBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);
        helpBtn.setOnClickListener(menuActionListener);
        settingsBtn.setOnClickListener(menuActionListener);

        if(getStars(getApplicationContext()) == 0){
            rankBtn.setBackgroundResource(R.drawable.avatar6_big);
        }else{
            //rankBtn.setBackgroundResource(android.R.color.transparent);
        }

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
            switch (action) {
                case "profileBtn":
                    startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
                    break;
                case "playBtn":
                    startActivity(new Intent(MenuActivity.this, ButtonsActivity.class));
                    break;
                case "rankBtn":
                    if(getStars(getApplicationContext()) == 0){
                        showAlertDialogButtonClicked("You need 1 star to see this score.");
                    }else{
                        startActivity(new Intent(MenuActivity.this, TopTenScore.class));
                    }
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

    public void showAlertDialogButtonClicked(String s) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle("Info");
        builder.setMessage(s);

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialog.show();
        }
    }

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
}
