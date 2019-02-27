package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.Interfaces.APIupdateServisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitUpdateCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PlayActivity extends AppCompatActivity {
    private int score = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private CountDownTimer countDownTimer3;
    private boolean started = false;
    private int mode = -1;
    private int time = 0;
    private int speed = 7;
    private int tempSpeed = 7;
    private int lives = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle b = getIntent().getExtras();
        if(b != null)
            mode = b.getInt("mode");

        final TextView textView4 = findViewById(R.id.textView4);
        disableButtons();

        countDownTimer2 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                if(l / 1000 == 3){
                    btnVisibility();
                    livesVisibility();
                }

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
                started = true;
                startGame();
            }
        }.start();
    }

    private void resetOnStart(){
        score = 0;
        time = 0;
        speed = 7;
        tempSpeed = 7;
        lives = 3;
        ((TextView)findViewById(R.id.textView)).setText("");
        ((TextView)findViewById(R.id.textView2)).setText("");
        ((TextView)findViewById(R.id.textView3)).setText("");
    }

    private void startGame(){
        resetOnStart();
        switch (mode){
            case 1:
                startGameTimeAttack();
                break;
            case 2:
                startGameClassic();
                break;
            case 3:
                startHardMode();
                startGameTimeAttack();
                break;
            case 4:
                startHardMode();
                startGameClassic();
                break;
        }
    }

    private void startGameTimeAttack() {
        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);

        redBtn.setOnClickListener(playActionListener);
        greenBtn.setOnClickListener(playActionListener);
        blueBtn.setOnClickListener(playActionListener);

        final TextView textView3 = findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);

        changeText();
        textView2.setText("Score\n" + String.valueOf(score));
        textView3.setText("Time\n30");
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                textView3.setText("Time\n" + String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                showAlertDialogButtonClicked("Time's up");
            }
        }.start();
    }

    private void startGameClassic(){
        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);

        redBtn.setOnClickListener(playActionListener);
        greenBtn.setOnClickListener(playActionListener);
        blueBtn.setOnClickListener(playActionListener);

        final TextView textView3 = findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);

        changeText();
        textView2.setText("Score\n" + String.valueOf(score));
        countDownTimer3 = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                ++time;
                --tempSpeed;
                textView3.setText("Time\n" + String.valueOf(time));

                if(speed > 1 && time % 10 == 0){
                    --speed;
                }

                if(tempSpeed == 0){
                    changeText();
                    --lives;
                    lostLife();
                    tempSpeed = speed;
                }
            }

            @Override
            public void onFinish() {
                countDownTimer3.cancel();
                countDownTimer3.start();
            }
        }.start();
    }

    private void startHardMode(){
        Button purpleBtn = findViewById(R.id.purpleBtn);
        Button yellowBtn = findViewById(R.id.yellowBtn);

        purpleBtn.setOnClickListener(playActionListener);
        yellowBtn.setOnClickListener(playActionListener);
    }

    private void btnVisibility(){
        Button purpleBtn = findViewById(R.id.purpleBtn);
        Button yellowBtn = findViewById(R.id.yellowBtn);

        if(mode == 3 || mode == 4){
            purpleBtn.setVisibility(View.VISIBLE);
            yellowBtn.setVisibility(View.VISIBLE);
        }else{
            purpleBtn.setVisibility(View.GONE);
            yellowBtn.setVisibility(View.GONE);
        }
    }

    private void livesVisibility(){
        if(mode == 2 || mode == 4){
            ((ImageView)findViewById(R.id.imageView1)).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.imageView2)).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.imageView3)).setVisibility(View.VISIBLE);

            ((ImageView)findViewById(R.id.imageView1)).setScaleY(1);
            ((ImageView)findViewById(R.id.imageView2)).setScaleY(1);
            ((ImageView)findViewById(R.id.imageView3)).setScaleY(1);
        }else{

            ((ImageView)findViewById(R.id.imageView1)).setVisibility(View.GONE);
            ((ImageView)findViewById(R.id.imageView2)).setVisibility(View.GONE);
            ((ImageView)findViewById(R.id.imageView3)).setVisibility(View.GONE);
        }
    }

    private void lostLife(){
        if(lives == 0){
            disableButtons();
            countDownTimer3.cancel();
            scaleView(((ImageView)findViewById(R.id.imageView1)), 1, 0);
            showAlertDialogButtonClicked("GAME OVER");
        }else if(lives == 1){
            scaleView(((ImageView)findViewById(R.id.imageView2)), 1, 0);
        }else if(lives == 2){
            scaleView(((ImageView)findViewById(R.id.imageView3)), 1, 0);
        }
    }

    private void disableButtons(){
        Button redBtn = findViewById(R.id.redBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button blueBtn = findViewById(R.id.blueBtn);
        Button purpleBtn = findViewById(R.id.purpleBtn);
        Button yellowBtn = findViewById(R.id.yellowBtn);

        redBtn.setOnClickListener(null);
        greenBtn.setOnClickListener(null);
        blueBtn.setOnClickListener(null);
        purpleBtn.setOnClickListener(null);
        yellowBtn.setOnClickListener(null);
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
                tempSpeed = speed;
                textView2.setText("Score\n" + String.valueOf(score));
                changeText();
            }else{
                switch (mode){
                    case 1:
                        countDownTimer.cancel();
                        showAlertDialogButtonClicked("GAME OVER");
                        break;
                    case 2:
                        --lives;
                        lostLife();
                        break;
                    case 3:
                        countDownTimer.cancel();
                        showAlertDialogButtonClicked("GAME OVER");
                        break;
                    case 4:
                        --lives;
                        lostLife();
                        break;
                }
            }
        }
    };

    private void changeText() {
        int random = new Random().nextInt(50);
        int random2 = new Random().nextInt(50);

        int numOfColors = 3;

        if(mode == 3 || mode == 4){
            numOfColors = 5;
        }

        random = random % numOfColors;
        random2 = random2 % numOfColors;

        TextView textView = findViewById(R.id.textView);

        fadeOutAnimation(textView, 750);
        fadeInAnimation(textView, 750);

        switch (random) {
            case 0:
                textView.setText("RED");
                break;
            case 1:
                textView.setText("GREEN");
                break;
            case 2:
                textView.setText("BLUE");
                break;
            case 3:
                textView.setText("PURPLE");
                break;
            case 4:
                textView.setText("YELLOW");
                break;
        }

        switch (random2) {
            case 0:
                textView.setTextColor(Color.parseColor("Red"));
                break;
            case 1:
                textView.setTextColor(Color.parseColor("Green"));
                break;
            case 2:
                textView.setTextColor(Color.parseColor("Blue"));
                break;
            case 3:
                textView.setTextColor(Color.parseColor("Purple"));
                break;
            case 4:
                textView.setTextColor(Color.parseColor("Yellow"));
                break;
        }
    }

    public void showAlertDialogButtonClicked(String s) {
        switch (mode){
            case 1:
                //timeattack
                finishUpdate(1);
                break;
            case 2:
                //classic
                finishUpdate(2);
                break;
            case 3:
                //timeattack hard
                finishUpdate(3);
                break;
            case 4:
                //classic hard
                finishUpdate(4);
                break;
        }


        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s);
        builder.setMessage("You scored " + score + ".");

        // add a button
        builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                disableButtons();
                resetOnStart();
                livesVisibility();
                started = false;
                countDownTimer2.start();
            }
        });
        builder.setNegativeButton("Change Mode", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(PlayActivity.this, ModeActivity.class));
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if(started){
            if(mode == 1 || mode == 3){
                countDownTimer.cancel();
            }else{
                countDownTimer3.cancel();
            }
            showAlertDialogButtonClicked("GAME OVER");
        }else{
            countDownTimer2.cancel();

            finish();
            startActivity(new Intent(PlayActivity.this, ModeActivity.class));
        }
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

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    private void updateScore(int ID, int score, int mode) {
        APIupdateServisi api = RetrofitUpdateCall.getApi();
        Call<String> call;

        call = api.setQuery(ID, score, mode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("");
                } else {
                    System.out.println("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    void setID() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://rgb.dx.am/")
                .build();

        APIgetID scalarService = retrofit.create(APIgetID.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/getID.php");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String responseString = response.body();
                    try {
                        JSONObject object = new JSONObject(responseString);
                        JSONArray Jarray = object.getJSONArray("data");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject Jasonobject = Jarray.getJSONObject(i);

                            int ID = Integer.parseInt(Jasonobject.get("ID").toString());
                            ID++;

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putInt("ID", ID);
                            // Apply the edits!
                            editor.apply();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                } else {
                    //System.out.println(response.body() + "ETOOOOO");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void finishUpdate(int mode){
        SharedPreferences settings;
        int myScore = 0;
        switch (mode){
            case 1:
                //timeattack
                settings = getApplicationContext().getSharedPreferences("score", 0);
                myScore = settings.getInt("score", 0);
                break;
            case 2:
                //classic
                settings = getApplicationContext().getSharedPreferences("classic", 0);
                myScore = settings.getInt("classic", 0);
                break;
            case 3:
                //timeattack hard
                settings = getApplicationContext().getSharedPreferences("timeattack_hard", 0);
                myScore = settings.getInt("timeattackHard", 0);
                break;
            case 4:
                //classic hard
                settings = getApplicationContext().getSharedPreferences("classic_hard", 0);
                myScore = settings.getInt("classicHard", 0);
                break;
        }
        // Get from the SharedPreferences


        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if (!(myName.length() > 0)) {
            settings = getApplicationContext().getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);
            if (myID == 0) {
                setID();
                settings = getApplicationContext().getSharedPreferences("ID", 0);
                myID = settings.getInt("ID", 0);
                addNewScore(myName,score);
            }
            setID();
            settings = getApplicationContext().getSharedPreferences("name", 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("name", "DefaultUser");
            // Apply the edits!
            editor.apply();

            myName = "DefaultUser";
        }

        if (myScore < score) {
            settings = getApplicationContext().getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);
            updateScore(myID, score, mode);

            SharedPreferences.Editor editor;

            switch (mode){
                case 1:
                    //timeattack
                    settings = getApplicationContext().getSharedPreferences("score", 0);
                    editor = settings.edit();
                    editor.putInt("score", score);
                    break;
                case 2:
                    //classic
                    settings = getApplicationContext().getSharedPreferences("classic", 0);
                    editor = settings.edit();
                    editor.putInt("classic", score);
                    break;
                case 3:
                    //timeattack hard
                    settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
                    editor = settings.edit();
                    editor.putInt("timeattackHard", score);
                    break;
                case 4:
                    //classic hard
                    settings = getApplicationContext().getSharedPreferences("classicHard", 0);
                    editor = settings.edit();
                    editor.putInt("classicHard", score);
                    break;

                    default:
                        settings = getApplicationContext().getSharedPreferences("score", 0);
                        editor = settings.edit();
                        editor.putInt("score", score);
                        break;
            }

            // Apply the edits!
            editor.apply();
        }
    }

    private void addNewScore(String name, int score) {
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(name, score);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("dadsadasdas");
                } else {
                    System.out.println("NIJEdadsadasdas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}


