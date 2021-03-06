package com.example.ca.rgb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getPoints;
import static com.example.ca.rgb.StaticMethods.getRanking;
import static com.example.ca.rgb.StaticMethods.getStars;
import static com.example.ca.rgb.StaticMethods.retPoints;

public class ProfileActivity extends AppCompatActivity {
    TextView textView;
    private AdView mAdView;
    ImageView avatarProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setAvatar();
        avatarProfile.setOnClickListener(editNameOnClickListener);
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }

        setStars();

        ((TextView)findViewById(R.id.pointsTxt)).setText(retPoints(getApplicationContext()));
        ((TextView)findViewById(R.id.rankTxt7)).setText(getRanking(getApplicationContext()));

        textView = findViewById(R.id.scoreTxtt);
        textView.setOnClickListener(editNameOnClickListener);

        Button editName = findViewById(R.id.editNameBtn);
        editName.setOnClickListener(editNameOnClickListener);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("name",0);
        String myName = settings.getString("name","");

        textView.setText(myName);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    private void setStars(){
        int temp = getStars(getApplicationContext());

        switch (temp){
            case 0:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.starholder);
                break;
            case 1:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.starholder);
                break;
            case 2:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.starholder);
                break;
            case 3:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.starholder);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.starholder);
                break;
            case 4:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.starholder);
                break;
            case 5:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.star);
                break;
            case 6:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.star);
                break;
            case 7:
                ((ImageView)findViewById(R.id.imgStar1)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar2)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar3)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar4)).setImageResource(R.drawable.star);
                ((ImageView)findViewById(R.id.imgStar5)).setImageResource(R.drawable.star);
                break;
        }
    }

    private void setAvatar() {
        avatarProfile = findViewById(R.id.avatarProfile);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("avatar", 0);
        String avatar = settings.getString("avatar", "avatar1");

        switch (avatar){
            case "avatar1":
                avatarProfile.setImageResource(R.drawable.avatar1_big);
                break;
            case "avatar2":
                avatarProfile.setImageResource(R.drawable.avatar2_big);
                break;
            case "avatar3":
                avatarProfile.setImageResource(R.drawable.avatar3_big);
                break;
            case "avatar4":
                avatarProfile.setImageResource(R.drawable.avatar4_big);
                break;
            case "avatar5":
                avatarProfile.setImageResource(R.drawable.avatar5_big);
                break;
            case "avatar6":
                avatarProfile.setImageResource(R.drawable.avatar6_big);
                break;
            case "avatar7":
                avatarProfile.setImageResource(R.drawable.avatar7_big);
                break;
            case "avatar8":
                avatarProfile.setImageResource(R.drawable.avatar8_big);
                break;
            case "avatar9":
                avatarProfile.setImageResource(R.drawable.avatar9_big);
                break;
            case "avatar10":
                avatarProfile.setImageResource(R.drawable.avatar1a_big);
                break;
            case "avatar11":
                avatarProfile.setImageResource(R.drawable.avatar1b_big);
                break;
            case "avatar12":
                avatarProfile.setImageResource(R.drawable.avatar1c_big);
                break;
            case "avatar13":
                avatarProfile.setImageResource(R.drawable.avatar1d_big);
                break;
            case "avatar14":
                avatarProfile.setImageResource(R.drawable.avatar1e_big);
                break;
            case "avatar15":
                avatarProfile.setImageResource(R.drawable.avatar1f_big);
                break;
            case "avatar16":
                avatarProfile.setImageResource(R.drawable.avatar1g_big);
                break;
            case "avatar17":
                avatarProfile.setImageResource(R.drawable.avatar1h_big);
                break;
            case "avatar18":
                avatarProfile.setImageResource(R.drawable.avatar1i_big);
                break;
            case "avatar19":
                avatarProfile.setImageResource(R.drawable.avatar1j_big);
                break;
            case "avatar20":
                avatarProfile.setImageResource(R.drawable.avatar1k_big);
                break;
            case "avatar21":
                avatarProfile.setImageResource(R.drawable.avatar1l_big);
                break;
            case "avatar22":
                avatarProfile.setImageResource(R.drawable.avatar1m_big);
                break;
            case "avatar23":
                avatarProfile.setImageResource(R.drawable.avatar1n_big);
                break;
            case "avatar24":
                avatarProfile.setImageResource(R.drawable.avatar1o_big);
                break;
            case "avatar25":
                avatarProfile.setImageResource(R.drawable.avatar1p_big);
                break;
        }
    }

    View.OnClickListener editNameOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProfileActivity.this, User2Activity.class));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        avatarProfile = findViewById(R.id.avatarProfile);
        avatarProfile.setImageDrawable(null);
        setAvatar();
        TextView textView = findViewById(R.id.scoreTxtt);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("name",0);
        String myName = settings.getString("name","");
        textView.setText(myName);
        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getMusic(getApplicationContext()) == 1){
            mp.pause();
        }
    }
}
