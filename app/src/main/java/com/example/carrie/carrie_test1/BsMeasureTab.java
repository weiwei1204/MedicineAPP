package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by mindy on 2017/9/4.
 */
public class BsMeasureTab extends Fragment{
    ListView bsmeasure;
    private ArrayList<BsBpMeasureObject> my_measure =new ArrayList<BsBpMeasureObject>();
    MesureAdapter mesureAdapter ;
    Button saveBsMeasureButton;
    String bs1,bsat1;
    String bs2,bsat2;
    String bs3,bsat3;
    RequestQueue requestQueue;
    String memberid;
    String updateFirstUrl = "http://54.65.194.253/Member/updateBsMeature.php";
    String gethealth_timeUrl = "http://54.65.194.253/Health_Calendar/gethealth_time.php";
    String updateh_alerttimeUrl = "http://54.65.194.253/Health_Calendar/updateh_alerttime.php";
    String[] measure_time={"9:00","12:00","18:00"};
    PendingIntent pending_intent;
    AlarmManager alarm_manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.bsmeature_tab1, container, false);
        bsmeasure = (ListView)rootView.findViewById(R.id.bsfirst);
        //BsBpMeasureObject bs = new BsBpMeasureObject(1,"1","7:00","12:30","18:00","9:00","12:00","18:00");
        Intent intent = getActivity().getIntent();
        Bundle bundle = getActivity().getIntent().getExtras();
        memberid=bundle.getString("memberid");
        BsBpMeasureObject bsBpMeasureObject = (BsBpMeasureObject)intent.getSerializableExtra("bsBpMeasureObject");
        my_measure.add(bsBpMeasureObject);
        mesureAdapter=new MesureAdapter(getActivity(),my_measure);
        bsmeasure.setAdapter(mesureAdapter);

        bsmeasure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你按下"+position, Toast.LENGTH_SHORT).show();
            }

        });
        saveBsMeasureButton = (Button) rootView.findViewById(R.id.saveBsMeasureButton);
        saveBsMeasureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("saveBsMeasureButton","clickabble");
                View view1 = bsmeasure.getChildAt(0);
                View view2 = bsmeasure.getChildAt(1);
                View view3 = bsmeasure.getChildAt(2);
                EditText bs_1 =(EditText) view1.findViewById(R.id.TimeMeasureDisplay);
                EditText bs_2 =(EditText) view2.findViewById(R.id.TimeMeasureDisplay);
                EditText bs_3 =(EditText) view3.findViewById(R.id.TimeMeasureDisplay);
                Log.d("saveBsMeasureButton",bs_1.getText().toString());
                Log.d("saveBsMeasureButton",bs_2.getText().toString());
                Log.d("saveBsMeasureButton",bs_3.getText().toString());
                if (bs_1.getText().length()==0){
                    bs1 = "2017-01-01 00:00:00";
//                    bsat1 = null;
                }else{
                    bs1 = "2017-09-18 "+bs_1.getText().toString();
                }
                if (bs_2.getText().length()==0){
                    bs2 = "2017-01-01 00:00:00";
//                    bsat2 = null;
                }else{
                    bs2 = "2017-09-18 "+bs_2.getText().toString();
                }
                if (bs_3.getText().length()==0){
                    bs3 = "2017-01-01 00:00:00";
//                     bsat3 = null;
                }else{
                    bs3 = "2017-09-18 "+bs_3.getText().toString();
                }
//                Log.d("saveBpMeasureButton",bp_3.getText().toString());
                bsat1 = bs_1.getText().toString();
                bsat2 = bs_2.getText().toString();
                bsat3 = bs_3.getText().toString();
                inserh_alerttime(bsat1,"bs_1",false);
                inserh_alerttime(bsat2,"bs_2",false);
                inserh_alerttime(bsat3,"bs_3",true);
                insertBsMeasure();
                saveCheck();
            }
        });
        return rootView;

    }
    public void insertBsMeasure() {

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, updateFirstUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Error read updateBsMeature.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bs_first", bs1);
                parameters.put("bs_second",bs2);
                parameters.put("bs_third",bs3);
                parameters.put("member_id",memberid);
                Log.d("test111111111", parameters.toString());
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void inserh_alerttime(final String time, final String type , final Boolean finish){  //存時間 時間 血壓血糖早中晚 是否存完
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, updateh_alerttimeUrl, new Response.Listener<String>() {
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
                Toast.makeText(getActivity().getApplicationContext(), "Error read updateh_alerttime.php!!!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public void setnotice(){    //設定提醒
        alarm_manager=(AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, gethealth_timeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] dbtype=new String[jarray.length()];
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String type = obj.getString("type");
                        Log.d("timeeeee", "1");
                        if (obj.getString("time")!=null){
                            if (type.equals("bs_1") || type.equals("bs_2") || type.equals("bs_3")){
                                String id = obj.getString("id");
                                String time = obj.getString("time");
                                int alarmsetid= -Integer.valueOf(id);
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
                                Log.d("timeeeee", String.valueOf(ddate));
                                final Intent my_intent=new Intent(getActivity(),Alarm_Receiver.class);
                                my_intent.putExtra("extra","alarm on");
                                my_intent.putExtra("alarmid",String.valueOf(alarmsetid));
                                my_intent.putExtra("memberid",memberid);
                                my_intent.putExtra("alarmtype","health");
                                pending_intent= PendingIntent.getBroadcast(getActivity(),alarmsetid,
                                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, ldate,pending_intent);
                            }

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
                Toast.makeText(getActivity().getApplicationContext(), "Error read gethealth_time.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberid);
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(drugrequest);
    }
    public void saveCheck() {
        new AlertDialog.Builder(getActivity())
                .setMessage("儲存成功")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }

}
