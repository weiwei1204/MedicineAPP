package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * Created by rita on 2017/7/13.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;
    String alarmid,mcalid,memberid,alarmtype;
    private FragmentManager manager;


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
        alarmtype = intent.getExtras().getString("alarmtype");
        if (alarmtype.equals("health")){
            alarmid = intent.getExtras().getString("alarmid");
            memberid = intent.getExtras().getString("memberid");

        }
        else {
            alarmid = intent.getExtras().getString("alarmid");
            mcalid = intent.getExtras().getString("mcalid");
            memberid = intent.getExtras().getString("memberid");
        }


        Log.d("nonono2", String.valueOf(alarmid));


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
//            media_song.setLooping(true);
            media_song.start();

            this.isRunning=false;
            this.startId=0;
            try {

                NotificationManager notify_manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

                if (alarmtype.equals("healthbs")||alarmtype.equals("healthbp")) {
                    Intent intent_alarm=new Intent(this.getApplicationContext(),notification.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("alarmid", String.valueOf(alarmid));
                    bundle.putString("mcalid",mcalid);
                    bundle.putString("memberid",memberid);
                    bundle.putString("alarmtype",alarmtype);
                    intent_alarm.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                    PendingIntent pending_intent_alarm=PendingIntent.getActivity(this, Integer.parseInt(alarmid),intent_alarm,0);
                    Log.d("nonono33", String.valueOf(memberid));
//                    Notification notification_popup=new Notification.Builder(this)
//                            .setSmallIcon(R.drawable.add)
//                            .setContentTitle("測量時間到!!")
//                            .setContentText("click me")
//                            .setContentIntent(pending_intent_alarm)
//                            .setAutoCancel(true)
//                            .build();
//                    notify_manager.notify(Integer.parseInt(alarmid),notification_popup);
                }else {
                    Intent intent_alarm=new Intent(this.getApplicationContext(),alarm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("alarmid", String.valueOf(alarmid));
                    bundle.putString("mcalid",mcalid);
                    bundle.putString("memberid",memberid);
                    bundle.putString("alarmtype",alarmtype);
                    intent_alarm.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                    PendingIntent pending_intent_alarm=PendingIntent.getActivity(this, Integer.parseInt(alarmid),intent_alarm,0);
                    Log.d("nonono3", String.valueOf(memberid));

                    Notification notification_popup=new Notification.Builder(this)
                            .setSmallIcon(R.drawable.add)
                            .setContentTitle("an alarm is goin off!!")
                            .setContentText("click me")
                            .setContentIntent(pending_intent_alarm)
                            .setAutoCancel(true)
                            .build();

                    notify_manager.notify(Integer.parseInt(alarmid),notification_popup);
                }


            }catch (Exception e){
                Log.d("nonono",e.toString());
            }

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
            this.startId=1;

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

    public void notifytype(){

    }


}
