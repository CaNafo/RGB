package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Random;

import static com.example.ca.rgb.StaticMethods.getPoints;
import static com.example.ca.rgb.StaticMethods.scaleView;
import static com.example.ca.rgb.StaticMethods.fadeInAnimation;
import static com.example.ca.rgb.StaticMethods.fadeOutAnimation;
import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getSound;
import static com.example.ca.rgb.StaticMethods.setPoints;
import static com.example.ca.rgb.StaticScoreMethods.finishUpdate;

public class PlayActivityClassic extends AppCompatActivity {
    private int score = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private CountDownTimer countDownTimer3;
    private boolean started = false;
    private boolean rewarded = false;
    private boolean gameOver = false;
    private boolean dialogShowed = false;
    private int mode = -1;
    private int time = 0;
    private int points = 0;
    private int pointsIncrement = 0;
    private int speed = 6000;
    private int lives = 3;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }

        initializeAds();

        Bundle b = getIntent().getExtras();
        if (b != null)
            mode = b.getInt("mode");

        setPointsIncrement();

        btnVisibility();

        final TextView textView4 = findViewById(R.id.textView4);
        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                if (l / 1000 == 3) {
                    livesVisibility();
                }

                fadeOutAnimation(textView4, 750);
                fadeInAnimation(textView4, 750);
                if (l / 1000 != 0) {
                    textView4.setText(String.valueOf(l / 1000));
                } else {
                    textView4.setText("START!");
                }
            }

            @Override
            public void onFinish() {
                textView4.setText("");
                started = true;
                enableButtons();
                startGame();
            }
        }.start();
    }

    private void initializeAds(){
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);

        mInterstitialAd = new InterstitialAd(this);
        defaultInputText = getResources().getString(R.string.ad_id_interstitial);
        mInterstitialAd.setAdUnitId(String.valueOf(defaultInputText));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);

        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        String defaultInputText = getResources().getString(R.string.ad_id_rewarded);
        mRewardedVideoAd.loadAd(defaultInputText, new AdRequest.Builder().build());
    }

    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {

        }

        @Override
        public void onRewardedVideoAdOpened() {

        }

        @Override
        public void onRewardedVideoStarted() {

        }

        @Override
        public void onRewardedVideoAdClosed() {
            loadRewardedVideoAd();
            if(rewarded == false){
                CountDownTimer cdt = new CountDownTimer(300,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        showAlertDialogButtonClicked("GAME OVER");
                    }
                }.start();
            }else{
                lives = 1;
                gameOver = false;
                scaleView(((ImageView)findViewById(R.id.imageView1)), 0, 1);
                continueReward();
            }
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {
            rewarded = true;
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {

        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {

        }

        @Override
        public void onRewardedVideoCompleted() {

        }
    };

    private void continueReward(){
        final TextView textView4 = findViewById(R.id.textView4);
        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                fadeOutAnimation(textView4, 750);
                fadeInAnimation(textView4, 750);
                if (l / 1000 != 0) {
                    textView4.setText(String.valueOf(l / 1000));
                } else {
                    textView4.setText("START!");
                }
            }

            @Override
            public void onFinish() {
                textView4.setText("");
                enableButtons();
                countDownTimer2.start();
            }
        }.start();
    }

    private void adReward(){
        if(mRewardedVideoAd.isLoaded()){
            mRewardedVideoAd.show();
        }
    }

    private void disableButtons(){
        ((Button)findViewById(R.id.redBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.greenBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.blueBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.purpleBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.yellowBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.blackBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.orangeBtn)).setOnClickListener(null);
        ((Button)findViewById(R.id.whiteBtn)).setOnClickListener(null);
    }

    private void enableButtons(){
        switch (mode){
            case 2:
                ((Button)findViewById(R.id.redBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.greenBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.blueBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.purpleBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.yellowBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.blackBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.orangeBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.whiteBtn)).setOnClickListener(null);
                break;
            case 4:
                ((Button)findViewById(R.id.redBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.greenBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.blueBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.purpleBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.yellowBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.blackBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.orangeBtn)).setOnClickListener(null);
                ((Button)findViewById(R.id.whiteBtn)).setOnClickListener(null);
                break;
            case 6:
                ((Button)findViewById(R.id.redBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.greenBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.blueBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.purpleBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.yellowBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.blackBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.orangeBtn)).setOnClickListener(playActionListener);
                ((Button)findViewById(R.id.whiteBtn)).setOnClickListener(playActionListener);
                break;
            case 8:
                //10 buttona
                break;
        }
    }

    private void startGame() {
        final TextView textView3 = findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);

        changeText();
        textView2.setText("Score\n" + String.valueOf(score));
        tempTimer(speed);
        countDownTimer2 = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                ++time;
                textView3.setText("Time\n" + String.valueOf(time));

                setSpeed();
            }

            @Override
            public void onFinish() {
                countDownTimer2.cancel();
                countDownTimer2.start();
            }
        }.start();
    }

    private void setSpeed(){
        if(score < 10){
            speed = 6000;
        }else if(score >= 10 && score < 20){
            speed = 5000;
        }else if(score >= 20 && score < 30){
            speed = 4000;
        }else if(score >= 30 && score < 40){
            speed = 3000;
        }else if(score >= 40 && score < 50){
            speed = 2000;
        }else if(score >= 50 && score < 100){
            speed = 1000;
        }else{
            speed = 700;
        }
    }

    private void setPointsIncrement(){
        switch (mode){
            case 2:
                pointsIncrement = 5;
                break;
            case 4:
                pointsIncrement = 15;
                break;
            case 6:
                pointsIncrement = 25;
                break;
        }
    }

    private void tempTimer(int speed){
        countDownTimer3 = new CountDownTimer(speed, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(getSound(getApplicationContext()) == 1){
                    MediaPlayer mp = MediaPlayer.create(PlayActivityClassic.this, R.raw.error);
                    mp.start();
                }
                --lives;
                changeText();
                lostLife();
            }
        }.start();
    }

    private void btnVisibility() {
        switch(mode){
            case 2:
                ((Button)findViewById(R.id.purpleBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.yellowBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.blackBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.orangeBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.whiteBtn)).setVisibility(View.GONE);
                break;
            case 4:
                ((Button)findViewById(R.id.purpleBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.yellowBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.blackBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.orangeBtn)).setVisibility(View.GONE);
                ((Button)findViewById(R.id.whiteBtn)).setVisibility(View.GONE);
                break;
            case 6:
                ((Button)findViewById(R.id.purpleBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.yellowBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.blackBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.orangeBtn)).setVisibility(View.VISIBLE);
                ((Button)findViewById(R.id.whiteBtn)).setVisibility(View.VISIBLE);
                break;
            case 8:
                //ako zatreba 10 buttona
                break;
        }
    }

    private void livesVisibility() {
        ((ImageView) findViewById(R.id.imageView1)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.imageView2)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.imageView3)).setVisibility(View.VISIBLE);

        scaleView((ImageView) findViewById(R.id.imageView1), 0, 1);
        scaleView((ImageView) findViewById(R.id.imageView2), 0, 1);
        scaleView((ImageView) findViewById(R.id.imageView3), 0, 1);
    }

    View.OnClickListener playActionListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button button = (Button) v;
            TextView textView = findViewById(R.id.textView);
            TextView textView2 = findViewById(R.id.textView2);

            String colorName = String.valueOf(textView.getText());
            String action = String.valueOf(button.getTag());

            if (colorName.equalsIgnoreCase(action)) {
                ++score;
                if(score % 30 == 0){
                    printPoints();
                }
                textView2.setText("Score\n" + String.valueOf(score));
                countDownTimer3.cancel();
                tempTimer(speed);
                changeText();
            } else {
                if(getSound(getApplicationContext()) == 1){
                    MediaPlayer mp = MediaPlayer.create(PlayActivityClassic.this, R.raw.error);
                    mp.start();
                }
                --lives;
                lostLife();
            }
        }
    };

    private void changeText() {
        int random = new Random().nextInt(50);
        int random2 = new Random().nextInt(50);

        int numOfColors = 3;

        if (mode == 4 ) {
            numOfColors = 5;
        }else if(mode == 6){
            numOfColors = 8;
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
            case 5:
                textView.setText("BLACK");
                break;
            case 6:
                textView.setText("ORANGE");
                break;
            case 7:
                textView.setText("WHITE");
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
            case 5:
                textView.setTextColor(Color.parseColor("Black"));
                break;
            case 6:
                textView.setTextColor(Color.parseColor("#ffa500"));
                break;
            case 7:
                textView.setTextColor(Color.parseColor("White"));
                break;
        }
    }

    private void lostLife() {
        if (lives == 0) {
            disableButtons();
            gameOver = true;
            countDownTimer2.cancel();
            countDownTimer3.cancel();
            scaleView(((ImageView) findViewById(R.id.imageView1)), 1, 0);
            if(rewarded){
                showAlertDialogButtonClicked("GAME OVER");
            }else{
                rewardAlertDialog();
            }
        } else if (lives == 1) {
            scaleView(((ImageView) findViewById(R.id.imageView2)), 1, 0);
            countDownTimer3.cancel();
            countDownTimer3.start();
        } else if (lives == 2) {
            scaleView(((ImageView) findViewById(R.id.imageView3)), 1, 0);
            countDownTimer3.cancel();
            countDownTimer3.start();
        }
    }

    private void addPoints(){
        int temp = getPoints(getApplicationContext());
        temp += points;

        setPoints(getApplicationContext(), temp);
    }

    private void printPoints(){
        int i = 0;

        if(score > 100){
            i = 5;
        }
        TextView textView4 = findViewById(R.id.textView4);
        fadeInAnimation(textView4, 1500);
        if(rewarded == true){
            textView4.setText("+" + String.valueOf(pointsIncrement + i) + " points");
        }else{
            textView4.setText("+" + String.valueOf(pointsIncrement + i) + " points");
        }
        fadeOutAnimation(textView4, 1500);

        points += pointsIncrement + i;
    }

    private void rewardAlertDialog(){
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reward");
        builder.setMessage("Watch video and continue playing.");

        // add a button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adReward();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CountDownTimer cdt = new CountDownTimer(300,100) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        showAlertDialogButtonClicked("GAME OVER");
                    }
                }.start();
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialog.show();
        }
    }

    public void showAlertDialogButtonClicked(String s) {
        finishUpdate(mode, score, getApplicationContext());
        addPoints();

        int r = new Random().nextInt(100);

        if(r < 15){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s);
        builder.setMessage("You scored " + score + " and earned " + points + " points.");

        // add a button
        builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PlayActivityClassic.this, PlayActivityClassic.class);
                Bundle b = new Bundle();
                b.putInt("mode", mode);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Change Mode", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PlayActivityClassic.this, ModeActivity.class);
                Bundle b = new Bundle();
                b.putInt("mode", mode);

                intent.putExtras(b);
                finish();
                startActivity(intent);
            }
        });

        builder.setCancelable(false);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialogShowed = true;
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        if (started) {
            countDownTimer2.cancel();
            countDownTimer3.cancel();
            showAlertDialogButtonClicked("GAME OVER");
        } else {
            countDownTimer.cancel();
            Intent intent = new Intent(PlayActivityClassic.this, ModeActivity.class);
            Bundle b = new Bundle();
            b.putInt("mode", mode);

            intent.putExtras(b);
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getMusic(getApplicationContext()) == 1){
            MenuActivity.mp.start();
        }

        if(gameOver && !dialogShowed){
            CountDownTimer cdt = new CountDownTimer(300, 300) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    showAlertDialogButtonClicked("GAME OVER");
                }
            }.start();
        }
    }
}
