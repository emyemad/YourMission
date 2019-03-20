package com.example.android.yourmission;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class mission extends AppCompatActivity {

    public static Context context;


    int year , month , day , hour , minute , intDate , intTime;
    String    allTime , allDate;
    String ampm = "am";
    EditText edit_text ;
    TextView time , date , forMission;
    DatePicker datePicker;
    TimePicker timePicker;
    Button send ,setAlarm , cancelAlarm ;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        context = getApplicationContext();

        // definition of edit text
        edit_text = findViewById(R.id.edit_text);

        // definition of Text Views
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        forMission = findViewById(R.id.forMission);

        // definitions of pickers
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        // definitions of buttons
        setAlarm = findViewById(R.id.setAlarm);
        cancelAlarm = findViewById(R.id.cancelAlarm);
        send = findViewById(R.id.send);

        Calendar calendar1 = Calendar.getInstance();

        // Display the current date & time
        datePicker.init(calendar1.get(Calendar.YEAR) , calendar1.get(Calendar.MONTH) ,
                calendar1.get(Calendar.DAY_OF_MONTH) , null);

        timePicker.setCurrentHour(calendar1.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar1.get(Calendar.MINUTE));

        // set Alarm when clicking ( setAlarm ) button
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar current = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();

                // Make the user choose date & time from pickers
                // these methods(getYear, getMonth, getDayOfMonth) returns integer values
                calendar2.set(datePicker.getYear() , datePicker.getMonth() , datePicker.getDayOfMonth() ,
                        timePicker.getCurrentHour() , timePicker.getCurrentMinute() , 00);

                // to make it in 12 hour not 24 hour
                timePicker.setIs24HourView(false);

                // Save chosen time & date in integer values

                year = datePicker.getYear();
                 month = datePicker.getMonth() +1;  // we add ( +1 ) as Months begin with zero!!
                 day = datePicker.getDayOfMonth();

                 hour = timePicker.getCurrentHour();
                 minute = timePicker.getCurrentMinute();


                 intDate = year + month + day;
                 intTime = hour + minute;

                // convert chosen time & date from integer values to String
                String syear = String.valueOf(year);
                String smonth = String.valueOf(month);
                String sday = String.valueOf(day);
                String shour = String.valueOf(hour);
                String sminute = String.valueOf(minute);

                if(hour == 12){
                    allTime = "12:" + sminute +" " + ampm;
                   // allTime = "12:" + sminute +" " ;

                }
                if(hour > 12){
                    shour = String.valueOf(hour - 12);
                   ampm = "pm";
                }
                if(minute < 10){
                    sminute = "0" + String.valueOf(minute);
                }

                allDate = syear + "-" + smonth + "-" + sday;
                date.setText(allDate);
                allTime = shour + ":" + sminute + " " + ampm;
               // allTime = shour + ":" + sminute + " " ;
                time.setText(allTime);

                forMission.setText(edit_text.getText().toString());

                //If the set Date/Time already passed
                if(calendar2.compareTo(current)<=0){

                    Toast.makeText(getApplicationContext(), "Invalid Date/Time", Toast.LENGTH_LONG).show();
                }else{

                    setAlarm(calendar2);
                }
                }

        });

        // cancel Alarm when clicking ( cancelAlarm ) button
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cancel the alarm
                alarmManager.cancel(pendingIntent);
                // stop the ringtone by sending signal to (AlarmReceiver) class which will send a signal also
                // to the (RingtoneService) class
              //  sendBroadcast(intent);


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // send data to MainActivity when clicking ( send ) button
                Intent main_intent = new Intent(mission.this , MainActivity.class);
                intent.putExtra("sender_Key" , "mission");
                intent.putExtra("mission_Text" , edit_text.getText().toString());
                intent.putExtra("date" , allDate);
                intent.putExtra("time" , allTime);
                startActivity(main_intent);

                // close this activity
                finish();
            }
        });


    }

    // setting the Alarm with this method
      private void setAlarm(Calendar targetCal){
             intent = new Intent(getBaseContext(), AlarmReceiver.class);
             intent.putExtra("sender_Key2" , "mission");
             intent.putExtra("msg_Text" , edit_text.getText().toString());
             pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);

             alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

}
