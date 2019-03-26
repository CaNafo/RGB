package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.ca.rgb.StaticMethods.getAvatar;
import static com.example.ca.rgb.StaticMethods.getRanking;
import static com.example.ca.rgb.StaticMethods.setAvatar;
import static com.example.ca.rgb.StaticMethods.setPoints;

public class AvatarActivity extends AppCompatActivity {
    private AdView mAdView;
    String tempAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        
        resetAvatars();

        tempAvatar = getAvatar(getApplicationContext());
        setChecked(tempAvatar);

        Button okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(onClickListener);

        setListeners();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    private void setListeners(){
        findViewById(R.id.avatar1).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar2).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar3).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar4).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar5).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar6).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar7).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar8).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar9).setOnClickListener(imageOnClickListener);
        findViewById(R.id.avatar10).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar11).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar12).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar13).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar14).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar15).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar16).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar17).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar18).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar19).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar20).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar21).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar22).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar23).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar24).setOnClickListener(imageOnClickListener2);
        findViewById(R.id.avatar25).setOnClickListener(imageOnClickListener2);

        String temp = getRanking(getApplicationContext());

        switch (temp){
            case "Talented":
                findViewById(R.id.avatar11).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar12).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar13).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar14).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar15).setOnClickListener(imageOnClickListener);
                break;
            case "Professional":
                findViewById(R.id.avatar11).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar12).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar13).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar14).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar15).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar16).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar17).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar18).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar19).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar20).setOnClickListener(imageOnClickListener);
                break;
            case "Insane":
                findViewById(R.id.avatar11).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar12).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar13).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar14).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar15).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar16).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar17).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar18).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar19).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar20).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar21).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar22).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar23).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar24).setOnClickListener(imageOnClickListener);
                findViewById(R.id.avatar25).setOnClickListener(imageOnClickListener);
                break;
        }
    }

    private void setChecked(String a){
        int c = Character.getNumericValue(a.charAt(a.length() - 1));

        switch (a){
            case "avatar1":
                ((ImageView)findViewById(R.id.avatar1)).setImageResource(R.drawable.avatar1_small_checked);
                break;
            case "avatar2":
                ((ImageView)findViewById(R.id.avatar2)).setImageResource(R.drawable.avatar2_small_checked);
                break;
            case "avatar3":
                ((ImageView)findViewById(R.id.avatar3)).setImageResource(R.drawable.avatar3_small_checked);
                break;
            case "avatar4":
                ((ImageView)findViewById(R.id.avatar4)).setImageResource(R.drawable.avatar4_small_checked);
                break;
            case "avatar5":
                ((ImageView)findViewById(R.id.avatar5)).setImageResource(R.drawable.avatar5_small_checked);
                break;
            case "avatar6":
                ((ImageView)findViewById(R.id.avatar6)).setImageResource(R.drawable.avatar6_small_checked);
                break;
            case "avatar7":
                ((ImageView)findViewById(R.id.avatar7)).setImageResource(R.drawable.avatar7_small_checked);
                break;
            case "avatar8":
                ((ImageView)findViewById(R.id.avatar8)).setImageResource(R.drawable.avatar8_small_checked);
                break;
            case "avatar9":
                ((ImageView)findViewById(R.id.avatar9)).setImageResource(R.drawable.avatar9_small_checked);
                break;
            case "avatar10":
                ((ImageView)findViewById(R.id.avatar10)).setImageResource(R.drawable.avatar1a_small_checked);
                break;
            case "avatar11":
                ((ImageView)findViewById(R.id.avatar11)).setImageResource(R.drawable.avatar1b_small_checked);
                break;
            case "avatar12":
                ((ImageView)findViewById(R.id.avatar12)).setImageResource(R.drawable.avatar1c_small_checked);
                break;
            case "avatar13":
                ((ImageView)findViewById(R.id.avatar13)).setImageResource(R.drawable.avatar1d_small_checked);
                break;
            case "avatar14":
                ((ImageView)findViewById(R.id.avatar14)).setImageResource(R.drawable.avatar1e_small_checked);
                break;
            case "avatar15":
                ((ImageView)findViewById(R.id.avatar15)).setImageResource(R.drawable.avatar1f_small_checked);
                break;
            case "avatar16":
                ((ImageView)findViewById(R.id.avatar16)).setImageResource(R.drawable.avatar1g_small_checked);
                break;
            case "avatar17":
                ((ImageView)findViewById(R.id.avatar17)).setImageResource(R.drawable.avatar1h_small_checked);
                break;
            case "avatar18":
                ((ImageView)findViewById(R.id.avatar18)).setImageResource(R.drawable.avatar1i_small_checked);
                break;
            case "avatar19":
                ((ImageView)findViewById(R.id.avatar19)).setImageResource(R.drawable.avatar1j_small_checked);
                break;
            case "avatar20":
                ((ImageView)findViewById(R.id.avatar20)).setImageResource(R.drawable.avatar1k_small_checked);
                break;
            case "avatar21":
                ((ImageView)findViewById(R.id.avatar21)).setImageResource(R.drawable.avatar1l_small_checked);
                break;
            case "avatar22":
                ((ImageView)findViewById(R.id.avatar22)).setImageResource(R.drawable.avatar1m_small_checked);
                break;
            case "avatar23":
                ((ImageView)findViewById(R.id.avatar23)).setImageResource(R.drawable.avatar1n_small_checked);
                break;
            case "avatar24":
                ((ImageView)findViewById(R.id.avatar24)).setImageResource(R.drawable.avatar1o_small_checked);
                break;
            case "avatar25":
                ((ImageView)findViewById(R.id.avatar25)).setImageResource(R.drawable.avatar1p_small_checked);
                break;
        }
    }

    private void resetAvatars(){
        ((ImageView)findViewById(R.id.avatar1)).setImageResource(R.drawable.avatar1_small);
        ((ImageView)findViewById(R.id.avatar2)).setImageResource(R.drawable.avatar2_small);
        ((ImageView)findViewById(R.id.avatar3)).setImageResource(R.drawable.avatar3_small);
        ((ImageView)findViewById(R.id.avatar4)).setImageResource(R.drawable.avatar4_small);
        ((ImageView)findViewById(R.id.avatar5)).setImageResource(R.drawable.avatar5_small);
        ((ImageView)findViewById(R.id.avatar6)).setImageResource(R.drawable.avatar6_small);
        ((ImageView)findViewById(R.id.avatar7)).setImageResource(R.drawable.avatar7_small);
        ((ImageView)findViewById(R.id.avatar8)).setImageResource(R.drawable.avatar8_small);
        ((ImageView)findViewById(R.id.avatar9)).setImageResource(R.drawable.avatar9_small);
        ((ImageView)findViewById(R.id.avatar10)).setImageResource(R.drawable.avatar1a_small);

        switch (getRanking(getApplicationContext())){
            case "Talented":
                ((ImageView)findViewById(R.id.avatar11)).setImageResource(R.drawable.avatar1b_small);
                ((ImageView)findViewById(R.id.avatar12)).setImageResource(R.drawable.avatar1c_small);
                ((ImageView)findViewById(R.id.avatar13)).setImageResource(R.drawable.avatar1d_small);
                ((ImageView)findViewById(R.id.avatar14)).setImageResource(R.drawable.avatar1e_small);
                ((ImageView)findViewById(R.id.avatar15)).setImageResource(R.drawable.avatar1f_small);
                break;
            case "Professional":
                ((ImageView)findViewById(R.id.avatar11)).setImageResource(R.drawable.avatar1b_small);
                ((ImageView)findViewById(R.id.avatar12)).setImageResource(R.drawable.avatar1c_small);
                ((ImageView)findViewById(R.id.avatar13)).setImageResource(R.drawable.avatar1d_small);
                ((ImageView)findViewById(R.id.avatar14)).setImageResource(R.drawable.avatar1e_small);
                ((ImageView)findViewById(R.id.avatar15)).setImageResource(R.drawable.avatar1f_small);
                ((ImageView)findViewById(R.id.avatar16)).setImageResource(R.drawable.avatar1g_small);
                ((ImageView)findViewById(R.id.avatar17)).setImageResource(R.drawable.avatar1h_small);
                ((ImageView)findViewById(R.id.avatar18)).setImageResource(R.drawable.avatar1i_small);
                ((ImageView)findViewById(R.id.avatar19)).setImageResource(R.drawable.avatar1j_small);
                ((ImageView)findViewById(R.id.avatar20)).setImageResource(R.drawable.avatar1k_small);
                break;
            case "Insane":
                ((ImageView)findViewById(R.id.avatar11)).setImageResource(R.drawable.avatar1b_small);
                ((ImageView)findViewById(R.id.avatar12)).setImageResource(R.drawable.avatar1c_small);
                ((ImageView)findViewById(R.id.avatar13)).setImageResource(R.drawable.avatar1d_small);
                ((ImageView)findViewById(R.id.avatar14)).setImageResource(R.drawable.avatar1e_small);
                ((ImageView)findViewById(R.id.avatar15)).setImageResource(R.drawable.avatar1f_small);
                ((ImageView)findViewById(R.id.avatar16)).setImageResource(R.drawable.avatar1g_small);
                ((ImageView)findViewById(R.id.avatar17)).setImageResource(R.drawable.avatar1h_small);
                ((ImageView)findViewById(R.id.avatar18)).setImageResource(R.drawable.avatar1i_small);
                ((ImageView)findViewById(R.id.avatar19)).setImageResource(R.drawable.avatar1j_small);
                ((ImageView)findViewById(R.id.avatar20)).setImageResource(R.drawable.avatar1k_small);
                ((ImageView)findViewById(R.id.avatar21)).setImageResource(R.drawable.avatar1l_small);
                ((ImageView)findViewById(R.id.avatar22)).setImageResource(R.drawable.avatar1m_small);
                ((ImageView)findViewById(R.id.avatar23)).setImageResource(R.drawable.avatar1n_small);
                ((ImageView)findViewById(R.id.avatar24)).setImageResource(R.drawable.avatar1o_small);
                ((ImageView)findViewById(R.id.avatar25)).setImageResource(R.drawable.avatar1p_small);
                break;
        }
    }

    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView imageView = (ImageView)v;

            switch (imageView.getTag().toString()){
                case "avatar1":
                    tempAvatar = "avatar1";
                    break;
                case "avatar2":
                    tempAvatar = "avatar2";
                    break;
                case "avatar3":
                    tempAvatar = "avatar3";
                    break;
                case "avatar4":
                    tempAvatar = "avatar4";
                    break;
                case "avatar5":
                    tempAvatar = "avatar5";
                    break;
                case "avatar6":
                    tempAvatar = "avatar6";
                    break;
                case "avatar7":
                    tempAvatar = "avatar7";
                    break;
                case "avatar8":
                    tempAvatar = "avatar8";
                    break;
                case "avatar9":
                    tempAvatar = "avatar9";
                    break;
                case "avatar10":
                    tempAvatar = "avatar10";
                    break;
                case "avatar11":
                    tempAvatar = "avatar11";
                    break;
                case "avatar12":
                    tempAvatar = "avatar12";
                    break;
                case "avatar13":
                    tempAvatar = "avatar13";
                    break;
                case "avatar14":
                    tempAvatar = "avatar14";
                    break;
                case "avatar15":
                    tempAvatar = "avatar15";
                    break;
                case "avatar16":
                    tempAvatar = "avatar16";
                    break;
                case "avatar17":
                    tempAvatar = "avatar17";
                    break;
                case "avatar18":
                    tempAvatar = "avatar18";
                    break;
                case "avatar19":
                    tempAvatar = "avatar19";
                    break;
                case "avatar20":
                    tempAvatar = "avatar20";
                    break;
                case "avatar21":
                    tempAvatar = "avatar21";
                    break;
                case "avatar22":
                    tempAvatar = "avatar22";
                    break;
                case "avatar23":
                    tempAvatar = "avatar23";
                    break;
                case "avatar24":
                    tempAvatar = "avatar24";
                    break;
                case "avatar25":
                    tempAvatar = "avatar25";
                    break;
            }
            resetAvatars();
            setChecked(tempAvatar);
        }
    };

    View.OnClickListener imageOnClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView imageView = (ImageView)v;

            switch (imageView.getTag().toString()){
                case "avatar11":
                    showAlertDialogButtonClicked("You have to be Talented to unlock this avatar.");
                    break;
                case "avatar12":
                    showAlertDialogButtonClicked("You have to be Talented to unlock this avatar.");
                    break;
                case "avatar13":
                    showAlertDialogButtonClicked("You have to be Talented to unlock this avatar.");
                    break;
                case "avatar14":
                    showAlertDialogButtonClicked("You have to be Talented to unlock this avatar.");
                    break;
                case "avatar15":
                    showAlertDialogButtonClicked("You have to be Talented to unlock this avatar.");
                    break;
                case "avatar16":
                    showAlertDialogButtonClicked("You have to be Professional to unlock this avatar.");
                    break;
                case "avatar17":
                    showAlertDialogButtonClicked("You have to be Professional to unlock this avatar.");
                    break;
                case "avatar18":
                    showAlertDialogButtonClicked("You have to be Professional to unlock this avatar.");
                    break;
                case "avatar19":
                    showAlertDialogButtonClicked("You have to be Professional to unlock this avatar.");
                    break;
                case "avatar20":
                    showAlertDialogButtonClicked("You have to be Professional to unlock this avatar.");
                    break;
                case "avatar21":
                    showAlertDialogButtonClicked("You have to be Insane to unlock this avatar.");
                    break;
                case "avatar22":
                    showAlertDialogButtonClicked("You have to be Insane to unlock this avatar.");
                    break;
                case "avatar23":
                    showAlertDialogButtonClicked("You have to be Insane to unlock this avatar.");
                    break;
                case "avatar24":
                    showAlertDialogButtonClicked("You have to be Insane to unlock this avatar.");
                    break;
                case "avatar25":
                    showAlertDialogButtonClicked("You have to be Insane to unlock this avatar.");
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
                startActivity(new Intent(AvatarActivity.this, ProfileActivity.class));
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setAvatar(getApplicationContext(), tempAvatar);
            finish();
            startActivity(new Intent(AvatarActivity.this, User2Activity.class));
        }
    };
}
