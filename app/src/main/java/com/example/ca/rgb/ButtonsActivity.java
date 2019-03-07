package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getPoints;
import static com.example.ca.rgb.StaticMethods.getStars;

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
        int stars = getStars(getApplicationContext());

        ((Button)findViewById(R.id.threeButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.fiveButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.eightButtons)).setOnClickListener(modeActionListener);
        ((Button)findViewById(R.id.eightHardButtons)).setOnClickListener(modeActionListener);

        if(stars < 5){
            ((Button)findViewById(R.id.eightHardButtons)).setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.gray));
            ((Button)findViewById(R.id.eightHardButtons)).setOnClickListener(infoActionListener);
        }

        if(stars < 3){
            ((Button)findViewById(R.id.eightButtons)).setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.gray));
            ((Button)findViewById(R.id.eightButtons)).setOnClickListener(infoActionListener);
        }

        if(stars < 2){
            ((Button)findViewById(R.id.fiveButtons)).setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.gray));
            ((Button)findViewById(R.id.fiveButtons)).setOnClickListener(infoActionListener);
        }
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
                    finish();
                    startActivity(intent);
                    break;
                case "5btns":
                    b = new Bundle();
                    b.putInt("mode", 3); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intent);
                    break;
                case "8btns":
                    b = new Bundle();
                    b.putInt("mode", 5); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intent);
                    break;
                case "8btnHard":
                    b = new Bundle();
                    b.putInt("mode", 7); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    finish();
                    startActivity(intent);
                    break;
            }
        }
    };

    View.OnClickListener infoActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            String s;

            switch (action){
                case "5btns":
                    s = "You need 2 stars to play this mode.";
                    showAlertDialogButtonClicked(s);
                    break;
                case "8btns":
                    s = "You need 3 stars to play this mode.";
                    showAlertDialogButtonClicked(s);
                    break;
                case "8btnHard":
                    s = "You need 5 stars to play this mode.";
                    showAlertDialogButtonClicked(s);
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
        builder.setPositiveButton("Stats", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(ButtonsActivity.this, ProfileActivity.class));
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
