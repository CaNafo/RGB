package com.example.ca.rgb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.MenuActivity.music;

public class ProfileActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = findViewById(R.id.nameTxt);
        textView.setOnClickListener(editNameOnClickListener);

        Button editName = findViewById(R.id.editNameBtn);
        editName.setOnClickListener(editNameOnClickListener);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("name",0);
        String myName = settings.getString("name","");

        textView.setText(myName);
    }

    View.OnClickListener editNameOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ProfileActivity.this, UserActivity.class));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = findViewById(R.id.nameTxt);
        SharedPreferences settings = getApplicationContext().getSharedPreferences("name",0);
        String myName = settings.getString("name","");
        textView.setText(myName);
        if(music == 1){
            mp.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MenuActivity.music == 1){
            MenuActivity.mp.pause();
        }
    }
}
