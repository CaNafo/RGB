package com.example.ca.rgb;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIgetPosition;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Size;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIrefreshScore;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;

public class ScoreActivity extends AppCompatActivity {
    int mode = 1;
    int buttonMode;
    boolean btnClicked = false;
    String responseString;
    TextView tittleTxt;
    private AdView mAdView;
    Button nextBtn;
    Button previousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if (getMusic(getApplicationContext()) == 1) {
            mp.start();
        }

        tittleTxt = findViewById(R.id.titleTxt);
        tittleTxt.setText("Top 10 Time Attack players");

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int myScore = settings.getInt("ID", 0);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(nextListener);

        previousBtn = findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(backListener);

        Button refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(refreshListener);

        Bundle b = getIntent().getExtras();
        if (b != null)
            buttonMode = b.getInt("mode");

        if (buttonMode == 1 || buttonMode == 2) {
            offlineLoad("timeAttack");
            getRetrofitObject("timeAttack");
            getUserPosition("timeAttack");
        } else if (buttonMode == 3 || buttonMode == 4) {
            offlineLoad("timeattackHard");
            getRetrofitObject("timeattackHard");
            getUserPosition("timeattackHard");
        } else if (buttonMode == 5 || buttonMode == 6) {
            offlineLoad("timeAttack8");
            getRetrofitObject("timeAttack8");
            getUserPosition("timeAttack8");
        } else if (buttonMode == 7 || buttonMode == 8) {
            tittleTxt.setText("Top 10 players");
            nextBtn.setVisibility(View.INVISIBLE);
            previousBtn.setVisibility(View.INVISIBLE);
            offlineLoad("8Hard");
            getRetrofitObject("8Hard");
            getUserPosition("8Hard");
        }


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    void offlineLoad(String mode) {
        TextView textView = findViewById(R.id.myScoreTxt);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
        int myScore = 0;
        switch (mode) {
            case "timeAttack":
                settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
                myScore = settings.getInt("timeAttack", 0);
                break;
            case "classic":
                settings = getApplicationContext().getSharedPreferences("classic", 0);
                myScore = settings.getInt("classic", 0);
                break;
            case "timeattackHard":
                settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
                myScore = settings.getInt("timeattackHard", 0);
                break;
            case "classicHard":
                settings = getApplicationContext().getSharedPreferences("classicHard", 0);
                myScore = settings.getInt("classicHard", 0);
                break;
            case "timeAttack8":
                settings = getApplicationContext().getSharedPreferences("timeAttack8", 0);
                myScore = settings.getInt("timeAttack8", 0);
                break;
            case "classic8":
                settings = getApplicationContext().getSharedPreferences("classic8", 0);
                myScore = settings.getInt("classic8", 0);
                break;
            case "8Hard":
                settings = getApplicationContext().getSharedPreferences("8Hard", 0);
                myScore = settings.getInt("8Hard", 0);
                break;

        }

        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");
        if (!(myName.length() > 0)) {
            textView.setText("    Name:  " + myName + ",    Score:  " + myScore);

            settings = getApplicationContext().getSharedPreferences("name", 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("name", "DefaultUser");
            // Apply the edits!
            editor.apply();
        } else {
            textView.setText("Name:  " + myName + ",    Score:  " + myScore);
        }

        LinearLayout noumber = findViewById(R.id.numberLayout);
        noumber.removeAllViews();

        LinearLayout name = findViewById(R.id.nameLayout);
        name.removeAllViews();

        LinearLayout image = findViewById(R.id.imageLayout);
        image.removeAllViews();

        LinearLayout score = findViewById(R.id.scoreLayout);
        score.removeAllViews();

        TextView noumberTxt = new TextView(noumber.getContext());
        noumberTxt.setText("1.");
        noumberTxt.setTextColor(Color.parseColor("#FFFFFF"));
        noumberTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        noumberTxt.setTypeface(null, Typeface.BOLD);
        noumberTxt.setGravity(Gravity.CENTER);
        noumber.addView(noumberTxt);

        TextView nameTxt = new TextView(name.getContext());
        nameTxt.setText("Loading...");
        nameTxt.setTextColor(Color.parseColor("#FFFFFF"));
        nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        nameTxt.setTypeface(null, Typeface.BOLD);
        nameTxt.setGravity(Gravity.CENTER);
        name.addView(nameTxt);


        TextView scoreTxt = new TextView(score.getContext());
        scoreTxt.setText("Loading...");
        scoreTxt.setTextColor(Color.parseColor("#FFFFFF"));
        scoreTxt.setTypeface(null, Typeface.BOLD);
        scoreTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        scoreTxt.setGravity(Gravity.CENTER);
        score.addView(scoreTxt);
    }

    void getRetrofitObject(String mode) {
        final String jsonMode = mode;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();

        APIogovor scalarService = retrofit.create(APIogovor.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/score_read.php", mode);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseString = response.body();
                    try {
                        JSONObject object = new JSONObject(responseString);
                        JSONArray Jarray = object.getJSONArray("data");

                        LinearLayout noumber = findViewById(R.id.numberLayout);
                        noumber.removeAllViews();

                        LinearLayout name = findViewById(R.id.nameLayout);
                        name.removeAllViews();

                        LinearLayout image = findViewById(R.id.imageLayout);
                        image.removeAllViews();

                        LinearLayout score = findViewById(R.id.scoreLayout);
                        score.removeAllViews();

                        TextView noumberTxt = new TextView(noumber.getContext());
                        noumberTxt.setText("No.");
                        noumberTxt.setTextColor(Color.parseColor("#FFFFFF"));
                        noumberTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                        noumberTxt.setTypeface(null, Typeface.BOLD);
                        noumberTxt.setGravity(Gravity.CENTER);
                        noumber.addView(noumberTxt);

                        ImageView avatarImg ;

                        TextView nameTxt = new TextView(name.getContext());
                        nameTxt.setText("Name");
                        nameTxt.setTextColor(Color.parseColor("#FFFFFF"));
                        nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                        nameTxt.setTypeface(null, Typeface.BOLD);
                        nameTxt.setGravity(Gravity.CENTER);
                        name.addView(nameTxt);


                        TextView scoreTxt = new TextView(score.getContext());
                        scoreTxt.setText("Score");
                        scoreTxt.setTextColor(Color.parseColor("#FFFFFF"));
                        scoreTxt.setTypeface(null, Typeface.BOLD);
                        scoreTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                        scoreTxt.setGravity(Gravity.CENTER);
                        score.addView(scoreTxt);

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject Jasonobject = Jarray.getJSONObject(i);

                            noumberTxt = new TextView(noumber.getContext());
                            noumberTxt.setText((i + 1) + ".");
                            noumberTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
                            noumberTxt.setGravity(Gravity.CENTER);
                            noumberTxt.setTypeface(null, Typeface.BOLD);
                            noumber.addView(noumberTxt);

                            avatarImg = new ImageView(image.getContext());
                            String avatar = Jasonobject.getString("avatar");

                            switch (avatar){
                                case "avatar1":
                                    avatarImg.setImageResource(R.drawable.avatar1_small);
                                    break;
                                case "avatar2":
                                    avatarImg.setImageResource(R.drawable.avatar2_small);
                                    break;
                                case "avatar3":
                                    avatarImg.setImageResource(R.drawable.avatar3_small);
                                    break;
                                case "avatar4":
                                    avatarImg.setImageResource(R.drawable.avatar4_small);
                                    break;
                                case "avatar5":
                                    avatarImg.setImageResource(R.drawable.avatar5_small);
                                    break;
                                case "avatar6":
                                    avatarImg.setImageResource(R.drawable.avatar6_small);
                                    break;
                                case "avatar7":
                                    avatarImg.setImageResource(R.drawable.avatar7_small);
                                    break;
                                case "avatar8":
                                    avatarImg.setImageResource(R.drawable.avatar8_small);
                                    break;
                                case "avatar9":
                                    avatarImg.setImageResource(R.drawable.avatar9_small);
                                    break;
                                case "avatar10":
                                    avatarImg.setImageResource(R.drawable.avatar1a_small);
                                    break;

                                    default:
                                        avatarImg.setImageResource(R.drawable.avatar1_small);
                                        break;
                            }
                            avatarImg.setLayoutParams(new LinearLayout.LayoutParams(45, 45));

                            //image.addView(avatarImg);

                            nameTxt = new TextView(name.getContext());
                            nameTxt.setText((Jasonobject.getString("name")));
                            nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
                            nameTxt.setGravity(Gravity.CENTER);
                            nameTxt.setTypeface(null, Typeface.BOLD);

                            LinearLayout nameLinear = new LinearLayout(name.getContext());
                            nameLinear.setOrientation(LinearLayout.HORIZONTAL);
                            nameLinear.addView(avatarImg);
                            nameLinear.addView(nameTxt);

                            name.addView(nameLinear);

                            scoreTxt = new TextView(score.getContext());
                            scoreTxt.setText(Jasonobject.getString(jsonMode));
                            scoreTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
                            scoreTxt.setGravity(Gravity.CENTER);
                            scoreTxt.setTypeface(null, Typeface.BOLD);
                            score.addView(scoreTxt);

                        }

                       /* TextView textView = findViewById(R.id.nameTxt);
                        textView.setText(name);
                        textView = findViewById(R.id.scoreTxt);
                        textView.setText(score);
                        textView = findViewById(R.id.numberTxt);
                        textView.setText(number);*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                } else {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    void getUserPosition(String mode) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int myID = settings.getInt("ID", 0);

        int myScore = 0;
        switch (mode) {
            case "timeAttack":
                settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
                myScore = settings.getInt("timeAttack", 0);
                break;
            case "classic":
                settings = getApplicationContext().getSharedPreferences("classic", 0);
                myScore = settings.getInt("classic", 0);
                break;
            case "timeattackHard":
                settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
                myScore = settings.getInt("timeattackHard", 0);
                break;
            case "classicHard":
                settings = getApplicationContext().getSharedPreferences("classicHard", 0);
                myScore = settings.getInt("classicHard", 0);
                break;
            case "timeAttack8":
                settings = getApplicationContext().getSharedPreferences("timeAttack8", 0);
                myScore = settings.getInt("timeAttack8", 0);
                break;
            case "classic8":
                settings = getApplicationContext().getSharedPreferences("classic8", 0);
                myScore = settings.getInt("classic8", 0);
                break;
            case "8Hard":
                settings = getApplicationContext().getSharedPreferences("8Hard", 0);
                myScore = settings.getInt("8Hard", 0);
                break;

        }

        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if (myID == 0) {
            setID(mode);
            settings = getApplicationContext().getSharedPreferences("ID", 0);
            myID = settings.getInt("ID", 0);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();

        APIgetPosition scalarService = retrofit.create(APIgetPosition.class);
        APIgetPosition api = scalarService;
        Call<String> stringCall = scalarService.getStringResponse("/RGB/getUserPosition.php", myID, mode);

        final String modeJson = mode;

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

                            TextView textView = findViewById(R.id.myScoreTxt);
                            SharedPreferences settings;
                            int myScore = 0;
                            switch (modeJson) {
                                case "timeAttack":
                                    settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
                                    myScore = settings.getInt("timeAttack", 0);
                                    break;
                                case "classic":
                                    settings = getApplicationContext().getSharedPreferences("classic", 0);
                                    myScore = settings.getInt("classic", 0);
                                    break;
                                case "timeattackHard":
                                    settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
                                    myScore = settings.getInt("timeattackHard", 0);
                                    break;
                                case "classicHard":
                                    settings = getApplicationContext().getSharedPreferences("classicHard", 0);
                                    myScore = settings.getInt("classicHard", 0);
                                    break;
                                case "timeAttack8":
                                    settings = getApplicationContext().getSharedPreferences("timeAttack8", 0);
                                    myScore = settings.getInt("timeAttack8", 0);
                                    break;
                                case "classic8":
                                    settings = getApplicationContext().getSharedPreferences("classic8", 0);
                                    myScore = settings.getInt("classic8", 0);
                                    break;
                                case "8Hard":
                                    settings = getApplicationContext().getSharedPreferences("8Hard", 0);
                                    myScore = settings.getInt("8Hard", 0);
                                    break;

                            }


                            settings = getApplicationContext().getSharedPreferences("name", 0);
                            String myName = settings.getString("name", "");
                            if (!(myName.length() > 0)) {
                                textView.setText("Position:  " + Jasonobject.get("position") + " ,    Name:  " + myName + ",    Score:  " + myScore);

                                settings = getApplicationContext().getSharedPreferences("name", 0);
                                SharedPreferences.Editor editor = settings.edit();

                                editor.putString("name", "DefaultUser");
                                // Apply the edits!
                                editor.apply();
                            } else {
                                textView.setText("Position:  " + Jasonobject.get("position") + " ,    Name:  " + myName + ",    Score:  " + myScore);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                } else {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    void setID(String mode) {
        final String modeJson = mode;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
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
                            settings = getApplicationContext().getSharedPreferences("ID", 0);
                            int myID = settings.getInt("ID", 0);

                            int myScore = 0;
                            switch (modeJson) {
                                case "timeAttack":
                                    settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
                                    myScore = settings.getInt("timeAttack", 0);
                                    break;
                                case "classic":
                                    settings = getApplicationContext().getSharedPreferences("classic", 0);
                                    myScore = settings.getInt("classic", 0);
                                    break;
                                case "timeattackHard":
                                    settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
                                    myScore = settings.getInt("timeattackHard", 0);
                                    break;
                                case "classicHard":
                                    settings = getApplicationContext().getSharedPreferences("classicHard", 0);
                                    myScore = settings.getInt("classicHard", 0);
                                    break;
                                case "timeAttack8":
                                    settings = getApplicationContext().getSharedPreferences("timeAttack8", 0);
                                    myScore = settings.getInt("timeAttack8", 0);
                                    break;
                                case "classic8":
                                    settings = getApplicationContext().getSharedPreferences("classic8", 0);
                                    myScore = settings.getInt("classic8", 0);
                                    break;
                                case "8Hard":
                                    settings = getApplicationContext().getSharedPreferences("8Hard", 0);
                                    myScore = settings.getInt("8Hard", 0);
                                    break;

                            }

                            settings = getApplicationContext().getSharedPreferences("name", 0);
                            String myName = settings.getString("name", "");

                            settings = getApplicationContext().getSharedPreferences("avatar", 0);
                            String avatar = settings.getString("avatar", "");

                            if (!(myName.length() > 0)) {
                                settings = getApplicationContext().getSharedPreferences("name", 0);
                                editor = settings.edit();
                                editor.putString("name", "DefaultUser");
                                // Apply the edits!
                                editor.apply();

                                addNewScore("DefaultUser",avatar);

                            } else {
                                addNewScore(myName,avatar);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // todo: do something with the response string
                } else {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void addNewScore(String name, String avatar) {
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(name,avatar);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnClicked = true;
            if (mode == 2)
                mode = 1;
            else
                mode++;

            switch (mode) {
                case 1:
                    tittleTxt.setText("Top 10 Time Attack players");
                    if (buttonMode == 1 || buttonMode == 2) {
                        offlineLoad("timeAttack");
                        getRetrofitObject("timeAttack");
                        getUserPosition("timeAttack");
                    } else if (buttonMode == 3 || buttonMode == 4) {
                        offlineLoad("timeattackHard");
                        getRetrofitObject("timeattackHard");
                        getUserPosition("timeattackHard");
                    } else if (buttonMode == 5 || buttonMode == 6) {
                        offlineLoad("timeAttack8");
                        getRetrofitObject("timeAttack8");
                        getUserPosition("timeAttack8");
                    } else if (buttonMode == 7 || buttonMode == 8) {
                        tittleTxt.setText("Top 10 players");
                        nextBtn.setVisibility(View.INVISIBLE);
                        previousBtn.setVisibility(View.INVISIBLE);
                        offlineLoad("8Hard");
                        getRetrofitObject("8Hard");
                        getUserPosition("8Hard");
                    }
                    break;
                case 2:
                    tittleTxt.setText("Top 10 Classic players");
                    if (buttonMode == 1 || buttonMode == 2) {
                        offlineLoad("classic");
                        getRetrofitObject("classic");
                        getUserPosition("classic");
                    } else if (buttonMode == 3 || buttonMode == 4) {
                        buttonMode = 3;
                        offlineLoad("classicHard");
                        getRetrofitObject("classicHard");
                        getUserPosition("classicHard");
                    } else if (buttonMode == 5 || buttonMode == 6) {
                        offlineLoad("classic8");
                        getRetrofitObject("classic8");
                        getUserPosition("classic8");
                    } else if (buttonMode == 7 || buttonMode == 8) {
                        tittleTxt.setText("Top 10 players");
                        nextBtn.setVisibility(View.INVISIBLE);
                        previousBtn.setVisibility(View.INVISIBLE);
                        offlineLoad("8Hard");
                        getRetrofitObject("8Hard");
                        getUserPosition("8Hard");
                    }
                    break;
            }
        }
    };

    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!btnClicked) {
                mode = 2;
                btnClicked = true;
            } else {
                if (mode == 1)
                    mode = 2;
                else
                    mode--;
            }
            switch (mode) {
                case 1:
                    tittleTxt.setText("Top 10 Time Attack players");
                    if (buttonMode == 1 || buttonMode == 2) {
                        offlineLoad("timeAttack");
                        getRetrofitObject("timeAttack");
                        getUserPosition("timeAttack");
                    } else if (buttonMode == 3 || buttonMode == 4) {
                        buttonMode = 3;
                        offlineLoad("timeattackHard");
                        getRetrofitObject("timeattackHard");
                        getUserPosition("timeattackHard");
                    } else if (buttonMode == 5 || buttonMode == 6) {
                        offlineLoad("timeAttack8");
                        getRetrofitObject("timeAttack8");
                        getUserPosition("timeAttack8");
                    } else if (buttonMode == 7 || buttonMode == 8) {
                        tittleTxt.setText("Top 10 players");
                        nextBtn.setVisibility(View.INVISIBLE);
                        previousBtn.setVisibility(View.INVISIBLE);
                        offlineLoad("8Hard");
                        getRetrofitObject("8Hard");
                        getUserPosition("8Hard");
                    }
                    break;
                case 2:
                    tittleTxt.setText("Top 10 Classic players");
                    if (buttonMode == 1 || buttonMode == 2) {
                        offlineLoad("classic");
                        getRetrofitObject("classic");
                        getUserPosition("classic");
                    } else if (buttonMode == 3 || buttonMode == 4) {
                        buttonMode = 3;
                        offlineLoad("classicHard");
                        getRetrofitObject("classicHard");
                        getUserPosition("classicHard");
                    } else if (buttonMode == 5 || buttonMode == 6) {
                        offlineLoad("classic8");
                        getRetrofitObject("classic8");
                        getUserPosition("classic8");
                    } else if (buttonMode == 7 || buttonMode == 8) {
                        tittleTxt.setText("Top 10 players");
                        nextBtn.setVisibility(View.INVISIBLE);
                        previousBtn.setVisibility(View.INVISIBLE);
                        offlineLoad("8Hard");
                        getRetrofitObject("8Hard");
                        getUserPosition("8Hard");
                    }
                    break;
            }
        }
    };

    View.OnClickListener refreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(DomainName.getIstance())
                    .build();

            APIrefreshScore scalarService = retrofit.create(APIrefreshScore.class);

            SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);

            settings = getApplicationContext().getSharedPreferences("timeAttack", 0);
            int timeAttack = settings.getInt("timeAttack", 0);

            settings = getApplicationContext().getSharedPreferences("classic", 0);
            int classic = settings.getInt("classic", 0);

            settings = getApplicationContext().getSharedPreferences("timeattackHard", 0);
            int timeAttackHard = settings.getInt("timeattackHard", 0);

            settings = getApplicationContext().getSharedPreferences("classicHard", 0);
            int classicHard = settings.getInt("classicHard", 0);

            settings = getApplicationContext().getSharedPreferences("timeAttack8", 0);
            int timeAttack8 = settings.getInt("timeAttack8", 0);

            settings = getApplicationContext().getSharedPreferences("classic8", 0);
            int classic8 = settings.getInt("classic8", 0);

            settings = getApplicationContext().getSharedPreferences("8Hard", 0);
            int hard8 = settings.getInt("8Hard", 0);

            settings = getApplicationContext().getSharedPreferences("avatar", 0);
            String avatar = settings.getString("avatar", "");

            Call<String> stringCall = scalarService.setQuery(myID, avatar, timeAttack, classic, timeAttackHard, classicHard,
                    timeAttack8, classic8, hard8);

            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        tittleTxt.setText("Top 10 Time Attack players");
                        if (buttonMode == 1 || buttonMode == 2) {
                            offlineLoad("timeAttack");
                            getRetrofitObject("timeAttack");
                            getUserPosition("timeAttack");
                        } else if (buttonMode == 3 || buttonMode == 4) {
                            buttonMode = 3;
                            offlineLoad("timeattackHard");
                            getRetrofitObject("timeattackHard");
                            getUserPosition("timeattackHard");
                        } else if (buttonMode == 5 || buttonMode == 6) {
                            offlineLoad("timeAttack8");
                            getRetrofitObject("timeAttack8");
                            getUserPosition("timeAttack8");
                        } else if (buttonMode == 7 || buttonMode == 8) {
                            tittleTxt.setText("Top 10");
                            offlineLoad("8Hard");
                            getRetrofitObject("8Hard");
                            getUserPosition("8Hard");
                        }
                        mode = 1;
                    } else {
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    tittleTxt.setText("Top 10 Time Attack players");
                    if (buttonMode == 1 || buttonMode == 2) {
                        offlineLoad("timeAttack");
                        getRetrofitObject("timeAttack");
                        getUserPosition("timeAttack");
                    } else if (buttonMode == 3 || buttonMode == 4) {
                        buttonMode = 3;
                        offlineLoad("timeattackHard");
                        getRetrofitObject("timeattackHard");
                        getUserPosition("timeattackHard");
                    } else if (buttonMode == 5 || buttonMode == 6) {
                        offlineLoad("timeAttack8");
                        getRetrofitObject("timeAttack8");
                        getUserPosition("timeAttack8");
                    } else if (buttonMode == 7 || buttonMode == 8) {
                        tittleTxt.setText("Top 10");
                        offlineLoad("8Hard");
                        getRetrofitObject("8Hard");
                        getUserPosition("8Hard");
                    }
                    mode = 1;
                }
            });
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (getMusic(getApplicationContext()) == 1) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getMusic(getApplicationContext()) == 1) {
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
