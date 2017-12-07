package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import static android.R.attr.id;

public class notification extends AppCompatActivity {
    TextClock textClock;
    TextView health , alarmdrug;
    RequestQueue requestQueue;
    Button delaybtnh,alarmoffh;
    Context context;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;
    private String memberid,alarmtype,alarmtime;
    private int alarmid;
    String gethealth_timeUrl = "http://54.65.194.253/Health_Calendar/gethealth_time.php";




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        health = (TextView) findViewById(R.id.health);
        textClock = (TextClock) findViewById(R.id.textClock);
        alarmoffh = (Button) findViewById(R.id.alarmoffh);
        delaybtnh = (Button) findViewById(R.id.delaybtnh);
        Log.d("timeeeeooo","here");
        Bundle bundle = getIntent().getExtras();
        alarmid = Integer.parseInt(bundle.getString("alarmid"));
        memberid = bundle.getString("memberid");
        alarmtype = bundle.getString("alarmtype");

        gethealthdb();

        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);
        alarmoffh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm(my_intent);
                Log.d("timeeeee", "okkkk");
            }
        });
        delaybtnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timeeeee", "delayyyy");
                Alarmdelay(my_intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Bundle bundle1 = getIntent().getExtras();
        alarmid = Integer.parseInt(bundle1.getString("alarmid"));
        memberid = bundle1.getString("memberid");
        alarmtype = bundle1.getString("alarmtype");
        String msg=null;
        if (alarmtype.equals("healthbs")){msg="量血糖囉！";}
        else if (alarmtype.equals("healthbp")){msg="量血壓囉！";}
        builder.setMessage(msg)
                .setTitle("Time's Up")
                .setCancelable(false)
                .setPositiveButton("進行測量", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        alarmoffh.performClick();
                    }
                })
                .setNegativeButton("延遲", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delaybtnh.performClick();
                        notification.this.finish();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main3_drawer, menu);
        return true;
    }



    public void gethealthdb(){  //取資料庫值 health_alert_time
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, gethealth_timeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        Log.d("timeeeee", "1");
                        String id = obj.getString("id");
                        String time = obj.getString("time");
                        String type = obj.getString("type");
                        Log.d("timeeeee", type);
                        if (Math.abs(alarmid) == Integer.valueOf(id)){
                            alarmtime=time;
                            if (type.equals("bs_1") || type.equals("bs_2") || type.equals("bs_3")){
                                health.setText("量血糖囉～");
                            }else if(type.equals("bp_1") || type.equals("bp_2") || type.equals("bp_3")){
                                health.setText("量血壓囉～");
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.d("timeeee",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("timeeee", error.toString());
                Toast.makeText(getApplicationContext(), "Error read gethealth_time.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }

    public void cancelAlarm(Intent my_intent){
        set_alarm_text("Alarm off!");
        my_intent.putExtra("extra","alarm off");
        my_intent.putExtra("alarmid",String.valueOf(alarmid));
        my_intent.putExtra("alarmtype",alarmtype);
        my_intent.putExtra("memberid",memberid);
        pending_intent= PendingIntent.getBroadcast(notification.this,alarmid,
                my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarm_manager.cancel(pending_intent);
        sendBroadcast(my_intent);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, +1);  //起始日+1天
        String tomorrow = sdf.format(now.getTime());
        String date = tomorrow+" "+alarmtime;
        Date ddate = null;
        try {
            ddate = sdf2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long ldate = ddate.getTime();
        final Intent my_intent1=new Intent(notification.this.context,Alarm_Receiver.class);
        my_intent1.putExtra("extra","alarm on");
        my_intent1.putExtra("alarmid",String.valueOf(-alarmid));
        my_intent1.putExtra("memberid",memberid);
        my_intent1.putExtra("alarmtype","health");
        pending_intent= PendingIntent.getBroadcast(notification.this,-alarmid,
                my_intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        alarm_manager.setExact(AlarmManager.RTC_WAKEUP, ldate,pending_intent);
        Intent it = new Intent(notification.this,EnterBsBpActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void Alarmdelay(final Intent my_intent){      //延遲時間
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new  Date();
        Long now = dt.getTime();    //  現在時間
        String todate = sdf.format(dt);
        String date = todate+" "+alarmtime; //提醒時間
        Date ddate = null;
        int delay=4;
        try {
            ddate = sdf2.parse(date);
        } catch (ParseException e) {
            Log.d("notify",e.toString());
            e.printStackTrace();
        }
        Long ldate = ddate.getTime();
        if (now - ddate.getTime() > 13*60*1000){   //代表提醒時間超過一個小時
            cancelAlarm(my_intent);
        }
        else {
            if (ddate.getTime()+3*3*60*1000 - now > 60*1000){ //現在時間
                Log.d("timeee5","3");
                delay=1;
            }
            if (ddate.getTime()+2*3*60*1000 - now > 60*1000){
                Log.d("timeee5","2");
                delay=2;
            }
            if (ddate.getTime()+1*3*60*1000 - now > 60*1000){
                Log.d("timeee5","1");
                delay=3;
            }
            if (delay!=4){
                my_intent.putExtra("extra","alarm off");
                my_intent.putExtra("alarmid",String.valueOf(alarmid));
                my_intent.putExtra("alarmtype",alarmtype);
                my_intent.putExtra("memberid",memberid);
                set_alarm_text("Alarm off!");
                sendBroadcast(my_intent);

                my_intent.putExtra("extra","alarm on");
                my_intent.putExtra("alarmid",String.valueOf(alarmid));
                my_intent.putExtra("alarmtype",alarmtype);
                my_intent.putExtra("memberid",memberid);
                pending_intent= PendingIntent.getBroadcast(notification.this,alarmid,
                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_manager.setExact(AlarmManager.RTC_WAKEUP,ddate.getTime()+(4-delay)*3*60*1000, pending_intent);
//                            sendBroadcast(my_intent);
//            Intent it = new Intent(notification.this,EnterBsBpActivity.class);
//            Bundle bundle1 = new Bundle();
//            bundle1.putString("memberid", memberid);
//            it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
//            startActivity(it);
            }else {
                cancelAlarm(my_intent);
            }

        }

    }
    private void set_alarm_text(String output) {
        health.setText(output);
    }


    public void goback(View v){
        finish();
    }

}
