package com.example.android.yourmission;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class RingtoneService extends Service {


    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent , int flags , int startId){


        // Start the Media Player
        mediaPlayer = MediaPlayer.create(this , R.raw.alarm);
        mediaPlayer.start();


        // Make an Alarm with the Your Mission
         AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Cancel Alarm")
                .setMessage(intent.getStringExtra("missionText"))
                .setNegativeButton("Cancel Alarm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();


                    }
                })
               // setCanceble(false) to avoid the dialog from disappearing when we touch the screen
                 .setCancelable(false)
                .create();

         alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
         alertDialog.show();

        // The integer return value is a value that describes how the system should continue the service
        // in the event that the system kills it
        // that return statement means if somehow our service stopped it will automatically restarted
        return START_NOT_STICKY;
    }
}
