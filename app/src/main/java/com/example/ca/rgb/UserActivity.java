package com.example.ca.rgb;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ca.rgb.Interfaces.APIgetID;
import com.example.ca.rgb.Interfaces.APIogovor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(onClickListener);
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
            String myID = settings.getString("ID", "");
            if (!(myID.length() > 0))
                setID();

            finish();
        }
    };

    void setID() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://192.168.1.5/")
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
}
