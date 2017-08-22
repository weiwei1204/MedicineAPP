package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {


    Button btnDisplay,m_store,alarmoff;
    ImageButton btnAdd;
    EditText txtTime;
    EditText m_cal_name;
    EditText txtdate;
    EditText txtday;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String m_caledarUrl = "http://54.65.194.253/Medicine_Calendar/m_calendar.php";
    String get_m_caledar_idUrl = "http://54.65.194.253/Medicine_Calendar/getm_calendarid.php";
    String m_alerttimeUrl = "http://54.65.194.253/Medicine_Calendar/m_alerttime.php";
    String getm_alerttimeUrl = "http://54.65.194.253/Medicine_Calendar/getm_alerttime.php";
    ArrayAdapter<CharSequence> adapterbeacon;
    Spinner spinnerbeacon;
    String memberid,beaconUUID,beaconid,m_calendarid;
    RequestQueue requestQueue;
    int counttime;
    final java.util.ArrayList<String> msg = new ArrayList<String>();
    java.util.ArrayList<String> timearray = new ArrayList<String>();
    Context context;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;






    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        m_cal_name = (EditText)findViewById(R.id.m_cal_name);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        alarmoff = (Button) findViewById(R.id.alarmoff);
        m_store = (Button)findViewById(R.id.m_store);
        txtdate = (EditText) findViewById(R.id.txtdate);
        txtday = (EditText)findViewById(R.id.txtday);
        MyLayoutOperation.display(this, btnDisplay);
//        MyLayoutOperation.add(this, btnAdd);
        add(this,btnAdd);
        Log.d("ssdf",this.toString());
        spinnerbeacon = (Spinner)findViewById(R.id.spinnerbeacon);
        getBeacon();
        this.context=this;
        alarm_manager=(AlarmManager)getSystemService(ALARM_SERVICE);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void getBeacon(){//取此會員的beacon
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getBeaconUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray beacons = response.getJSONArray("Beacons");
                    int count=0;
                    for (int i=0 ; i<beacons.length() ; i++){
                        JSONObject beacon = beacons.getJSONObject(i);
                        String Member_id = beacon.getString("member_id");
                        if (Member_id.equals(memberid)){
                            count++;
                            Log.d("fffabc", String.valueOf(count));
                        };
                    }
                    Log.d("vvv123", String.valueOf(count));
                    final String[] beaconarray=new String[count];
                    final String[] beaconaidrray=new String[count];
                    count=0;
                    for (int i=0 ; i<beacons.length() ; i++){
                        JSONObject beacon = beacons.getJSONObject(i);
                        String UUID = beacon.getString("UUID");
                        String Member_id = beacon.getString("member_id");
                        String id = beacon.getString("id");
                        if (Member_id.equals(memberid)){
                            beaconaidrray[count] = id;
                            beaconarray[count] = UUID;
                            Log.d("vvvvv",beaconarray[count]);
                            count++;
                        };
                    }//取值結束
                    adapterbeacon = new ArrayAdapter(ThirdActivity.this,android.R.layout.simple_spinner_item,beaconarray);
                    adapterbeacon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerbeacon.setSelection(0,false);
                    spinnerbeacon.setAdapter(adapterbeacon);
                    spinnerbeacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
                            beaconUUID= (String) parent.getItemAtPosition(position);
                            for (int i=0;i<beaconaidrray.length;i++){
                                if (beaconUUID.equals(beaconarray[i])){
                                    beaconid = beaconaidrray[i];
                                }
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}//~~~~
                    });
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    public void spinnerbcon(){
//        adapterbeacon = new ArrayAdapter(this,android.R.layout.simple_list_item_1,beaconarray);
//        adapterbeacon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        spinnerbeacon.setSelection(0,false);
//        spinnerbeacon.setAdapter(adapterbeacon);
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void  onStart(){//設定日期和時間
        super.onStart();
        EditText txtDate=(EditText)findViewById(R.id.txtdate);//日期
                txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            DateDialog dialog=new DateDialog(v);
                            android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                            Log.d("ff123",ft.toString());
                            dialog.show(ft,"DatePicker");
                        }
                    }
                });
    }





    public void gotoalarm(View v){ //連到親友認證頁面
        Intent it = new Intent(this,alarm.class);
        startActivity(it);
    }



    public void gotohistoryrecord(View v){ //連到親友認證頁面
        Intent it = new Intent(this,historyrecord.class);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }



    /*
    public void gotoThirdverify(View v){
        Intent it = new Intent(this,thirdverify.class);
        startActivity(it);
    }
    */
    public void add(final Activity activity, ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.activity_edt, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                EditText txtTime= (EditText) newView.findViewById(R.id.timetxt);
                final TextView gettime=(TextView)newView.findViewById(R.id.settimetxt);
                Log.d("fff", String.valueOf(txtTime));
                txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            TimeDialog tdialog=new TimeDialog(v,gettime);
                            try {
                                android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                                Log.d("ff12345",ft.toString());
                                tdialog.show(ft,"TimePicker");
                            }
                            catch (Exception e){
                                Log.d("ffffffff",e.toString());
                            }
                        }
                    }
                });
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                    }
                });
                linearLayoutForm.addView(newView);
            }
        });
    }



    public  void gettime(final Activity activity)//取吃藥次數
    {
        counttime=0;
        LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
        {
            Log.d("nnn","3");
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
            TextView edit = (TextView) innerLayout.findViewById(R.id.settimetxt);
            TextView edittime = (TextView) innerLayout.findViewById(R.id.timetxt);
            if (edit.getText().toString().equals("")){
                Log.d("nnn","2");
            }else {
                msg.add(edit.getText().toString());
                timearray.add(edittime.getText().toString());
                counttime++;
                Log.d("nnn","1");
            }

        }
        Log.d("nnn","5");
//        Toast t = Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
//        t.show();

    }
