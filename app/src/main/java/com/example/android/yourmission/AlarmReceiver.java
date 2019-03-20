package com.example.android.yourmission;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


       String mission = intent.getStringExtra("msg_Text");

       // create intent to RingtoneService
        Intent service_intent = new Intent(context , RingtoneService.class);


        service_intent.putExtra("missionText" , mission);

        // start our service
        context.startService(service_intent);
    }
}
