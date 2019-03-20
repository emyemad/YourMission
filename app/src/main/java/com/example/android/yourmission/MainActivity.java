package com.example.android.yourmission;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    String  missionDate , missionTime;

    TextView mission_txt , mission_date , mission_time;
    Button start ;
    LinearLayout linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define the TextViews
        mission_txt = findViewById(R.id.mission_txt);
        mission_date = findViewById(R.id.mission_date);
        mission_time = findViewById(R.id.mission_time);
        // Define the Linear Layout which is Invisible
        // it will be visible when we press the (send) button in the (mission) Activity
        linear_layout = findViewById(R.id.linear_layout);
        // Define the Button
        start = findViewById(R.id.start);

        // Start the (mission) Activity when clicking this button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , mission.class);
                startActivity(intent);

                finish();

            }
        });

        //  get the key of intent from the mission Activity
       try{
           String sender = this.getIntent().getExtras().getString("sender_Key");
           //IF ITS THE misison activity THEN RECEIVE DATA
           if(sender != null)
           {
               Toast.makeText(this , "Data has been sent to Main Activity" , Toast.LENGTH_LONG).show();
               this.recieveData();
           }

       } catch (Exception e){
           System.out.println(e);
       }

       }

       // Receive Data from (mission) Activity
        private void recieveData(){
        Intent intent = getIntent();

        linear_layout.setVisibility(View.VISIBLE);

       // missionText = intent.getExtras().getString("mission_Text");
        mission_txt.setText(getIntent().getStringExtra("mission_Text"));

        missionDate = intent.getExtras().getString("date");
        mission_date.setText(missionDate);

        missionTime = intent.getExtras().getString("time");
        mission_time.setText(missionTime);

    }

}
