package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        //startActivity(new Intent(HelpActivity.this, MenuActivity.class));
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
