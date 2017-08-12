package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

public class notification extends ThirdActivity {
    Button alarmoff;
    Context context;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        alarmoff = (Button) findViewById(R.id.alarmoff);
        this.context=this;
        alarm_manager=(AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmset=(TextView)findViewById(R.id.alarmset);
        final Calendar calendar=Calendar.getInstance();
//        final Button alarmon=(Button)findViewById(R.id.alarmon);

        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        alarmoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm off!");
                my_intent.putExtra("extra","alarm off");
                pending_intent= PendingIntent.getBroadcast(notification.this,0,
                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_manager.cancel(pending_intent);

                sendBroadcast(my_intent);
            }
        });

    }

           public void checkalarm(ArrayList<String> timearray){//失敗中

               for (int i=0;i<timearray.size();i++){
                   Log.d("timearray", timearray.get(i));
               }



               for (int i=0;i<timearray.size();i++){
                   Log.d("test00000",notification.this.toString());
                   Intent my_intent=new Intent(notification.this,Alarm_Receiver.class);
                   Log.d("test00000","1");

                   my_intent.putExtra("extra","alarm on");
                   Log.d("test00000","2");
                   Log.d("test00000","3");

                   pending_intent= PendingIntent.getBroadcast(notification.this,i,
                    my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                   Log.d("test00000","4");

                   alarm_manager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(timearray.get(i)),pending_intent);
                   Log.d("test00000","5");

                   Log.d("sss", String.valueOf( Long.parseLong(timearray.get(i))));
        }

    }
    private void set_alarm_text(String output) {
//        alarmset.setText(output);
    }

    public Context getContext(){
        return this.context;
    }

    public void goback(View v){
        finish();
    }

}
