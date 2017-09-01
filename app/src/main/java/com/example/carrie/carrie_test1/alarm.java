package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class alarm extends AppCompatActivity {
    AlarmManager alarm_manager;
//    TimePicker alarm_timepicker;
    TextClock textClock;
    TextView alarmset , alarmdrug;
    Context context;
    RequestQueue requestQueue;

    private String mcalnameUrl = "http://54.65.194.253/Medicine_Calendar/mcalname.php";
    private String sideEffect = "http://54.65.194.253/Medicine_Calendar/sideEffect.php";
    PendingIntent pending_intent;
    int alarmid;
    String mcalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.context=this;
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmset = (TextView)findViewById(R.id.alarmset);
        textClock = (TextClock)findViewById(R.id.textClock);
        alarmdrug = (TextView)findViewById(R.id.alarmdrug);
        final Calendar calendar=Calendar.getInstance();
//        final Button alarmon=(Button)findViewById(R.id.alarmon);
        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        Bundle bundle = getIntent().getExtras();
        alarmid= Integer.parseInt(bundle.getString("alarmid"));
        mcalid = bundle.getString("mcalid");
        drugeffect();
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        final StringRequest request = new StringRequest(Request.Method.POST, mcalnameUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmmmm",response);
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d("nnnnmnmn", String.valueOf(obj.length()));
                    Log.d("nnnnmnmn", String.valueOf(obj));
                    String id = obj.getString("id");
                    String name = obj.getString("name");
//                        String startDate = mcalendar.getString("startDate");
//                        String day = mcalendar.getString("day");
//                        String beacon_id =mcalendar.getString("beacon_id");
                    Log.d("nnnmnmnmn",id);
                    alarmset.setText(name+"  時間到了!!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",mcalid);
                Log.d("nnnmmmm",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




//        alarmon.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
//                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());
//                int hour=alarm_timepicker.getHour();
//                int minute=alarm_timepicker.getMinute();
//
//                String hour_string=String.valueOf(hour);
//                String minute_string=String.valueOf(minute);
//
//                if (hour>12){
//                    hour_string=String.valueOf(hour-12);
//                }
//                if (minute<10){
//                    minute_string="0"+String.valueOf(minute);
//                }
//
//                set_alarm_text("Alarm set to:"+hour_string+":"+minute_string);
//                my_intent.putExtra("extra","alarm on");
//                pending_intent= PendingIntent.getBroadcast(alarm.this,0,
//                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
//                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);
//                Log.d("sss", String.valueOf(calendar.getTimeInMillis()));
//
//            }
//        });
        Button alarmoff=(Button)findViewById(R.id.alarmoff);
        alarmoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm off!");
                my_intent.putExtra("extra","alarm off");
                pending_intent= PendingIntent.getBroadcast(alarm.this,alarmid,
                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_manager.cancel(pending_intent);
                sendBroadcast(my_intent);

            }
        });

    }
    public void drugeffect(){
        //取藥的副作用
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, sideEffect, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nn11",response);
                String effectstring;
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] effect=new String[jarray.length()];
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        Log.d("nn11", String.valueOf(obj.length()));
                        Log.d("nn11", String.valueOf(obj));
                        String drugeffect = obj.getString("sideEffect");
                        effect[i]=drugeffect;
                        Log.d("nn11",drugeffect);
                    }
                    alarmdrug.setText(Arrays.toString(effect).replaceAll("\\[|\\]", ""));



                } catch (JSONException e) {
                    Log.d("nn11",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nn11", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",mcalid);
                Log.d("nn11",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }
    private void set_alarm_text(String output) {
        alarmset.setText(output);
    }
    public void goback(View v){
        finish();
    }

}

//
//        import android.app.AlarmManager;
//        import android.app.PendingIntent;
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.support.v7.app.AppCompatActivity;
//        import android.view.View;
//        import android.widget.TimePicker;
//        import android.widget.Toast;
//        import android.widget.ToggleButton;
//
//        import java.util.Calendar;
//
//public class alarm extends AppCompatActivity
//{
//    TimePicker alarmTimePicker;
//    PendingIntent pendingIntent;
//    AlarmManager alarmManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm);
//        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//    }
//    public void OnToggleClicked(View view)
//    {
//        long time;
//        if (((ToggleButton) view).isChecked())
//        {
//            Toast.makeText(alarm.this, "ALARM ON", Toast.LENGTH_SHORT).show();
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//            Intent intent = new Intent(this, Alarm_Receiver.class);
//            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
//            if(System.currentTimeMillis()>time)
//            {
//                if (calendar.AM_PM == 0)
//                    time = time + (1000*60*60*12);
//                else
//                    time = time + (1000*60*60*24);
//            }
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
//        }
//        else
//        {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(alarm.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void goback(View v){
//        finish();
//    }

//}

