package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class FirstSaveBsBpActivity extends AppCompatActivity  {
    EditText bsfirstsave;
    EditText bsSecondsave;
    EditText bsThirdsave;
    EditText bpfirstsave ;
    EditText bpSecondsave ;
    EditText bpThirdsave;
    public String googleid;
    public String memberid;
    RequestQueue requestQueue;
    String bs_first = "2017-01-01 00:00:00";
    String bs_second = "2017-01-01 00:00:00";
    String bs_third = "2017-01-01 00:00:00";
    String bp_first = "2017-01-01 00:00:00";
    String bp_second = "2017-01-01 00:00:00";
    String bp_third = "2017-01-01 00:00:00";
    String getidUrl = "http://54.65.194.253/Member/getid.php";
    String insertFirstUrl = "http://54.65.194.253/Member/insertMeature.php";
    String gethealth_timeUrl = "http://54.65.194.253/Health_Calendar/gethealth_time.php";
    String inserth_alerttimeUrl = "http://54.65.194.253/Health_Calendar/inserth_alerttime.php";
    private java.util.ArrayList<String> timearray = new ArrayList<String>();
    private PendingIntent pending_intent1;
    private AlarmManager alarm_manager;
    Context context;
    SQLiteDatabase sqLiteDatabase;
    String col_id = "id";
    String col_member_id = "member_id";
    String col_bs_first = "bs_first";
    String col_bs_second = "bs_second";
    String col_bs_third = "bs_third";
    String col_bp_first  = "bp_first";
    String col_bp_second = "bp_second";
    String col_bp_third = "bp_third";
    String TABLE_NAME = "Health_BsBpMeasureTime";
    public static final String DATABASE_NAME = "MedicineTest.db";





    /**
     * Id to identity READ_CONTACTS permission request.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");
        Log.d("mm111",googleid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_save_bs_bp);


        alarm_manager=(AlarmManager)getSystemService(ALARM_SERVICE);

        bsfirstsave = (EditText) findViewById(R.id.bsfirstsave);
        bsSecondsave = (EditText) findViewById(R.id.bsSecondSave);
        bsThirdsave = (EditText) findViewById(R.id.bsThirdSave);
        bpfirstsave = (EditText) findViewById(R.id.bpfirstsave);
        bpSecondsave = (EditText) findViewById(R.id.bpSecondSave);
        bpThirdsave = (EditText) findViewById(R.id.bpThirdSave);
        this.context=this;


    }
    public void setTime1(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bsfirstsave.setText("0"+hourOfDay+":0"+minute);
                    bs_first = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_first : ",bs_first);
                }else if(minute<10){
                    bsfirstsave.setText(hourOfDay+":0"+minute);
                    bs_first = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_first : ",bs_first);
                }
                else if(hourOfDay<10){
                    bsfirstsave.setText("0"+hourOfDay+":"+minute);
                    bs_first = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_first : ",bs_first);
                }
                else {
                    bsfirstsave.setText(hourOfDay+":"+minute);
                    bs_first = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bs_first : ",bs_first);
                }
            }
        },hour,minute,true);

        timePickerDialog.show();
    }
    public void setTime2(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bsSecondsave.setText("0"+hourOfDay+":0"+minute);
                    bs_second = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_second : ",bs_second);
                }else if(minute<10){
                    bsSecondsave.setText(hourOfDay+":0"+minute);
                    bs_second = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_second : ",bs_second);
                }
                else if(hourOfDay<10){
                    bsSecondsave.setText("0"+hourOfDay+":"+minute);
                    bs_second = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bs_second : ",bs_second);
                }
                else {
                    bsSecondsave.setText(hourOfDay+":"+minute);
                    bs_second = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bs_second : ",bs_second);
                }
            }
        },hour,minute,true);

        timePickerDialog.show();
    }

    public void setTime3(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bsThirdsave.setText("0"+hourOfDay+":0"+minute);
                    bs_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_third : ",bs_third);
                }else if(minute<10){
                    bsThirdsave.setText(hourOfDay+":0"+minute);
                    bs_third = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bs_third : ",bs_third);
                }
                else if(hourOfDay<10){
                    bsThirdsave.setText("0"+hourOfDay+":"+minute);
                    bs_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bs_third : ",bs_third);
                }
                else {
                    bsThirdsave.setText(hourOfDay+":"+minute);
                    bs_third = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bs_third : ",bs_third);
                }
            }
        },hour,minute,true);

        timePickerDialog.show();
    }

    public void setTime4(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bpfirstsave.setText("0"+hourOfDay+":0"+minute);
                    bp_first = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_first : ",bp_first);
                }else if(minute<10){
                    bpfirstsave.setText(hourOfDay+":0"+minute);
                    bp_first = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_first : ",bp_first);
                }
                else if(hourOfDay<10){
                    bpfirstsave.setText("0"+hourOfDay+":"+minute);
                    bp_first = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_first : ",bp_first);
                }
                else {
                    bpfirstsave.setText(hourOfDay+":"+minute);
                    bp_first = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_first : ",bp_first);
                }

            }
        },hour,minute,true);

        timePickerDialog.show();
    }

    public void setTime5(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bpSecondsave.setText("0"+hourOfDay+":0"+minute);
                    bp_second = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_second : ",bp_second);
                }else if(minute<10){
                    bpSecondsave.setText(hourOfDay+":0"+minute);
                    bp_second = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_second : ",bp_second);
                }
                else if(hourOfDay<10){
                    bpSecondsave.setText("0"+hourOfDay+":"+minute);
                    bp_second = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_second : ",bp_second);
                }
                else {
                    bpSecondsave.setText(hourOfDay+":"+minute);
                    bp_second = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_second : ",bp_second);
                }
            }
        },hour,minute,true);

        timePickerDialog.show();
    }
    public void setTime6(View view){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;

        timePickerDialog = new TimePickerDialog(FirstSaveBsBpActivity.this,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                if(minute<10&&hourOfDay<10){
                    bpThirdsave.setText("0"+hourOfDay+":0"+minute);
                    bp_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_third : ",bp_third);
                }else if(minute<10){
                    bpThirdsave.setText(hourOfDay+":0"+minute);
                    bp_third = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
                    Log.d("bp_third : ",bp_third);
                }
                else if(hourOfDay<10){
                    bpThirdsave.setText("0"+hourOfDay+":"+minute);
                    bp_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_third : ",bp_third);
                }
                else {
                    bpThirdsave.setText(hourOfDay+":"+minute);
                    bp_third = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    Log.d("bp_third : ",bp_third);
                }
            }
        },hour,minute,true);

        timePickerDialog.show();
    }
    public void gotoMainActivity1(View v){ //連到首頁
        timearray.add(0,bsfirstsave.getText().toString());
        timearray.add(1,bsSecondsave.getText().toString());
        timearray.add(2,bsThirdsave.getText().toString());
        timearray.add(3,bpfirstsave.getText().toString());
        timearray.add(4,bpSecondsave.getText().toString());
        timearray.add(5,bpThirdsave.getText().toString());
        getMemberid();
        getMesure();
        Intent it = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void saveFirstInput(View v){ //連到首頁
//        insertmember();
//        insertMonitor();
        Intent it = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void insertFirstMeasure() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, insertFirstUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                inserh_alerttime(timearray.get(0),"bs_1",false);
                inserh_alerttime(timearray.get(1),"bs_2",false);
                inserh_alerttime(timearray.get(2),"bs_3",false);
                inserh_alerttime(timearray.get(3),"bp_1",false);
                inserh_alerttime(timearray.get(4),"bp_2",false);
                inserh_alerttime(timearray.get(5),"bp_3",true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insertMeature.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bs_first", bs_first);
                parameters.put("bs_second",bs_second);
                parameters.put("bs_third",bs_third);
                parameters.put("bp_first",bp_first);
                parameters.put("bp_second",bp_second);
                parameters.put("bp_third",bp_third);
                parameters.put("member_id", memberid);
                Log.d("test111111111", parameters.toString());
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    public void getMemberid(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, getidUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rrr1234", response);
                memberid = response;
                Log.d("rrr1234", memberid);
                Log.d("rrr1234", String.valueOf(timearray.size()));
                insertFirstMeasure();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr111", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("google_id", googleid);
                Log.d("my123", parameters.toString());
                Log.d("my123","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void inserh_alerttime(final String time, final String type , final Boolean finish){  //存時間 時間 血壓血糖早中晚 是否存完
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, inserth_alerttimeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                if (finish){
                    setnotice();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("timeeee", error.toString());
                Toast.makeText(getApplicationContext(), "Error read inserth_alerttime.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberid);
                parameters.put("time",time);
                parameters.put("type",type);

                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }
    public void setnotice(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, gethealth_timeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    String dbtype = null;
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        Log.d("timeeeee", "1");
                        if (obj.getString("time")!=null){
                            String hid = obj.getString("id");
                            String time = obj.getString("time");
                            String type = obj.getString("type");
                            if (type.equals("bs_1")||type.equals("bs_2")||type.equals("bs_3")){
                                dbtype="bs";
                            }
                            else if(type.equals("bp_1")||type.equals("bp_2")||type.equals("bp_3")){
                                dbtype="bp";
                            }
                            int alarmsetid= -Integer.valueOf(hid);
                            Log.d("timeeeee", String.valueOf(alarmsetid));
                            Log.d("timeeeee", "2");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date dt = new  Date();
                            String todate = sdf.format(dt);
                            String date = todate+" "+time;
                            Date ddate = sdf2.parse(date);
                            Long ldate = ddate.getTime();
                            if (ddate.getTime() - dt.getTime() > 60*1000){
                            }else {
                                Calendar now = Calendar.getInstance();
                                now.setTime(ddate);
                                now.add(Calendar.DAY_OF_YEAR, +1);  //起始日+1天
                                String tomorrow = sdf.format(now.getTime());
                                date = tomorrow+" "+time;
                                ddate = sdf2.parse(date);
                                ldate = ddate.getTime();
                            }
                            final Intent my_intent=new Intent(FirstSaveBsBpActivity.this.context,Alarm_Receiver.class);
                            my_intent.putExtra("extra","alarm on");
                            my_intent.putExtra("alarmid",String.valueOf(alarmsetid));
                            my_intent.putExtra("memberid",memberid);
                            my_intent.putExtra("alarmtype","health"+dbtype);
                            pending_intent1= PendingIntent.getBroadcast(FirstSaveBsBpActivity.this,alarmsetid,
                                    my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, ldate,pending_intent1);
                        }

                    }
                } catch (JSONException e) {
                    Log.d("timeeee3",e.toString());
                    e.printStackTrace();
                } catch (ParseException e) {
                    Log.d("timeeee4",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("timeeee", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberid);
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }
    public void addBsBpData(String a ,String b,String c ,String d,String e ,String f,String g ,String h){
        ContentValues contentValues = new ContentValues(8);
        contentValues.put("id","0");
        contentValues.put("member_id",b);
        contentValues.put("bs_first",c);
        contentValues.put("bs_second",d);
        contentValues.put("bs_third",e);
        contentValues.put("bp_first",f);
        contentValues.put("bp_second",g);
        contentValues.put("bp_third",h);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);


    }
    public void getMesure(){
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        String SQL_String = "CREATE  TABLE  IF NOT EXISTS " + TABLE_NAME + "(" + col_id + " VARCHAR(32)," + col_member_id + " VARCHAR(32)," +col_bs_first + " VARCHAR(32)," + col_bs_second + " VARCHAR(32)," + col_bs_third +" VARCHAR(32),"+ col_bp_first +" VARCHAR(32),"+ col_bp_second +" VARCHAR(32),"+ col_bp_third +" VARCHAR(32))";
        sqLiteDatabase.execSQL(SQL_String);
        Cursor c  = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (c.getCount() == 0) {
            addBsBpData("0",memberid,bs_first,bs_second,bs_third,bp_first,bp_second,bp_third);
            c  = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
            if(c.moveToFirst()){
                do {
                    Log.d("testMeasure : ",c.getString(0)+","+c.getString(1)+","+c.getString(2)+","+c.getString(3)+","+c.getString(4)+","+c.getString(5)+","+c.getString(6)+","+c.getString(7));
                }while (c.moveToNext());
            }

        }
    }


}

