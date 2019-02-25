package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);


        playBtn.setOnClickListener(menuActionListener);
        scoreBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);

    }


   View.OnClickListener menuActionListener = new View.OnClickListener() {

       @Override
       public void onClick(View v) {
           Button button = (Button)v;
           String action = String.valueOf(button.getTag());
           switch (action){
               case "playBtn":
                   startActivity(new Intent(MenuActivity.this, PlayActivity.class));
                   break;
               case "scoreBtn":
                   button.setText("radi2");
                   break;
               case "exitBtn":
                   finish();
                   break;
           }
       }
   };
}
