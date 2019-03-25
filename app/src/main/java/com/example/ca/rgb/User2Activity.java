package com.example.ca.rgb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.Interfaces.APIupdateName;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitUpdateName;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.ca.rgb.MenuActivity.mp;
import static com.example.ca.rgb.StaticMethods.getAvatar;
import static com.example.ca.rgb.StaticMethods.getMusic;

public class User2Activity extends AppCompatActivity {
    private AdView mAdView;
    String tempAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        tempAvatar = getAvatar(getApplicationContext());

        if(getMusic(getApplicationContext()) == 1){
            mp.start();
        }
        SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
        String name = settings.getString("name", "");

        findViewById(R.id.okBtn).setOnClickListener(onClickListener);
        findViewById(R.id.avatarBtn).setOnClickListener(avatarClickListener);

        TextView nameTxt = findViewById(R.id.scoreTxtt);
        nameTxt.setText(name);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        String defaultInputText = getResources().getString(R.string.ad_id_banner);
        MobileAds.initialize(this, defaultInputText);
    }

    View.OnClickListener avatarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            startActivity(new Intent(User2Activity.this, AvatarActivity.class));
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("name", 0);
            SharedPreferences.Editor editor = settings.edit();

            TextView textView = findViewById(R.id.scoreTxtt);
            if(textView.getText().toString().length()>0){
                editor.putString("name", textView.getText().toString());
                // Apply the edits!
                editor.apply();
            }else{
                editor.putString("name", "DefaultUser");
                // Apply the edits!
                editor.apply();
            }

            settings = getApplicationContext().getSharedPreferences("ID", 0);
            int myID = settings.getInt("ID", 0);

            settings = getApplicationContext().getSharedPreferences("avatar", 0);
            editor = settings.edit();
            editor.putString("avatar", tempAvatar);
            // Apply the edits!
            editor.apply();

            if (myID == 0) {
                setID();
            }else{

                updateName(myID,textView.getText().toString(), tempAvatar);
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
                            TextView textView = findViewById(R.id.scoreTxtt);

                            settings = getApplicationContext().getSharedPreferences("avatar", 0);
                            String avatar = settings.getString("avatar", "");

                            addNewScore(textView.getText().toString(),avatar, ID);
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

    private void addNewScore(String name, String avatar, int id) {
        APIservisi api = RetrofitCall.getApi();
        Call<String> call;
        call = api.setQuery(id,name,avatar);
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

    private void updateName(int ID, String name, String avatar) {
        APIupdateName api = RetrofitUpdateName.getApi();
        Call<String> call;

        call = api.setQuery(ID, name, avatar);
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
}
