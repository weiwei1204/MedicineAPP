//package com.example.carrie.carrie_test1;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
///**
// * Created by rita on 2017/8/1.
// */
//
//public class alarmset extends AppCompatActivity {
//    AlarmManager alarm_manager;
//    TimePicker alarm_timepicker;
//    TextView alarmset;
//    Context context;
//    PendingIntent pending_intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm);
//
//        this.context=this;
//        alarm_manager=(AlarmManager)getSystemService(ALARM_SERVICE);
//        alarm_timepicker=(TimePicker)findViewById(R.id.timePicker);
//        alarmset=(TextView)findViewById(R.id.alarmset);
//        final Calendar calendar=Calendar.getInstance();
////        final Button alarmon=(Button)findViewById(R.id.alarmon);
//        context=this.context;
//
////        Button alarmoff=(Button)findViewById(R.id.alarmoff);
////        alarmoff.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                set_alarm_text("Alarm off!");
////                my_intent.putExtra("extra","alarm off");
////                pending_intent= PendingIntent.getBroadcast(alarm.this,0,
////                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
////                alarm_manager.cancel(pending_intent);
////
////
////                sendBroadcast(my_intent);
////            }
////        });
//
//    }
//
//    public void checkalarm(ArrayList<String> timearray){
//        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
//        my_intent.putExtra("extra","alarm on");
//        for (int i=0;i<timearray.size();i++){
//            pending_intent= PendingIntent.getBroadcast(alarmset.this,i,
//                    my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
//            alarm_manager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(timearray.get(i)),pending_intent);
//            Log.d("sss", String.valueOf( Long.parseLong(timearray.get(i))));
//        }
//
//    }
//
//    private void set_alarm_text(String output) {
//        alarmset.setText(output);
//    }
//    public void goback(View v){
//        finish();
//    }
//
//}
