package com.example.ca.rgb;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);


        redBtn.setOnClickListener(playActionListener);
        greenBtn.setOnClickListener(playActionListener);
        blueBtn.setOnClickListener(playActionListener);
    }

    View.OnClickListener playActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            TextView textView = findViewById(R.id.textView);
            TextView textView2 = findViewById(R.id.textView2);

            String colorName = String.valueOf(textView.getText());
            String action = String.valueOf(button.getTag());

            if(colorName.equalsIgnoreCase(action)){
                ++score;
                textView2.setText(String.valueOf(score));
                changeText();
            }else{
                --score;
                textView2.setText(String.valueOf(score));
            }
        }
    };

    private void changeText(){
        int random = new Random().nextInt(50);
        int random2 = new Random().nextInt(50);

        random = random % 3;
        random2 = random2 % 3;

        TextView textView = findViewById(R.id.textView);

        switch(random){
            case 0:
                textView.setText("RED");
                break;
            case 1:
                textView.setText("GREEN");
                break;
            case 2:
                textView.setText("BLUE");
                break;
        }

        switch(random2){
            case 0:
                textView.setTextColor(Color.parseColor("Red"));
                break;
            case 1:
                textView.setTextColor(Color.parseColor("Green"));
                break;
            case 2:
                textView.setTextColor(Color.parseColor("Blue"));
                break;
        }
    }
}


