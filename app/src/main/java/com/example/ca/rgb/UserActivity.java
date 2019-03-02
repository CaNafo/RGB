package com.example.ca.rgb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.Interfaces.APIupdateName;
import com.example.ca.rgb.Interfaces.APIupdateServisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitUpdateCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitUpdateName;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        if(MenuActivity.music == 1){
            MenuActivity.mp.start();
        }
        SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
        String name = settings.getString("name", "");

        Button okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(onClickListener);

        TextView nameTxt = findViewById(R.id.nameTxt);
        nameTxt.setText(name);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-2037874631253623~2347238577");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
            SharedPreferences.Editor editor = settings.edit();

            TextView textView = findViewById(R.id.nameTxt);
            editor.putString("name", textView.getText().toString());
            // Apply the edits!
            editor.apply();

            settings = getApplicationContext().getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);

            if (myID == 0) {
                setID();
            }else{
                updateName(myID,textView.getText().toString());
            }

            finish();
        }
    };

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

                            ID = ID + 1;
                            System.out.println(ID+ "AJDIIII");

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putInt("ID", ID);
                            // Apply the edits!
                            editor.apply();
                            TextView textView = findViewById(R.id.nameTxt);
                            addNewScore(textView.getText().toString(),0);
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

    private void updateName(int ID, String name) {
        APIupdateName api = RetrofitUpdateName.getApi();
        Call<String> call;

        call = api.setQuery(ID, name);
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

    @Override
    public void onBackPressed() {
        finish();
        //startActivity(new Intent(HelpActivity.this, MenuActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MenuActivity.music == 1){
            MenuActivity.mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MenuActivity.music == 1){
            MenuActivity.mp.start();
        }
    }
}
