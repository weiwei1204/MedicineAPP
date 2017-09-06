package com.example.carrie.carrie_test1;

import android.app.TimePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;
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
    /**
     * Id to identity READ_CONTACTS permission request.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");
        Log.d("mm111",googleid);
        getMemberid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_save_bs_bp);


        bsfirstsave = (EditText) findViewById(R.id.bsfirstsave);
        bsSecondsave = (EditText) findViewById(R.id.bsSecondSave);
        bsThirdsave = (EditText) findViewById(R.id.bsThirdSave);
        bpfirstsave = (EditText) findViewById(R.id.bpfirstsave);
        bpSecondsave = (EditText) findViewById(R.id.bpSecondSave);
        bpThirdsave = (EditText) findViewById(R.id.bpThirdSave);



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
        insertFirstMeasure();
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

}

