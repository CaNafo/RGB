package com.example.ca.rgb;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIaddTopRank;
import com.example.ca.rgb.Interfaces.APIgetPosition;
import com.example.ca.rgb.Interfaces.APIodgovorTopRank;
import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIrefreshScore;
import com.example.ca.rgb.Interfaces.APIrefreshTop;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitAddTopRank;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.addNewTopRank;
import static com.example.ca.rgb.StaticMethods.getMusic;

public class TopTenScore extends AppCompatActivity {
    public static JSONArray tryJsonArray = null;
    static String tryResponseString;
    static JSONObject tryObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_score);
        Button refreshButton = findViewById(R.id.refreshBtn);
        refreshButton.setOnClickListener(refreshOnClickListener);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("firstTime", 0);
        int firstTime = settings.getInt("firstTime", 0);

        addNewTopRank(getApplicationContext());

        TextView textView = findViewById(R.id.myScoreTxt);

        settings = getApplicationContext().getSharedPreferences("Points", 0);
        int myScore = settings.getInt("Points", 0);

        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        textView.setText("Name:  " + myName + ",    Points:  " + myScore);

        updateRank();
        getUserPosition();
        if (tryJsonArray != null) {
            if (tryJsonArray.length() > 0)
                fillScore();
            else
                tryReadScore();
        } else {
            tryReadScore();
        }
        if (getMusic(getApplicationContext()) == 1) {
            mp.start();
        }
    }

    void updateRank() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();
        APIrefreshTop scalarService = retrofit.create(APIrefreshTop.class);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int ID = settings.getInt("ID", 0);

        settings = getApplicationContext().getSharedPreferences("Points", 0);
        int points = settings.getInt("Points", 0);

        Call<String> stringCall = scalarService.setQuery(ID, points);

        stringCall.enqueue(new Callback<String>() {
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

    void getUserPosition() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int myID = settings.getInt("ID", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();

        APIgetPosition scalarService = retrofit.create(APIgetPosition.class);
        APIgetPosition api = scalarService;
        Call<String> stringCall = scalarService.getStringResponse("/RGB/getUserPosition.php", myID, "points");


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

                            settings = getApplicationContext().getSharedPreferences("name", 0);
                            String myName = settings.getString("name", "");

                            settings = getApplicationContext().getSharedPreferences("Points", 0);
                            int myScore = settings.getInt("Points", 0);

                            if (!(myName.length() > 0)) {
                                textView.setText("Position:  " + Jasonobject.get("position") + " ,    Name:  " + myName + ",    Points:  " + myScore);

                                settings = getApplicationContext().getSharedPreferences("name", 0);
                                SharedPreferences.Editor editor = settings.edit();

                                editor.putString("name", "DefaultUser");
                                // Apply the edits!
                                editor.apply();
                            } else {
                                textView.setText("Position:  " + Jasonobject.get("position") + " ,    Name:  " + myName + ",    Points:  " + myScore);
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

    View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNewTopRank(getApplicationContext());
            tryReadScore();
        }
    };

    public void tryReadScore() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(DomainName.getIstance())
                .build();

        APIodgovorTopRank scalarService = retrofit.create(APIodgovorTopRank.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/read_top_rank.php");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    tryResponseString = response.body();
                    try {
                        tryObject = new JSONObject(tryResponseString);
                        tryJsonArray = tryObject.getJSONArray("data");
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

    void fillScore() {
        LinearLayout noumber = findViewById(R.id.numberLayout);
        noumber.removeAllViews();

        LinearLayout name = findViewById(R.id.nameLayout);
        name.removeAllViews();

        LinearLayout image = findViewById(R.id.imageLayout);
        image.removeAllViews();

        LinearLayout score = findViewById(R.id.scoreLayout);
        score.removeAllViews();

        LinearLayout rank = findViewById(R.id.rankLayout);
        rank.removeAllViews();

        TextView noumberTxt = new TextView(noumber.getContext());
        noumberTxt.setText("No.");
        noumberTxt.setTextColor(Color.parseColor("#FFFFFF"));
        noumberTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        noumberTxt.setTypeface(null, Typeface.BOLD);
        noumberTxt.setGravity(Gravity.CENTER);
        noumber.addView(noumberTxt);

        ImageView avatarImg;

        TextView nameTxt = new TextView(name.getContext());
        nameTxt.setText("Name");
        nameTxt.setTextColor(Color.parseColor("#FFFFFF"));
        nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        nameTxt.setTypeface(null, Typeface.BOLD);
        nameTxt.setGravity(Gravity.CENTER);
        name.addView(nameTxt);


        TextView scoreTxt = new TextView(score.getContext());
        scoreTxt.setText("Points");
        scoreTxt.setTextColor(Color.parseColor("#FFFFFF"));
        scoreTxt.setTypeface(null, Typeface.BOLD);
        scoreTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        scoreTxt.setGravity(Gravity.CENTER);
        score.addView(scoreTxt);

        TextView rankTxt = new TextView(rank.getContext());
        rankTxt.setText("Rank");
        rankTxt.setTextColor(Color.parseColor("#FFFFFF"));
        rankTxt.setTypeface(null, Typeface.BOLD);
        rankTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        rankTxt.setGravity(Gravity.CENTER);
        rank.addView(rankTxt);

        try {
                for (int i = 0; i < tryJsonArray.length(); i++) {
                    JSONObject Jasonobject = tryJsonArray.getJSONObject(i);

                    noumberTxt = new TextView(noumber.getContext());
                    noumberTxt.setText((i + 1) + ".");
                    noumberTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    noumberTxt.setGravity(Gravity.CENTER);
                    noumberTxt.setTypeface(null, Typeface.BOLD);
                    noumber.addView(noumberTxt);

                    avatarImg = new ImageView(image.getContext());
                    String avatar = Jasonobject.getString("avatar");

                    switch (avatar) {
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
                        case "avatar11":
                            avatarImg.setImageResource(R.drawable.avatar1b_small);
                            break;
                        case "avatar12":
                            avatarImg.setImageResource(R.drawable.avatar1c_small);
                            break;
                        case "avatar13":
                            avatarImg.setImageResource(R.drawable.avatar1d_small);
                            break;
                        case "avatar14":
                            avatarImg.setImageResource(R.drawable.avatar1e_small);
                            break;
                        case "avatar15":
                            avatarImg.setImageResource(R.drawable.avatar1f_small);
                            break;
                        case "avatar16":
                            avatarImg.setImageResource(R.drawable.avatar1g_small);
                            break;
                        case "avatar17":
                            avatarImg.setImageResource(R.drawable.avatar1h_small);
                            break;
                        case "avatar18":
                            avatarImg.setImageResource(R.drawable.avatar1i_small);
                            break;
                        case "avatar19":
                            avatarImg.setImageResource(R.drawable.avatar1j_small);
                            break;
                        case "avatar20":
                            avatarImg.setImageResource(R.drawable.avatar1k_small);
                            break;
                        case "avatar21":
                            avatarImg.setImageResource(R.drawable.avatar1l_small);
                            break;
                        case "avatar22":
                            avatarImg.setImageResource(R.drawable.avatar1m_small);
                            break;
                        case "avatar23":
                            avatarImg.setImageResource(R.drawable.avatar1n_small);
                            break;
                        case "avatar24":
                            avatarImg.setImageResource(R.drawable.avatar1o_small);
                            break;
                        case "avatar25":
                            avatarImg.setImageResource(R.drawable.avatar1p_small);
                            break;

                        default:
                            avatarImg.setImageResource(R.drawable.avatar1_small);
                            break;
                    }
                    avatarImg.setLayoutParams(new LinearLayout.LayoutParams(24, 24));

                    //image.addView(avatarImg);

                    nameTxt = new TextView(name.getContext());
                    nameTxt.setText((Jasonobject.getString("name")));
                    nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    nameTxt.setGravity(Gravity.CENTER);
                    nameTxt.setTypeface(null, Typeface.BOLD);

                    LinearLayout nameLinear = new LinearLayout(name.getContext());
                    nameLinear.setOrientation(LinearLayout.HORIZONTAL);
                    nameLinear.addView(avatarImg);
                    nameLinear.addView(nameTxt);

                    name.addView(nameLinear);

                    scoreTxt = new TextView(score.getContext());
                    scoreTxt.setText(Jasonobject.getString("points"));
                    scoreTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    scoreTxt.setGravity(Gravity.CENTER);
                    scoreTxt.setTypeface(null, Typeface.BOLD);
                    score.addView(scoreTxt);

                    int points = Integer.parseInt(Jasonobject.getString("points"));
                    int stars = 0;

                    if (points >= 10 && points < 100) {
                        stars = 1;
                    } else if (points >= 100 && points < 400) {
                        stars = 2;
                    } else if (points >= 400 && points < 700) {
                        stars = 3;
                    } else if (points >= 700 && points < 1000) {
                        stars = 4;
                    } else if (points >= 1000 && points < 3000) {
                        stars = 5;
                    } else if (points >= 3000 && points < 5000) {
                        stars = 6;
                    } else if (points >= 5000) {
                        stars = 7;
                    }

                    String ret = "";

                    switch (stars) {
                        case 1:
                            ret = "Newbee";
                            break;
                        case 2:
                            ret = "Casual";
                            break;
                        case 3:
                            ret = "Regular";
                            break;
                        case 4:
                            ret = "Experienced";
                            break;
                        case 5:
                            ret = "Talented";
                            break;
                        case 6:
                            ret = "Professional";
                            break;
                        case 7:
                            ret = "Insane";
                            break;
                        default:
                            ret = "Beginner";
                            break;
                    }
                    rankTxt = new TextView(rank.getContext());
                    rankTxt.setText(ret);
                    rankTxt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    rankTxt.setGravity(Gravity.CENTER);
                    rankTxt.setTypeface(null, Typeface.BOLD);
                    rank.addView(rankTxt);
                }
            } catch (JSONException e) {
                e.printStackTrace();
        }
    }

}
