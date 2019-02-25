package com.example.ca.rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

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
                   List<Map<String,String>> MyData = null;
                   GetData mydata =new GetData();
                   MyData= mydata.doInBackground();
                   String[] fromwhere = { "ID","name","score" };

                   //int[] viewswhere = {R.id.lblID , R.id.lblcountryname,R.id.lblCapitalCity};

                  /* ADAhere = new SimpleAdapter(MenuActivity.this, MyData,R.layout.listtemplate, fromwhere, viewswhere);

                   LV_Country.setAdapter(ADAhere);*/
                   break;
               case "exitBtn":
                   finish();
                   break;
           }
       }
   };

   /* LV_Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String,Object> obj=(HashMap<String,Object>)ADAhere.getItem(position);
            String ID=(String)obj.get("A");
            Toast.makeText(MainActivity.this, ID, Toast.LENGTH_SHORT).show();

        }
    });*/

}
