package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private  static MenuActivity application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        application = this;
        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);


        MenuActionListeners menuActionListeners = new MenuActionListeners();
        playBtn.setOnClickListener(menuActionListeners);
        scoreBtn.setOnClickListener(menuActionListeners);
        exitBtn.setOnClickListener(exitListener);

    }


   View.OnClickListener exitListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           //finish();
           startActivity(new Intent(MenuActivity.this, PlayActivity.class));
       }
   };
}
