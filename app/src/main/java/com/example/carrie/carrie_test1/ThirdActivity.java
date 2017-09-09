package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {


    Button btnDisplay,m_store,alarmoff;
    ImageButton btnAdd,adddrug;
    EditText txtTime;
    EditText m_cal_name;
    EditText txtdate;
    EditText txtday;
    EditText notxt;
    private ArrayList<ArrayList<String>> mtimes = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> mdrugs = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> dbtimes = new ArrayList<ArrayList<String>>();


    //    private String[][] mtimes=new String[20][2];
//    private String[][] mdrugs=new String[20][2];
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String m_caledarUrl = "http://54.65.194.253/Medicine_Calendar/m_calendar.php";
    String get_m_caledar_idUrl = "http://54.65.194.253/Medicine_Calendar/getm_calendarid.php";
    String m_alerttimeUrl = "http://54.65.194.253/Medicine_Calendar/m_alerttime.php";
    String getm_alerttimeUrl = "http://54.65.194.253/Medicine_Calendar/getm_alerttime.php";
    String insertmcaldrugsUrl = "http://54.65.194.253/Drug/insertmcaldrugs.php";
    String insertm_recordUrl = "http://54.65.194.253/Medicine_Calendar/insertm_record.php";
    String getm_recordtimeUrl = "http://54.65.194.253/Medicine_Calendar/getm_recordtime.php";
    ArrayAdapter<CharSequence> adapterbeacon;
    Spinner spinnerbeacon;
    String memberid,beaconUUID,beaconid,m_calendarid,drugid,drugname;
    int entertype,checkbtn;
    RequestQueue requestQueue;
    int counttime;
    final java.util.ArrayList<String> msg = new ArrayList<String>();
    java.util.ArrayList<String> timearray = new ArrayList<String>();
    Context context;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;

    //錯誤提示（防呆）
    private Vibrator vib;
    Animation animShake;
    private TextInputLayout m_cal_name1,txtdate1,txtday1,time1,newdrug1;






    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Bundle bundle1 = getIntent().getExtras();
        memberid=bundle1.getString("memberid");
        Bundle bundle = getIntent().getExtras();
        entertype=bundle.getInt("entertype");
        drugid=bundle.getString("drugid");
        drugname=bundle.getString("chineseName",drugname);
        m_cal_name = (EditText)findViewById(R.id.m_cal_name);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        alarmoff = (Button) findViewById(R.id.alarmoff);
        m_store = (Button)findViewById(R.id.m_store);
        txtdate = (EditText) findViewById(R.id.txtdate);
        txtday = (EditText)findViewById(R.id.txtday);
        adddrug = (ImageButton)findViewById(R.id.adddrug);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        m_cal_name1 = (TextInputLayout)findViewById(R.id.m_cal_name1);
        txtdate1 = (TextInputLayout)findViewById(R.id.txtdate1);
        txtday1 = (TextInputLayout)findViewById(R.id.txtday1);
        time1 = (TextInputLayout)findViewById(R.id.time1);







        if (entertype==1) {          //代表從藥品資訊頁面跳轉過來
            memberid=mcaldata.getMemberid();
            m_cal_name.setText(mcaldata.getMcalname());
            txtdate.setText(mcaldata.getMcaldate());
            txtday.setText(mcaldata.getMcalday());
//            checkbtn=1;
//            mdrugs.add(new ArrayList<String>());
//            Log.d("drugsize",String.valueOf(mdrugs.size()));
//            mdrugs.get(mdrugs.size()-1).add(drugid);
//            mdrugs.get(mdrugs.size()-1).add(drugname);


        }else if (entertype == 0){
            mtimes.clear();
            mdrugs.clear();
        }

        MyLayoutOperation.display(this, btnDisplay);
//        MyLayoutOperation.add(this, btnAdd);
        add(this,btnAdd);
        drug(this,adddrug);
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
                if (entertype == 1){       //塞值
                    for (int i=0;i<mtimes.size();i++){
                        if (!mtimes.get(i).get(0).equals(null)&&!mtimes.get(i).get(0).equals("0")){
                            txtTime.setText(mtimes.get(i).get(0));
                            gettime.setText(mtimes.get(i).get(1));
                            Log.d("drug", mtimes.get(i).get(0));
                            Log.d("drugi=", String.valueOf(i));
                            mtimes.get(i).add(0,"0");
                            break;
                        }
                    }

                }
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
        if(entertype == 1){ //從新增藥品頁面回來後把值恢復
//            btn.callOnClick();//換方法
            mtimes=mcaldata.getMcaltimes();
            for (int i=0;i<mtimes.size();i++){
                if (!mtimes.get(i).get(0).equals(null)){
                    btnAdd.performClick();
                }
            }
        }


    }


    public void drug(final Activity activity, ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.newdrug);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                gettime(ThirdActivity.this);
                getdrugs(ThirdActivity.this);
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.m_search, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView drugtxt= (TextView) newView.findViewById(R.id.drugtxt);
                final TextView drugidtxt=(TextView)newView.findViewById(R.id.drugid);
                if (entertype == 1 && checkbtn !=-1 && checkbtn !=-2){       //塞值
                    drugtxt.setText(mdrugs.get(checkbtn).get(0));
                    drugidtxt.setText(mdrugs.get(checkbtn).get(1));
                    Log.d("drug222",mdrugs.get(checkbtn).get(1));
                }
                else if (checkbtn == -2){//新增剛加入的藥品
                    drugtxt.setText(drugname);
                    drugidtxt.setText(drugid);
                    Log.d("drug22222",drugid);
                    checkbtn=-1;
                }
                else {
                   store();
                }
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
        if(entertype == 1){
            checkbtn=0;     //          判斷是手動觸發adddrug
            mdrugs=mcaldata.getMcaldrugs();
            int size=mdrugs.size();
            Log.d("drugarray1",String.valueOf(size));
            for (int i=0;i<size;i++){
                Log.d("drugaaaccc",String.valueOf(i));
                if (!mdrugs.get(i).get(0).equals(null)){
                    Log.d("drug",mdrugs.get(i).get(0));
                    checkbtn=i;
                    adddrug.performClick();
                }
            }
            checkbtn=-2;//最新的藥
            adddrug.performClick();

            checkbtn=-1;

        }
    }



    public void store(){        //跳頁之前先把輸入好的資料存起來
        mdrugs.clear();
        mtimes.clear();
        gettime(ThirdActivity.this);
        getdrugs(ThirdActivity.this);
        mcaldata.setMemberid(memberid);
        mcaldata.setMcalname(m_cal_name.getText().toString());
        mcaldata.setMcaldate(txtdate.getText().toString());
        mcaldata.setMcalday(txtday.getText().toString());
        mcaldata.setMcaltimes(mtimes);
        mcaldata.setMcaldrugs(mdrugs);
        Log.d("drug", String.valueOf(mtimes.size()));
        Intent it = new Intent(ThirdActivity.this,druginfo.class);
        Bundle bundle3 = new Bundle();
        bundle3.putString("my_id", "0");
        bundle3.putString("my_google_id", "0");
        bundle3.putString("my_supervise_id", "0");
        bundle3.putString("m_calid","0");
        it.putExtras(bundle3);
        startActivity(it);
    }


    public  void gettime(final Activity activity)//取吃藥次數
    {
        counttime = 0;
        timearray.clear();
        LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++) {
            Log.d("nnn", "3");
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
            TextView edit = (TextView) innerLayout.findViewById(R.id.settimetxt);
            TextView edittime = (TextView) innerLayout.findViewById(R.id.timetxt);
            if (edit.getText().toString().equals("")) {
                Log.d("nnn", "2");
            } else {
                Log.d("nnn", edit.getText().toString());
                mtimes.add(new ArrayList<String>());

                mtimes.get(counttime).add(edittime.getText().toString());   //0
                mtimes.get(counttime).add(edit.getText().toString());   //1
                msg.add(edit.getText().toString());
                timearray.add(edittime.getText().toString());
                counttime++;
                Log.d("nnn", "1");
            }
        }
    }



    public  void getdrugs(final Activity activity)//取吃藥次數
    {
        counttime=0;
        LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.newdrug);
        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
        {
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
            TextView txt = (TextView) innerLayout.findViewById(R.id.drugtxt);
            TextView id = (TextView) innerLayout.findViewById(R.id.drugid);
            if (txt.getText().toString().equals("")){
            }else {
                mdrugs.add(new ArrayList<String>());//存入另一個class在跳頁
                mdrugs.get(counttime).add(txt.getText().toString());
                mdrugs.get(counttime).add(id.getText().toString());
                counttime++;
            }

        }
    }


    public void insertm_calendar() {

//                drugnameid1.setErrorEnabled(false);
        gettime(ThirdActivity.this);
        final int day = Integer.parseInt(txtday.getText().toString());
        final int count = day*counttime;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, m_caledarUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmm",response);
                if (response.equals("beaconed")){
                    Toast.makeText(getApplicationContext(), "這個beacon已用過!!!", Toast.LENGTH_LONG).show();

                }
                else {get_M_calendart_id(count);}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insertm_calendar.php!!!", Toast.LENGTH_LONG).show();
            }})
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d("bcon1",this.toString());
        requestQueue.add(request);
    }



    public void get_M_calendart_id(final int counttimes){   //counttimes為了記服藥次數
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, get_m_caledar_idUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmmmm",response);
                if(response.equals("noid")){
                    get_M_calendart_id(counttimes);
                }
                else {
                    m_calendarid = response;
                    Log.d("nnn123456",m_calendarid);
//                    for (int j=0;j<msg.size();j++){ //一個一個設鬧鐘
//                        setAlerttime(j);
//                    }
//                    alarm a=new alarm(getApplicationContext());
//                    a.alarmclock(msg);
                    mtimes.clear();
                    for (int i=0;i<timearray.size();i++){      //一個一個存時間
                        boolean finish=false;       //判斷執行最後一個
                        if (i==timearray.size()-1){
                            finish=true;
                        }
                        insertAlert_time(i,finish,counttimes);
                    }

                    mdrugs.clear();
                    getdrugs(ThirdActivity.this);
                    int size=mdrugs.size();
                    for (int i=0;i<size;i++){
                        insertdrug(i);
                    }


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

    public void insertAlert_time(final int i,final boolean finish ,final int counttimes){//時間存到資料庫
        requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest request = new StringRequest(Request.Method.POST, m_alerttimeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("timeeeee",response);
                if (finish==true){
                    Alert_time(counttimes);
                }
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


    public void insertdrug(final int i){ //存入排成的藥袋
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, insertmcaldrugsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmm",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insertmcaldrugs.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Medicine_Calendar_id",m_calendarid);
                parameters.put("Drug_id", mdrugs.get(i).get(1));
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void Alert_time(final int counttimes){   //從資料庫取值(鬧鐘時間) //counttimes為了記服藥次數
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getm_alerttimeUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    dbtimes.clear();
                    JSONArray m_alerttimes = response.getJSONArray("Medicine_Alert_Time");
                    final String[] malertid=new String[m_alerttimes.length()];
                    final String[] timeInmill=new String[m_alerttimes.length()];
                    int count=0;
                    for (int i=0 ; i<m_alerttimes.length() ; i++){
                        JSONObject m_alerttime = m_alerttimes.getJSONObject(i);
                        String id = m_alerttime.getString("id");
                        String mcalid = m_alerttime.getString("Medicine_calendar_id");
                        String time = m_alerttime.getString("time");
                        String name = m_alerttime.getString("TimeInMillis");
                        if (mcalid.equals(m_calendarid)){
                            dbtimes.add(new ArrayList<String>());
                            dbtimes.get(count).add(time);   //0:Medicine_Alert_Time的時間time
                            dbtimes.get(count).add(id);   //1:Medicine_Alert_Time的id
                            malertid[count] = id;
                            timeInmill[count] = name;
                            Log.d("sss",malertid[count].toString());
//                            final Intent my_intent=new Intent(ThirdActivity.this.context,Alarm_Receiver.class);
//                            my_intent.putExtra("extra","alarm on");
//                            my_intent.putExtra("alarmid",malertid[count].toString());
//                            my_intent.putExtra("mcalid",m_calendarid);
//                            pending_intent= PendingIntent.getBroadcast(ThirdActivity.this,Integer.parseInt(malertid[count]),
//                                    my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
//                            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(timeInmill[count]),pending_intent);

//        alarm_manager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(msg.get(j)),pending_intent);
                            Log.d("sss", timeInmill[count]);
                            count++;
                        };
                    }
                    if(count==0){
                        Alert_time(counttimes);
                    }else {
                        checkm_record(counttimes);
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


    //新增每次吃藥狀況 Medicinhe_Calendar_Record
    public void checkm_record(int counttimes){
        Boolean finish=false;    //尚未儲存完
        Boolean today=false;        //確認起始日是否為今日
        String begindate=txtdate.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new  Date();
        String dts=sdf.format(dt);
        String ymds=ymd.format(dt);
        if (counttimes > 0){     //服藥總次數判斷
            for (int i=0;i<dbtimes.size();i++){
                String date =  begindate+" "+dbtimes.get(i).get(0);    //算起始日的時間
                if (begindate.equals(ymds)){    //如與今天日期相同再進行以下比較
                    today=true;
                    Long mmdts,mmdate,time;
                    try {
                        Date ddts = sdf.parse(dts);
                        Date ddate = sdf.parse(date);
                        mmdts = ddts.getTime();
                        mmdate = ddate.getTime();
                        time=mmdate-mmdts;      //時間相減（-：比現在時間以前）
                        Log.d("record3", String.valueOf(time));
                        if (time > 15000 && counttimes > 0){      //如果比現在時間更多15秒即可設為鬧鐘 && 次數要大於0
                            insertm_record(i,begindate,finish);
                            counttimes--;
                        }
                    } catch (ParseException e) {
                        Log.d("record3", e.toString());
                        e.printStackTrace();
                    }
                }
            }
            while (counttimes > 0){
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = format.parse(begindate);   //轉為時間格式
                    Calendar now = Calendar.getInstance();
                    now.setTime(date); //日期為2001/3/1
                    if (today == false){        //起始日不為今日，日期就不用先+1
                        today = true;           //日期開始+1
                    }
                    else {
                        now.add(Calendar.DAY_OF_YEAR, +1);  //起始日+1天
                        begindate=format.format(now.getTime());
                    }
                } catch (ParseException e) {
                    Log.d("record5", e.toString());
                    e.printStackTrace();
                }
                for (int i=0;i<dbtimes.size();i++){
                    if (counttimes > 0){
                        if (counttimes == 1){finish=true;}
                        Log.d("record5i", String.valueOf(dbtimes.size()));
                        Log.d("record5",dbtimes.get(i).get(1));
                        insertm_record(i,begindate,finish);
                        counttimes--;
                    }
                }
            }
        }
    }
        //   儲存record  /////////////////////
    public void insertm_record(final int i,final String date,final boolean finish){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, insertm_recordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnnmm",response);
                if (finish){
                    setAlarmtimes();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insertm_record.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("time_id",dbtimes.get(i).get(1));
                parameters.put("date", date);
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void setAlarmtimes(){        //設鬧鐘
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
                        String alarmtime=date+" "+time;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date todate = sdf.parse(alarmtime);
                        Long ldate = todate.getTime();
                        Log.d("timeeeee", String.valueOf(ldate));
                        final Intent my_intent=new Intent(ThirdActivity.this.context,Alarm_Receiver.class);
                        my_intent.putExtra("extra","alarm on");
                        my_intent.putExtra("alarmid",id);
                        my_intent.putExtra("mcalid",m_calendarid);
                        my_intent.putExtra("memberid",memberid);
                        pending_intent= PendingIntent.getBroadcast(ThirdActivity.this,Integer.parseInt(id),
                                my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                        alarm_manager.setExact(AlarmManager.RTC_WAKEUP, ldate,pending_intent);

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
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",m_calendarid);
                Log.d("timeeeee",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);

    }


    public void gotom_calendarlist(){
        Intent it = new Intent(ThirdActivity.this,m_calendarlist.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }



    public void checkinsert(View view){ //防呆檢查 ～顯示輸入錯誤提示
        boolean isVaild = true;
        m_cal_name1.setErrorEnabled(false);
        txtdate1.setErrorEnabled(false);
        txtday1.setErrorEnabled(false);
        time1.setErrorEnabled(false);

        if (m_cal_name.getText().toString().isEmpty()){
            m_cal_name.setError("Vaild Input Required");
            m_cal_name1.setError(Html.fromHtml("<font color='red'>Please Enter name</font>"));
            m_cal_name.setAnimation(animShake);
            m_cal_name1.setAnimation(animShake);
            vib.vibrate(120);
            isVaild=false;
        }else {
            m_cal_name1.setErrorEnabled(false);
        }
//        String[] s=txtdate.getText().toString().split("-");
//        int month=Integer.valueOf(s[1]);
//        int date=Integer.valueOf(s[2]);
//        if (month > 13 || date >32 || txtdate.getText().toString().isEmpty()){
        if (txtdate.getText().toString().equals("")){

            txtdate.setError("Vaild Input Required");
            txtdate1.setError(Html.fromHtml("<font color='red'>Date (YYYY-mm-dd) </font>"));
            txtdate.setAnimation(animShake);
            txtdate1.setAnimation(animShake);
            vib.vibrate(120);
            isVaild=false;
        }
        else {
            txtdate1.setErrorEnabled(false);
        }
        if (txtday.getText().toString().isEmpty()){
            txtday.setError("Vaild Input Required");
            txtday1.setError(Html.fromHtml("<font color='red'>Please Enter 天數</font>"));
            txtday.setAnimation(animShake);
            txtday1.setAnimation(animShake);
            vib.vibrate(120);
            isVaild=false;
        }
        else {
            txtday1.setErrorEnabled(false);
        }

        gettime(ThirdActivity.this);
        if (counttime==0){
            time1.setError(Html.fromHtml("<font color='red'>輸入至少一個時間</font>"));
            time1.setAnimation(animShake);
            vib.vibrate(120);
            isVaild=false;
        }
        else {
            time1.setErrorEnabled(false);
        }
        if (isVaild){
            insertm_calendar();
        }
    }




}
