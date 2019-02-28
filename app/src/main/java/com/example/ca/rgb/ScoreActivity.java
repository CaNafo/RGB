package com.example.ca.rgb;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIgetPosition;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ScoreActivity extends AppCompatActivity {
    int mode = 1;
    boolean btnClicked = false;
    String responseString;
    TextView tittleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tittleTxt = findViewById(R.id.titleTxt);
        tittleTxt.setText("Top 10 Time Attack players");

        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int myScore = settings.getInt("ID", 0);

        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(nextListener);

        Button previousBtn = findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(backListener);

        offlineLoad();
        getRetrofitObject("score");// Get from the SharedPreferences
        getUserPosition("score");
    }

    void offlineLoad() {
        TextView textView = findViewById(R.id.myScoreTxt);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("score", 0);
        int myScore = settings.getInt("score", 0);

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

        textView = findViewById(R.id.nameTxt);
        textView.setText("Loading...");
        textView = findViewById(R.id.scoreTxt);
        textView.setText("Loading...");
        textView = findViewById(R.id.numberTxt);
        textView.setText("1.");

    }

    void getRetrofitObject(String mode) {
        final String jsonMode = mode;
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://rgb.dx.am/")
                .build();

        APIogovor scalarService = retrofit.create(APIogovor.class);
        Call<String> stringCall = scalarService.getStringResponse("/RGB/score_read.php",mode);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    responseString = response.body();
                    try {
                        JSONObject object = new JSONObject(responseString);
                        JSONArray Jarray = object.getJSONArray("data");
                        String name = "Player\n\n";
                        String number = "\n\n";
                        String score = "Score\n\n";
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject Jasonobject = Jarray.getJSONObject(i);
                            if (i != (Jarray.length()-1)) {
                                number += (i + 1) + ".\n";
                                name += Jasonobject.getString("name") + "\n";
                                score += Jasonobject.getString(jsonMode) + "\n";
                            } else {
                                number += (i + 1);
                                name += Jasonobject.getString("name");
                                score += Jasonobject.getString(jsonMode);
                            }

                        }

                        TextView textView = findViewById(R.id.nameTxt);
                        textView.setText(name);
                        textView = findViewById(R.id.scoreTxt);
                        textView.setText(score);
                        textView = findViewById(R.id.numberTxt);
                        textView.setText(number);

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

    void getUserPosition(String mode) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
        int myID = settings.getInt("ID", 0);

        int myScore = 0;
        switch (mode){
            case "score":
                settings = getApplicationContext().getSharedPreferences("score", 0);
                myScore = settings.getInt("score", 0);
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
        }

        settings = getApplicationContext().getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if (myID == 0) {
            setID();
            settings = getApplicationContext().getSharedPreferences("ID", 0);
            myID = settings.getInt("ID", 0);

            if (!(myName.length() > 0)) {
                settings = getApplicationContext().getSharedPreferences("name", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", "DefaultUser");
                // Apply the edits!
                editor.apply();
                addNewScore("DefaultUser",myScore);

            } else {
                addNewScore(myName, myScore);
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://rgb.dx.am/")
                .build();

        APIgetPosition scalarService = retrofit.create(APIgetPosition.class);
        APIgetPosition api = scalarService;
        Call<String> stringCall = scalarService.getStringResponse("/RGB/getUserPosition.php", myID,mode);

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
                            switch (modeJson){
                                case "score":
                                    settings = getApplicationContext().getSharedPreferences("score", 0);
                                    myScore = settings.getInt("score", 0);
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

    private void addNewScore(String name, int score) {
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(name, score);
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

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            offlineLoad();
            btnClicked = true;
            if(mode == 4)
                mode = 1;
            else
                mode++;
            switch (mode){
                case 1:
                    tittleTxt.setText("Top 10 Time Attack players");
                    getRetrofitObject("score");
                    getUserPosition("score");
                    break;
                case 2:
                    tittleTxt.setText("Top 10 Classic players");
                    getRetrofitObject("classic");
                    getUserPosition("classic");
                    break;
                case 3:
                    tittleTxt.setText("Top 10 Time Attack Hard players");
                    getRetrofitObject("timeattackHard");
                    getUserPosition("timeattackHard");
                    break;
                case 4:
                    tittleTxt.setText("Top 10 Classic Hard players");
                    getRetrofitObject("classicHard");
                    getUserPosition("classicHard");
                    mode = 0;
                    break;
            }
        }
    };

    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            offlineLoad();
            if(!btnClicked){
                mode = 4;
                btnClicked = true;
            }else {
                if(mode == 1)
                    mode = 4;
                else
                    mode--;
            }
            switch (mode){
                case 1:
                    tittleTxt.setText("Top 10 Time Attack players");
                    getRetrofitObject("score");
                    getUserPosition("score");
                    break;
                case 2:
                    tittleTxt.setText("Top 10 Classic players");
                    getRetrofitObject("classic");
                    getUserPosition("classic");
                    break;
                case 3:
                    tittleTxt.setText("Top 10 Time Attack Hard players");
                    getRetrofitObject("timeattackHard");
                    getUserPosition("timeattackHard");
                    break;
                case 4:
                    tittleTxt.setText("Top 10 Classic Hard players");
                    getRetrofitObject("classicHard");
                    getUserPosition("classicHard");
                    break;
            }
        }
    };
}
