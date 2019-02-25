package com.example.ca.rgb;

import android.app.Application;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import static android.support.v4.content.ContextCompat.startActivity;

public class MenuActionListeners implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        String action = String.valueOf(button.getTag());

        switch (action){
            case "playBtn":
                button.setText("radi1");
                //((MenuActivity)v.getParent()).setContentView(R.layout.activity_play);
                break;
            case "scoreBtn":
                button.setText("radi2");
                break;
        }
    }
}