//    public ArrayList<String> gettimestring(){
//        return msg;
//    }



    public void insertm_calendar(View view) {
        gettime(ThirdActivity.this);
        final int day = Integer.parseInt(txtday.getText().toString());
        final int count = day*counttime;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, m_caledarUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmm",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insertm_calendar.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberid);
                parameters.put("startDate", txtdate.getText().toString());
                parameters.put("day", String.valueOf(day));
                parameters.put("count", String.valueOf(count));
                parameters.put("name",m_cal_name.getText().toString());
                parameters.put("beacon_id",beaconid);
                parameters.put("finish","0");
                parameters.put("open","0");
//                parameters.put("google_id", googleid);
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        get_M_calendart_id();

    }

    public void get_M_calendart_id(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, get_m_caledar_idUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmmmm",response);
                if(response.equals("noid")){
                    get_M_calendart_id();
                }
                else {
                    m_calendarid = response;
                    Log.d("nnn123456",m_calendarid);
//                    for (int j=0;j<msg.size();j++){ //一個一個設鬧鐘
//                        setAlerttime(j);
//                    }
//                    alarm a=new alarm(getApplicationContext());
//                    a.alarmclock(msg);

                    for (int i=0;i<timearray.size();i++){      //一個一個存時間
                        insertAlert_time(i);
                    }
                    Alert_time();
                    gotom_calendarlist();//儲存完後至用藥排成清單
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
                parameters.put("member_id",memberid);
                parameters.put("beacon_id",beaconid);
                Log.d("nnnmmmm",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    public void setAlerttime(int j){         //設鬧鐘
        final Intent my_intent=new Intent(this.context,Alarm_Receiver.class);
        my_intent.putExtra("extra","alarm on");
        my_intent.putExtra("count",j);
        pending_intent= PendingIntent.getBroadcast(this,j,
                my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarm_manager.setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(msg.get(j)),pending_intent);

//        alarm_manager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(msg.get(j)),pending_intent);
        Log.d("sss", String.valueOf(msg.get(j)));

    }
//    private void set_alarm_text(String output) {
//        alarmset.setText(output);
//    }

    public void insertAlert_time(final int i){//時間存到資料庫
        requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest request = new StringRequest(Request.Method.POST, m_alerttimeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmmmmmmm",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error read insertAlerttime.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Medicine_calendar_id",m_calendarid);
                parameters.put("time", timearray.get(i));
                parameters.put("TimeInMillis", msg.get(i));
                Log.d("nnnaaa",timearray.get(i));
                Log.d("nnnaaa",parameters.toString());
                return parameters;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void Alert_time(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getm_alerttimeUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("qqq","1");
                    JSONArray m_alerttimes = response.getJSONArray("Medicine_Alert_Time");
                    Log.d("qqq","3");
                    final String[] malertid=new String[m_alerttimes.length()];
                    final String[] timeInmill=new String[m_alerttimes.length()];
                    int count=0;
                    for (int i=0 ; i<m_alerttimes.length() ; i++){
                        JSONObject m_alerttime = m_alerttimes.getJSONObject(i);
                        String id = m_alerttime.getString("id");
                        String mcalid = m_alerttime.getString("Medicine_calendar_id");
                        String name = m_alerttime.getString("TimeInMillis");
                        Log.d("sss","1");
                        if (mcalid.equals(m_calendarid)){
                            Log.d("sss","2");
                            malertid[count] = id;
                            timeInmill[count] = name;
                            Log.d("sss",malertid[count].toString());
                            final Intent my_intent=new Intent(ThirdActivity.this.context,Alarm_Receiver.class);
                            my_intent.putExtra("extra","alarm on");
                            my_intent.putExtra("alarmid",malertid[count].toString());
                            pending_intent= PendingIntent.getBroadcast(ThirdActivity.this,Integer.parseInt(malertid[count]),
                                    my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(timeInmill[count]),pending_intent);

//        alarm_manager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(msg.get(j)),pending_intent);
                            Log.d("sss", timeInmill[count]);
                            count++;
                        };
                    }
                    if(count==0){
                        Alert_time();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    public void gotom_calendarlist(){
        Intent it = new Intent(ThirdActivity.this,m_calendarlist.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

}
