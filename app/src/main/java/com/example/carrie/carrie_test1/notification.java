//package com.example.carrie.carrie_test1;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import static android.content.Context.ALARM_SERVICE;
//import static com.example.carrie.carrie_test1.R.id.alarmset;
//
//public class notification extends ThirdActivity {
//    Button alarmoff;
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification);
//
//        alarmoff = (Button) findViewById(R.id.alarmoff);
//
//        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
//        alarmoff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                set_alarm_text("Alarm off!");
//                my_intent.putExtra("extra","alarm off");
//                pending_intent= PendingIntent.getBroadcast(notification.this,0,
//                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
//                alarm_manager.cancel(pending_intent);
//
//
//                sendBroadcast(my_intent);
//            }
//        });
//
//    }
//    private void set_alarm_text(String output) {
//        alarmset.setText(output);
//    }
//
//}
