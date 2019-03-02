package com.example.ca.rgb;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.R;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.getSimpleName();
    private AdView mAdView;
    public static int music;
    public static MediaPlayer mp;// = MediaPlayer.create(MenuActivity.this, R.raw.error);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mp = MediaPlayer.create(this, R.raw.music);
        mp.setLooping(true);

        SharedPreferences MusicSound = getApplicationContext().getSharedPreferences("Music", 0);
        SharedPreferences.Editor editorMS = MusicSound.edit();
        music = MusicSound.getInt("Music", 0);

        if(music == 2){
            ((Button)findViewById(R.id.musicBtn)).setText("Music: OFF");
        }else{
            ((Button)findViewById(R.id.musicBtn)).setText("Music: ON");
            mp.start();
            editorMS.putInt("Music", 1);
        }

        editorMS.apply();
        MusicSound = getApplicationContext().getSharedPreferences("Sound", 0);
        int sound = MusicSound.getInt("Sound", 0);

        if(sound == 2){
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: OFF");
        }else{
            ((Button)findViewById(R.id.soundBtn)).setText("Sound: ON");
            editorMS.putInt("Sound", 1);
            editorMS = MusicSound.edit();
        }
        editorMS.apply();

        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);
        Button editBtn = findViewById(R.id.editNameBtn);
        Button musicBtn = findViewById(R.id.musicBtn);
        Button soundBtn = findViewById(R.id.soundBtn);
        Button helpBtn = findViewById(R.id.helpBtn);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if(!(myName.length() > 0))
             startActivity(new Intent(MenuActivity.this, UserActivity.class));

        playBtn.setOnClickListener(menuActionListener);
        scoreBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);
        editBtn.setOnClickListener(editNameListener);
        musicBtn.setOnClickListener(menuActionListener);
        soundBtn.setOnClickListener(menuActionListener);
        helpBtn.setOnClickListener(menuActionListener);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-2037874631253623~2347238577");

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
               case "playBtn":
                   startActivity(new Intent(MenuActivity.this, ModeActivity.class));
                   //finish();
                   break;
               case "scoreBtn":
                   startActivity(new Intent(MenuActivity.this, ScoreActivity.class));
                    //addNewScore();
                   break;
               case "exitBtn":
                   finish();
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
               case "helpBtn":
                   startActivity(new Intent(MenuActivity.this, HelpActivity.class));
                   //finish();
                   break;
           }
       }
   };

    View.OnClickListener editNameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MenuActivity.this, UserActivity.class));
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
