package com.example.ca.rgb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getMusic;

public class UserActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        ImageView avatar1 = findViewById(R.id.avatar1);
        avatar1.setOnClickListener(imageOnClickListener);
        ImageView avatar2 = findViewById(R.id.avatar2);
        avatar2.setOnClickListener(imageOnClickListener);
        ImageView avatar3 = findViewById(R.id.avatar3);
        avatar3.setOnClickListener(imageOnClickListener);
        ImageView avatar4 = findViewById(R.id.avatar4);
        avatar4.setOnClickListener(imageOnClickListener);
        ImageView avatar5 = findViewById(R.id.avatar5);
        avatar5.setOnClickListener(imageOnClickListener);
        ImageView avatar6 = findViewById(R.id.avatar6);
        avatar6.setOnClickListener(imageOnClickListener);
        ImageView avatar7 = findViewById(R.id.avatar7);
        avatar7.setOnClickListener(imageOnClickListener);


        if(getMusic(getApplicationContext()) == 1){
            mp.start();
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
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
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

                            ID = ID + 1;

                            SharedPreferences settings = getApplicationContext().getSharedPreferences("ID", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putInt("ID", ID);
                            // Apply the edits!
                            editor.apply();
                            TextView textView = findViewById(R.id.nameTxt);
                            addNewScore(textView.getText().toString());
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

    private void addNewScore(String name) {
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(name);
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

    private void updateName(int ID, String name) {
        APIupdateName api = RetrofitUpdateName.getApi();
        Call<String> call;

        call = api.setQuery(ID, name);
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

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getMusic(getApplicationContext()) == 1){
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
    }

    View.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                ImageView imageView = (ImageView)v;
                SharedPreferences settings;
                 SharedPreferences.Editor editor;

                switch (imageView.getTag().toString()){
                    case "avatar1":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar1");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar2":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar2");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar3":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar3");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar4":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar4");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar5":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar5");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar6":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar6");
                        // Apply the edits!
                        editor.apply();
                        break;
                    case "avatar7":
                        settings = getApplicationContext().getSharedPreferences("avatar", 0);
                        editor = settings.edit();
                        editor.putString("avatar", "avatar7");
                        // Apply the edits!
                        editor.apply();
                        break;
                }
        }
    };
}
