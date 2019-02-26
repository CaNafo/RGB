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

            switch (action){
                case "mode1":
                    startActivity(new Intent(ModeActivity.this, PlayActivity.class));
                    break;
                case "mode2":

                    break;
            }
        }
    };
}
