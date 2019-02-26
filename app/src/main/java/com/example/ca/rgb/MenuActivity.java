package com.example.ca.rgb;

import com.example.ca.rgb.Interfaces.APIogovor;
import com.example.ca.rgb.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ca.rgb.Interfaces.APIservisi;
import com.example.ca.rgb.RetrofitPoziv.RetrofitCall;
import com.example.ca.rgb.RetrofitPoziv.RetrofitOdgovor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class MenuActivity extends AppCompatActivity {
    protected APIogovor api =  RetrofitOdgovor.getApi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playBtn = findViewById(R.id.playBtn);
        Button scoreBtn = findViewById(R.id.scoreBtn);
        Button exitBtn = findViewById(R.id.exitBtn);


        playBtn.setOnClickListener(menuActionListener);
        scoreBtn.setOnClickListener(menuActionListener);
        exitBtn.setOnClickListener(menuActionListener);

    }


   View.OnClickListener menuActionListener = new View.OnClickListener() {

       @Override
       public void onClick(View v) {
           Button button = (Button)v;
           String action = String.valueOf(button.getTag());
           switch (action){
               case "playBtn":
                   startActivity(new Intent(MenuActivity.this, PlayActivity.class));
                   break;
               case "scoreBtn":
                   Call<String> call;
                   call = api.setQuery("",0);

                   call.enqueue(new Callback<String>()
                   {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response)
                       {
                           System.out.println("AAAAAAAAAAAAAAAAAAAAAAaa");
                           if(response.isSuccessful())
                           {
                               System.out.println("dadsadasdas");
                           }else{
                               System.out.println("NIJEdadsadasdas");
                           }
                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t)
                       {
                           System.out.println("NIJEdadsadasdas");

                       }
                   });
                   break;
               case "exitBtn":
                   finish();
                   break;
           }
       }
   };

    private void addNewScore(){
        Call<String> call;
        call = api.setQuery("Neko",15);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.isSuccessful())
                {
                    System.out.println("dadsadasdas");
                }else{
                    System.out.println("NIJEdadsadasdas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }

}
