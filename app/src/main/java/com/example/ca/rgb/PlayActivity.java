package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayActivity extends AppCompatActivity {
    private int score = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private int mode = -1;
    private int speed = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle b = getIntent().getExtras();
        if(b != null)
            mode = b.getInt("mode");

        final TextView textView4 = findViewById(R.id.textView4);

        countDownTimer2 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                fadeOutAnimation(textView4, 750);
                fadeInAnimation(textView4, 750);
                if(l / 1000 != 0){
                    textView4.setText(String.valueOf(l / 1000));
                }else{
                    textView4.setText("START!");
                }
            }

            @Override
            public void onFinish() {
                textView4.setText("");
                startGame();
            }
        }.start();
    }

    private void startGame(){
        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);

        redBtn.setOnClickListener(playActionListener);
        greenBtn.setOnClickListener(playActionListener);
        blueBtn.setOnClickListener(playActionListener);

        final TextView textView3 = findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);

        changeText();
        textView2.setText(String.valueOf(score));
        textView3.setText("10");
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l2) {
                textView3.setText(String.valueOf(l2 / 1000));
            }

            @Override
            public void onFinish() {
                // Get from the SharedPreferences
                SharedPreferences settings = getApplicationContext().getSharedPreferences("score", 0);
                int myScore = settings.getInt("score", 0);

                settings = getApplicationContext().getSharedPreferences("name", 0);
                String myName = settings.getString("name", "");

                if(myScore<score) {
                    addNewScore(myName,score);
                    settings = getApplicationContext().getSharedPreferences("score", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("score", score);
                    // Apply the edits!
                    editor.apply();
                }
                showAlertDialogButtonClicked("Time's up");
            }
        }.start();
    }

    private void restartGame(){
        score = 0;
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);

        textView.setText("");
        textView2.setText("");
        textView3.setText("");

        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);

        redBtn.setOnClickListener(null);
        greenBtn.setOnClickListener(null);
        blueBtn.setOnClickListener(null);

        countDownTimer2.start();
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
                countDownTimer.cancel();
                // Get from the SharedPreferences
                SharedPreferences settings = getApplicationContext().getSharedPreferences("score", 0);
                int myScore = settings.getInt("score", 0);

                settings = getApplicationContext().getSharedPreferences("name", 0);
                String myName = settings.getString("name", "");

                if(myScore<score) {
                    addNewScore(myName,myScore);
                    settings = getApplicationContext().getSharedPreferences("score", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("score", score);
                    // Apply the edits!
                    editor.apply();
                }
                showAlertDialogButtonClicked("GAME OVER");
            }
        }
    };

    private void changeText(){
        int random = new Random().nextInt(50);
        int random2 = new Random().nextInt(50);

        random = random % 3;
        random2 = random2 % 3;

        TextView textView = findViewById(R.id.textView);

        fadeOutAnimation(textView, 1500);
        fadeInAnimation(textView, 1500);

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

    public void showAlertDialogButtonClicked(String s) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s);
        builder.setMessage("You scored " + score + ".");

        // add a button
        builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
            }
        });
        builder.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(PlayActivity.this, MenuActivity.class));
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(PlayActivity.this, ModeActivity.class));
    }

    public static void fadeInAnimation(final View view, long animationDuration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeIn);
    }

    public static void fadeOutAnimation(final View view, long animationDuration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(animationDuration);
        fadeOut.setDuration(animationDuration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeOut);
    }

    private void addNewScore(String name, int score){
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(name,score);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    System.out.println("dadsadasdas");
                }else{
                    System.out.println("NIJEdadsadasdas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }
}

