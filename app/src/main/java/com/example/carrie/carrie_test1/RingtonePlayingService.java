package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by rita on 2017/7/13.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone state is ",state);


        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }
        if (!this.isRunning && startId ==1){
            Log.e("there is no music","and you want start");
            media_song=MediaPlayer.create(this,R.raw.water);//raw裡的音樂
            media_song.start();

            this.isRunning=true;
            this.startId=0;

            NotificationManager notify_manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Intent intent_alarm=new Intent(this.getApplicationContext(),ThirdActivity.class);
            PendingIntent pending_intent_alarm=PendingIntent.getActivity(this,0,intent_alarm,0);
            Notification notification_popup=new Notification.Builder(this)
                    .setSmallIcon(R.drawable.add)
                    .setContentTitle("an alarm is goin off!!")
                    .setContentText("click me")
                    .setContentIntent(pending_intent_alarm)
                    .setAutoCancel(true)
                    .build();

            notify_manager.notify(0,notification_popup);
        }
        else if (this.isRunning && startId ==0){
            Log.e("there is music","and you want end");

            media_song.stop();
            media_song.reset();

            this.isRunning=false;
            this.startId=0;

        }
        else if (!this.isRunning && startId ==0){
            Log.e("there is no music","and you want end");

            this.isRunning=false;
            this.startId=0;

        }
        else if (this.isRunning && startId ==1){
            Log.e("there is music","and you want start");

            this.isRunning=true;
            this.startId=0;

        }
        else {

            Log.e("else","somehow you reached this");

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
       Log.e("on destroy call","ta da");

        super.onDestroy();
        this.isRunning=false;
    }



}
