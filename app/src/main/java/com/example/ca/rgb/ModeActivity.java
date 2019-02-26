package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        Button mode1Btn = findViewById(R.id.mode1Btn);
        Button mode2Btn = findViewById(R.id.mode2Btn);


        mode1Btn.setOnClickListener(modeActionListener);
        mode2Btn.setOnClickListener(modeActionListener);
    }

    View.OnClickListener modeActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button)v;
            String action = String.valueOf(button.getTag());
            Intent intent = new Intent(ModeActivity.this, PlayActivity.class);
            Bundle b;

            switch (action){
                case "mode1":
                    b = new Bundle();
                    b.putInt("mode", 1); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "mode2":
                    b = new Bundle();
                    b.putInt("mode", 2); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
                case "mode3":
                    b = new Bundle();
                    b.putInt("mode", 3); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ModeActivity.this, MenuActivity.class));
    }
}