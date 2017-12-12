package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class alarm extends AppCompatActivity {
    AlarmManager alarm_manager;
//    TimePicker alarm_timepicker;
    TextClock textClock;
    TextView alarmset , alarmdrug,mname;
    Context context;
    RequestQueue requestQueue;

    private String mcalnameUrl = "http://54.65.194.253/Medicine_Calendar/mcalname.php";
    private String sideEffect = "http://54.65.194.253/Medicine_Calendar/sideEffect.php";
    String getm_recordtimeUrl = "http://54.65.194.253/Medicine_Calendar/getm_recordtime.php";
    String finishm_recordeUrl = "    http://54.65.194.253/Medicine_Calendar/finishm_record.php";
    PendingIntent pending_intent;
    int alarmid;
    private String mcalid,memberid,alarmtype;
    Button delaybtn,alarmoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmset = (TextView) findViewById(R.id.alarmset);
        textClock = (TextClock) findViewById(R.id.textClock);
        alarmdrug = (TextView) findViewById(R.id.alarmdrug);
        alarmoff = (Button) findViewById(R.id.alarmoff);
        delaybtn = (Button) findViewById(R.id.delaybtn);
        mname = (TextView)findViewById(R.id.mname);
        final Calendar calendar = Calendar.getInstance();
//        final Button alarmon=(Button)findViewById(R.id.alarmon);
        Bundle bundle = getIntent().getExtras();
        alarmid = Integer.parseInt(bundle.getString("alarmid"));
        mcalid = bundle.getString("mcalid");
        memberid = bundle.getString("memberid");
        alarmtype = bundle.getString("alarmtype");

        mcalname();
        drugeffect();

        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);
        alarmoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm(my_intent);
                Log.d("timeeeee", "okkkk");
                Alarmstatus(1, 0, 0, 1);
            }
        });
        delaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timeeeee", "delayyyy");
                Alarmdelay(my_intent);
            }
        });



    }


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
        //關鬧鐘
//        alarmoff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                set_alarm_text("Alarm off!");
//                my_intent.putExtra("extra","alarm off");
//                pending_intent= PendingIntent.getBroadcast(alarm.this,alarmid,
//                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
//                alarm_manager.cancel(pending_intent);
//                sendBroadcast(my_intent);
//            }
//        });

//    }

