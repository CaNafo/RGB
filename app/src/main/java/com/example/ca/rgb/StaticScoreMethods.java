package com.example.ca.rgb;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.Interfaces.APIupdateServisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitUpdateCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class StaticScoreMethods {
    public static void finishUpdate(int mode, int score, Context context) {
        SharedPreferences settings;
        int myScore = 0;
        switch (mode) {
            case 1:
                //timeattack 3 buttona
                settings = context.getSharedPreferences("timeAttack", 0);
                myScore = settings.getInt("timeAttack", 0);
                break;
            case 2:
                //classic 3 buttona
                settings = context.getSharedPreferences("classic", 0);
                myScore = settings.getInt("classic", 0);
                break;
            case 3:
                //timeattack 5 buttona
                settings = context.getSharedPreferences("timeattackHard", 0);
                myScore = settings.getInt("timeattackHard", 0);
                break;
            case 4:
                //classic 5 buttona
                settings = context.getSharedPreferences("classicHard", 0);
                myScore = settings.getInt("classicHard", 0);
                break;
            case 5:
                settings = context.getSharedPreferences("timeAttack8", 0);
                myScore = settings.getInt("timeAttack8", 0);
                break;
            case 6:
                settings = context.getSharedPreferences("classic8", 0);
                myScore = settings.getInt("classic8", 0);
                break;
            case 7:
                settings = context.getSharedPreferences("8Hard", 0);
                myScore = settings.getInt("8Hard", 0);
                break;
        }
        // Get from the SharedPreferences


        settings = context.getSharedPreferences("name", 0);
        String myName = settings.getString("name", "");

        if (!(myName.length() > 0)) {
            settings = context.getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);
            if (myID == 0) {
                setID(context);
                settings = context.getSharedPreferences("ID", 0);
                myID = settings.getInt("ID", 0);


                settings = context.getSharedPreferences("avatar", 0);
                String avatar = settings.getString("avatar", "");


                addNewScore(myName,avatar);
            }
            setID(context);
            settings = context.getSharedPreferences("name", 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString("name", "DefaultUser");
            // Apply the edits!
            editor.apply();

            myName = "DefaultUser";
        }

        if (myScore < score) {
            settings = context.getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);
            updateScore(myID, score, mode);

            SharedPreferences.Editor editor;

            switch (mode) {
                case 1:
                    //timeattack
                    settings = context.getSharedPreferences("timeAttack", 0);
                    editor = settings.edit();
                    editor.putInt("timeAttack", score);
                    break;
                case 2:
                    //classic
                    settings = context.getSharedPreferences("classic", 0);
                    editor = settings.edit();
                    editor.putInt("classic", score);
                    break;
                case 3:
                    //timeattack hard
                    settings = context.getSharedPreferences("timeattackHard", 0);
                    editor = settings.edit();
                    editor.putInt("timeattackHard", score);
                    break;
                case 4:
                    //classic hard
                    settings = context.getSharedPreferences("classicHard", 0);
                    editor = settings.edit();
                    editor.putInt("classicHard", score);
                    break;
                case 5:
                    settings = context.getSharedPreferences("timeAttack8", 0);
                    editor = settings.edit();
                    editor.putInt("timeAttack8", score);
                    break;
                case 6:
                    settings = context.getSharedPreferences("classic8", 0);
                    editor = settings.edit();
                    editor.putInt("classic8", score);
                    break;
                case 7:
                    settings = context.getSharedPreferences("8Hard", 0);
                    editor = settings.edit();
                    editor.putInt("8Hard", score);
                    break;

                default:
                    settings = context.getSharedPreferences("timeAttack", 0);
                    editor = settings.edit();
                    editor.putInt("timeAttack", score);
                    break;
            }

            // Apply the edits!
            editor.apply();
        }
    }

    static void setID(final Context context) {
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

                            SharedPreferences settings = context.getSharedPreferences("ID", 0);
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

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private static void addNewScore(String name, String avatar) {
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

    private static void updateScore(int ID, int score, int mode) {
        APIupdateServisi api = RetrofitUpdateCall.getApi();
        Call<String> call;

        call = api.setQuery(ID, score, mode);
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
}
