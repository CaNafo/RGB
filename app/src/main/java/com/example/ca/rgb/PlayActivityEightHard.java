package com.example.ca.rgb;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.ca.rgb.StaticMethods.getPoints;
import static com.example.ca.rgb.StaticMethods.isConnectedToNetwork;
import static com.example.ca.rgb.StaticMethods.scaleView;
import static com.example.ca.rgb.StaticMethods.fadeInAnimation;
import static com.example.ca.rgb.StaticMethods.fadeOutAnimation;
import static com.example.ca.rgb.StaticMethods.getMusic;
import static com.example.ca.rgb.StaticMethods.getSound;
import static com.example.ca.rgb.StaticMethods.setPoints;
import static com.example.ca.rgb.StaticMethods.switchButton;
import static com.example.ca.rgb.StaticScoreMethods.finishUpdate;

public class PlayActivityEightHard extends AppCompatActivity {
    private int score = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private CountDownTimer countDownTimer3;
    private boolean started = false;
    private boolean rewarded = false;
    private boolean gameOver = false;
    private boolean dialogShowed = false;
    private int time = 0;
    private int speed = 2000;
    private int lives = 3;
    private int points = 0;
    private int pointsIncrement = 10;
    private List<String> listColors = Arrays.asList("red", "green", "blue", "purple", "yellow", "black", "orange", "white");
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
                tempTimer(speed);
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
        ((Button)findViewById(R.id.redBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.greenBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.blueBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.purpleBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.yellowBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.blackBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.orangeBtn)).setOnTouchListener(null);
        ((Button)findViewById(R.id.whiteBtn)).setOnTouchListener(null);
    }

    private void enableButtons(){
        ((Button)findViewById(R.id.redBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.greenBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.blueBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.purpleBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.yellowBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.blackBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.orangeBtn)).setOnTouchListener(playTouchListener);
        ((Button)findViewById(R.id.whiteBtn)).setOnTouchListener(playTouchListener);
    }

    private void startGame() {
        changeText();
        final TextView textView3 = findViewById(R.id.textView3);
        TextView textView2 = findViewById(R.id.textView2);

        textView2.setText("Score\n" + String.valueOf(score));
        tempTimer(speed);
        countDownTimer2 = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                ++time;
                textView3.setText("Time\n" + String.valueOf(time));

                if(time % 10 == 9 ){
                    if(getSound(getApplicationContext()) == 1){
                        MediaPlayer mp = MediaPlayer.create(PlayActivityEightHard.this, R.raw.shuffle);
                        mp.start();
                    }
                }

                if(time % 10 == 0){
                    changeButtons();
                }

                if(time % 5 == 0){
                    printPoints();
                }

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
        if(score < 50){
            speed = 2000;
        }else{
            speed = 1000;
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
                    MediaPlayer mp = MediaPlayer.create(PlayActivityEightHard.this, R.raw.error);
                    mp.start();
                }
                --lives;
                changeText();
                lostLife();
            }
        }.start();
    }

    private void btnVisibility() {
        ((Button)findViewById(R.id.purpleBtn)).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.yellowBtn)).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.blackBtn)).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.orangeBtn)).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.whiteBtn)).setVisibility(View.VISIBLE);
    }

    private void livesVisibility() {
        ((ImageView) findViewById(R.id.imageView1)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.imageView2)).setVisibility(View.VISIBLE);
        ((ImageView) findViewById(R.id.imageView3)).setVisibility(View.VISIBLE);

        scaleView((ImageView) findViewById(R.id.imageView1), 0, 1);
        scaleView((ImageView) findViewById(R.id.imageView2), 0, 1);
        scaleView((ImageView) findViewById(R.id.imageView3), 0, 1);
    }

    View.OnTouchListener playTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                Button button = (Button) view;
                TextView textView = findViewById(R.id.textView);
                TextView textView2 = findViewById(R.id.textView2);

                String colorName = String.valueOf(textView.getText());
                String action = String.valueOf(button.getTag());

                if (colorName.equalsIgnoreCase(action)) {
                    ++score;
                    textView2.setText("Score\n" + String.valueOf(score));
                    countDownTimer3.cancel();
                    tempTimer(speed);
                    changeText();
                } else {
                    if(getSound(getApplicationContext()) == 1){
                        MediaPlayer mp = MediaPlayer.create(PlayActivityEightHard.this, R.raw.error);
                        mp.start();
                    }
                    --lives;
                    lostLife();
                }
            }

            return false;
        }
    };

    private void changeText() {
        int random = new Random().nextInt(50);
        int random2 = new Random().nextInt(50);

        int numOfColors = 8;

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

    private void changeButtons(){
        Collections.shuffle(listColors);

        switchButton((Button)findViewById(R.id.redBtn), listColors.get(0));
        switchButton((Button)findViewById(R.id.greenBtn), listColors.get(1));
        switchButton((Button)findViewById(R.id.blueBtn), listColors.get(2));
        switchButton((Button)findViewById(R.id.purpleBtn), listColors.get(3));
        switchButton((Button)findViewById(R.id.yellowBtn), listColors.get(4));
        switchButton((Button)findViewById(R.id.blackBtn), listColors.get(5));
        switchButton((Button)findViewById(R.id.orangeBtn), listColors.get(6));
        switchButton((Button)findViewById(R.id.whiteBtn), listColors.get(7));
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
                if(isConnectedToNetwork(getApplicationContext())){
                    rewardAlertDialog();
                }else{
                    showAlertDialogButtonClicked("GAME OVER");
                }
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
        fadeInAnimation(textView4, 1000);
        if(rewarded == true){
            textView4.setText("+" + String.valueOf(pointsIncrement + i) + " points");
        }else{
            textView4.setText("+" + String.valueOf(pointsIncrement + i) + " points");
        }
        fadeOutAnimation(textView4, 1000);

        points += pointsIncrement + i;
    }

    private void rewardAlertDialog(){
        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME OVER");
        builder.setMessage("Watch video and continue playing.");

        // add a button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //adReward();
                rewarded = true;
                lives = 1;
                gameOver = false;
                scaleView(((ImageView)findViewById(R.id.imageView1)), 0, 1);
                continueReward();
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
        final AlertDialog dialog = builder.create();
        if (this.hasWindowFocus()) {
            dialog.show();

            // Initially disable the button
            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);

            CountDownTimer cdt = new CountDownTimer(1500, 1500) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    // Initially disable the button
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
                }
            }.start();
        }
    }

    public void showAlertDialogButtonClicked(String s) {
        finishUpdate(7, score, getApplicationContext());

        addPoints();

        int r = new Random().nextInt(100);

        if(r < 21){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {

            }
        }

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s);
        builder.setMessage("You scored " + score + " and earned " + points + " XP.");

        // add a button
        builder.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(PlayActivityEightHard.this, PlayActivityEightHard.class));
            }
        });
        builder.setNegativeButton("Change Mode", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle b = new Bundle();
                b.putInt("mode", 7);
                Intent intent = new Intent(PlayActivityEightHard.this, ModeActivity.class);
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
            Bundle b = new Bundle();
            b.putInt("mode", 7);
            Intent intent = new Intent(PlayActivityEightHard.this, ModeActivity.class);
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