//    @Override
//    public void onClick(View v) {   //完成和延遲的監聽
//        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
//        switch (v.getId()) {
//            case R.id.alarmoff: //關鬧鐘
//                cancelAlarm(my_intent);
//                Log.d("timeeeee","okkkk");
//                Alarmstatus(1,0,0);
//                break;
//            case R.id.delaybtn:
//                Log.d("timeeeee","delayyyy");
//                Alarmdelay(my_intent);
//                break;
//        }
//
//    }
    public void mcalname(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, mcalnameUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmmmm1", response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] dbmcalname=new String[jarray.length()];
                    for (int i=0;i<jarray.length();i++) {
                        JSONObject obj = jarray.getJSONObject(i);
                        Log.d("nnmmm1", String.valueOf(obj.length()));
                        Log.d("nnmmm1", String.valueOf(obj));
                        String dbmname = obj.getString("name");
                        dbmcalname[i]=dbmname;
                        Log.d("nnnmnmnmn1",dbmname);
//                    set_alarm_text(dbmname);
//                    alarmset.setText(name + "  時間到了!!");
//                        mname.setText(dbmname);
                        mname.setText(dbmcalname[i]);
                    }
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
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid", mcalid);
                Log.d("nnnmmmm", parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void cancelAlarm(Intent my_intent){
        set_alarm_text("Alarm off!");
        my_intent.putExtra("extra","alarm off");
        my_intent.putExtra("alarmid",String.valueOf(alarmid));
        my_intent.putExtra("mcalid",mcalid);
        my_intent.putExtra("memberid",memberid);
        my_intent.putExtra("alarmtype",alarmtype);
        pending_intent= PendingIntent.getBroadcast(alarm.this,alarmid,
                my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarm_manager.cancel(pending_intent);
        sendBroadcast(my_intent);
        Intent it = new Intent(alarm.this,m_calendarlist.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    //改變鬧鐘的狀態 //是否完成1:完成,0:未完成 //這次提醒是否錯過服藥時段+1 //延遲次數(3)--1 //已吃完幾次藥
    public void Alarmstatus(final int finish, final int delay, final int delaycount, final int time_count){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, finishm_recordeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("timeeee", error.toString());
                Toast.makeText(getApplicationContext(), "Error read finishm_recorde.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("finish", String.valueOf(finish));
                parameters.put("delay", String.valueOf(delay));
                parameters.put("delaycount", String.valueOf(delaycount));
                parameters.put("time_count",String.valueOf(time_count));
                parameters.put("m_calid",mcalid);
                parameters.put("id", String.valueOf(alarmid));
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);


    }


    public void Alarmdelay(final Intent my_intent){      //延遲時間
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_recordtimeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String id = obj.getString("id");
                        String date = obj.getString("date");
                        String time = obj.getString("time");
                        String delaycount = obj.getString("delaycount");
                        String alarmtime=date+" "+time;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date todate = sdf.parse(alarmtime);
                        Long ldate = todate.getTime();
                        Log.d("timeeeee", String.valueOf(ldate));
                        if (alarmid == Integer.valueOf(id) && Integer.valueOf(delaycount) > 0){    //找到現在儀醒的時間id
                            my_intent.putExtra("extra","alarm off");
                            my_intent.putExtra("alarmid",id);
                            my_intent.putExtra("mcalid",mcalid);
                            my_intent.putExtra("alarmtype",alarmtype);
                            my_intent.putExtra("memberid",memberid);
                            set_alarm_text("Alarm off!");
                            sendBroadcast(my_intent);

                            my_intent.putExtra("extra","alarm on");
                            my_intent.putExtra("alarmid",id);
                            my_intent.putExtra("mcalid",mcalid);
                            my_intent.putExtra("alarmtype",alarmtype);
                            my_intent.putExtra("memberid",memberid);
                            pending_intent= PendingIntent.getBroadcast(alarm.this,alarmid,
                                    my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            alarm_manager.setExact(AlarmManager.RTC_WAKEUP,ldate+(4-Integer.valueOf(delaycount))*2*60*1000, pending_intent);
//                            sendBroadcast(my_intent);
                            Alarmstatus(0,0,1,0);
                            Intent it = new Intent(alarm.this,m_calendarlist.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("memberid", memberid);
                            it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
                            startActivity(it);
                        }
                        else if(Integer.valueOf(delaycount) == 0){
                            cancelAlarm(my_intent);
                            Alarmstatus(0,1,1,0);
                        }

                    }
                } catch (JSONException e) {
                    Log.d("timeeee",e.toString());
                    e.printStackTrace();
                } catch (ParseException e) {
                    Log.d("timeeee",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("timeeee", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_recordtime.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",mcalid);
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);

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
                    alarmdrug.setText(autoSplitText(alarmdrug));

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

    private String autoSplitText(final TextView tv) {
                 final String rawText = tv.getText().toString(); //原始文本
                 final Paint tvPaint = tv.getPaint(); //paint，包含字體等信息
                 final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控制項可用寬度

                 //將原始文本按行拆分
                 String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
                 StringBuilder sbNewText = new StringBuilder();
                 for (String rawTextLine : rawTextLines) {
                        if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                                 //如果整行寬度在控制項可用寬度之內，就不處理了
                                 sbNewText.append(rawTextLine);
                            } else {
                                 //如果整行寬度超過控制項可用寬度，則按字元測量，在超過可用寬度的前一個字元處手動換行
                                 float lineWidth = 0;
                                 for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                                         char ch = rawTextLine.charAt(cnt);
                                         lineWidth += tvPaint.measureText(String.valueOf(ch));
                                         if (lineWidth <= tvWidth) {
                                                 sbNewText.append(ch);
                                             } else {
                                                 sbNewText.append("\n");
                                                 lineWidth = 0;
                                                 --cnt;
                                             }
                                     }
                             }
                         sbNewText.append("\n");
                     }
                 //把結尾多餘的\n去掉
                 if (!rawText.endsWith("\n")) {
                         sbNewText.deleteCharAt(sbNewText.length() - 1);
                     }

                 return sbNewText.toString();
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

