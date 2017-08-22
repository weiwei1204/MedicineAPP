package com.example.carrie.carrie_test1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by rita on 2017/7/13.
 */

public class Alarm_Receiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("nonono","1");

        Log.d("nonono",intent.toString());

        Log.e("We are in there","yap");

        String get_your_string=intent.getExtras().getString("extra");
        String alarmid=intent.getExtras().getString("alarmid");
        Log.d("nonono1", alarmid);

        Log.e("What is the key",get_your_string);

        Intent service_intent=new Intent(context,RingtonePlayingService.class);
        service_intent.putExtra("extra",get_your_string);
        service_intent.putExtra("alarmid",alarmid);
        context.startService(service_intent);
    }
}

//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.util.Log;
//import android.widget.Toast;
//
//import static android.content.Context.NOTIFICATION_SERVICE;
//
//
//public class Alarm_Receiver extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        String get_your_string=intent.getExtras().getString("extra");
//        Log.d("nonono",get_your_string);
//        if (get_your_string.equals("alarm off")){
//            ringtone.stop();
//            Log.d("nono","hahahah");
//        }
//        else {
//            Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
//
//            try {
//                Log.d("nonono","3");
//
//                NotificationManager notify_manager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//                Log.d("nonono","4");
//
//                Intent intent_alarm=new Intent(context,alarm.class);
//                TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
//                taskStackBuilder.addParentStack(alarm.class);
//                taskStackBuilder.addNextIntent(intent_alarm);
//                Log.d("nonono","5");
//
//                PendingIntent pending_intent_alarm=PendingIntent.getActivity(context,0,intent_alarm,PendingIntent.FLAG_UPDATE_CURRENT);
//
////                PendingIntent pending_intent_alarm=PendingIntent.getActivity(this,0,intent_alarm,PendingIntent.FLAG_UPDATE_CURRENT);
//                Log.d("nonono","6");
//
//                Notification notification_popup=new Notification.Builder(context)
//                        .setSmallIcon(R.drawable.add)
//                        .setContentTitle("an alarm is goin off!!")
//                        .setContentText("click me")
//                        .setContentIntent(pending_intent_alarm)
//                        .setAutoCancel(true)
//                        .build();
//                Log.d("nonono","7");
//
//                notify_manager.notify(0,notification_popup);
//
//            }catch (Exception e){
//                Log.d("nonono",e.toString());
//            }
//            ringtone.play();
//
//        }
//
//
//    }
//}