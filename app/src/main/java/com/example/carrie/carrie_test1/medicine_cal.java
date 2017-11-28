package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class medicine_cal extends AppCompatActivity {
    private EditText m_cal_nameid,drugnameid;
    private TextView txtdateid,txtdayid;

    RequestQueue requestQueue;
    private String getm_calendarUrl = "http://54.65.194.253/Medicine_Calendar/getm_calendar.php";
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String deletem_caledarUrl = "http://54.65.194.253/Medicine_Calendar/deletem_calendar.php";
    private String getm_caltimesUrl = "http://54.65.194.253/Medicine_Calendar/getm_caltimes.php";
    private String getm_caldrugUrl = "http://54.65.194.253/Medicine_Calendar/getm_caldrug.php";
    private String deletem_drugUrl = "http://54.65.194.253/Medicine_Calendar/deletem_drug.php";
    private String updatem_calnameUrl = "http://54.65.194.253/Medicine_Calendar/updatem_calname.php";
    private String insertmcaldrugsUrl = "http://54.65.194.253/Drug/insertmcaldrugs.php";
    private String m_calid,memberid,beaconUUID,beaconid,drugid,drugname;
    private Spinner spinnerbeaconid;
    private ArrayAdapter<CharSequence> adapterbeacon;
    private ArrayList<ArrayList<String>> bconarray = new ArrayList<ArrayList<String>>();
    int calbid,calbeacon;
    private Button m_delete,m_modify;
    private ArrayList<ArrayList<String>> dbmdrugs = new ArrayList<ArrayList<String>>();



    private Vibrator vib;
    Animation animShake;
    private TextInputLayout drugnameid1;
    int entertype,checkbtn;
    int counttime;
    private ArrayList<ArrayList<String>> mtimes = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> mdrugs = new ArrayList<ArrayList<String>>();
    private ImageButton modifydrug,copybtn;
    String drug0,drug1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_cal);
        m_cal_nameid = (EditText)findViewById(R.id.m_cal_nameid);
        txtdateid = (TextView) findViewById(R.id.txtdateid);
        txtdayid = (TextView)findViewById(R.id.txtdayid);
        spinnerbeaconid = (Spinner)findViewById(R.id.spinnerbeaconid);
        m_delete = (Button)findViewById(R.id.m_delete);
        m_modify = (Button)findViewById(R.id.m_modify);
        copybtn = (ImageButton)findViewById(R.id.copybtn);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        modifydrug = (ImageButton)findViewById(R.id.modifydrug);
        Bundle bundle2 = getIntent().getExtras();
        entertype= bundle2.getInt("entertype");
        m_calid=bundle2.getString("m_calid");
        memberid=bundle2.getString("memberid");

        memberid=memberdata.getMember_id();
        Bundle bundle1 = getIntent().getExtras();
        entertype=bundle1.getInt("entertype");
        drugid=bundle1.getString("drugid");
        drugname=bundle1.getString("chineseName",drugname);
        m_calid=bundle2.getString("m_calid");

        if (entertype == 0){
            getmcaldrugs();
            Log.d("druggg","9");
        }
        else {
            Log.d("druggg","555");
            drug(medicine_cal.this,modifydrug);

        }
        checkDialog();
        gettime();
//        Bundle bundle = getIntent().getExtras();
//        m_calid=bundle.getString("m_calid");


        m_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmcaldrugs();//取資料庫已有的drugs
                entertype=-3;
            }
        });


        final Intent it = new Intent(this,ThirdActivity.class);
        Bundle bundle3 = new Bundle();
        bundle3.putString("memberid", memberid);
        bundle3.putInt("entertype", 1);
        bundle3.putInt("copy", 1);
        it.putExtras(bundle3);   // 記得put進去，不然資料不會帶過去哦
        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mdrugs.clear();
//                getdrugs(medicine_cal.this);
                mcaldata.setMcalname(m_cal_nameid.getText().toString());
                mcaldata.setMemberid(memberdata.getMember_id());
                mcaldata.setMcaldate(txtdateid.getText().toString());
                mcaldata.setMcalday(txtdayid.getText().toString());
                mcaldata.setMcaltimes(mtimes);
                mcaldata.setMcaldrugs(dbmdrugs);
                startActivity(it);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, getm_calendarUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("nn11",response.toString());
                    JSONArray jarray = new JSONArray(response);
                    for (int i=0 ; i<jarray.length() ; i++){
                        JSONObject mcalendar = jarray.getJSONObject(i);
                        Log.d("nnnmmmmo",mcalendar.toString());
                        String id = mcalendar.getString("id");
                        String name = mcalendar.getString("name");
                        String startDate = mcalendar.getString("startDate");
                        String day = mcalendar.getString("day");
                        String beacon_id =mcalendar.getString("beacon_id");
                        if (id.equals(m_calid)){
                            m_cal_nameid.setText(name);
                            txtdateid.setText(startDate);
                            txtdayid.setText(day);
                            memberid = mcalendar.getString("member_id");
                            calbid = Integer.parseInt(beacon_id);
                        };
                        getBeacon();
                    }//取值結束
                }catch (JSONException e){
                    e.printStackTrace();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }}){
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn11",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void getBeacon(){//取此會員的beacon
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, getBeaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("nn11",response.toString());
                    JSONArray jarray = new JSONArray(response);
                    final String[] beaconarray=new String[jarray.length()];
                    final String[] beaconaidrray=new String[jarray.length()];
                    bconarray.clear();
                    for (int i=0 ; i<jarray.length() ; i++){
                        JSONObject beacon = jarray.getJSONObject(i);
                        String UUID = beacon.getString("UUID");
                        String Member_id = beacon.getString("member_id");
                        String id = beacon.getString("id");
                        String name = beacon.getString("newname");
                        beaconaidrray[i] = id;
                        beaconarray[i] = UUID;
                        bconarray.add(new ArrayList<String>());
                        bconarray.get(i).add(name);
                        bconarray.get(i).add(id);

                        if (calbid==Integer.parseInt(id)){
                                calbeacon =i;
                            }
                    }//取值結束
                    Log.d("bbbbbbcon", String.valueOf(bconarray.size()));
                    myspinner myspinner = new myspinner(medicine_cal.this,R.layout.myspinner,R.id.mysipnner,bconarray);
                    spinnerbeaconid.setAdapter(myspinner);
                    spinnerbeaconid.setSelection(calbeacon,false);
                    spinnerbeaconid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            beaconid=bconarray.get(position).get(1);
                            Log.d("bbbbbbcon", beaconid);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}//~~~~
                    });}catch (JSONException e){
                    e.printStackTrace();
                }}}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn11",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }


    public void getmcaldrugs(){ //資料庫存的藥品
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_caldrugUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                try {
                    dbmdrugs.clear();
                    JSONArray jarray = new JSONArray(response);
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String chineseName = obj.getString("chineseName");
                        String id = obj.getString("id");
                        dbmdrugs.add(new ArrayList<String>());//存入另一個class在跳頁
                        dbmdrugs.get(i).add(chineseName);
                        dbmdrugs.get(i).add(id);

                        Log.d("drugggg111",dbmdrugs.get(i).get(0));

                    }
                    if(entertype == -3){
                        check_dbmdrug();
                    }
                    else {
                        drug(medicine_cal.this,modifydrug);

                    }
                } catch (JSONException e) {
                    Log.d("nnn",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnn", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",m_calid);
                Log.d("nnnmcalid",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);



    }

    public void gettime(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_caltimesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nnn",response);
                mtimes.clear();
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] timearray=new String[jarray.length()];
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String time = obj.getString("time");
                        String TimeInMillis = obj.getString("TimeInMillis");

                        mtimes.add(new ArrayList<String>());
                        mtimes.get(i).add(time);
                        timetonow(i,time);
                        add(medicine_cal.this,time,TimeInMillis);       //呼叫add()
                    }
                } catch (JSONException e) {
                    Log.d("nnn",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnn", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_calendarid.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",m_calid);
                Log.d("nn11",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }


    public void timetonow(int i,String time){ //為了複製排成先算出現在時間點
        Long mmdate = null;
//        Calendar c=Calendar.getInstance();
//        int year=c.get(Calendar.YEAR);
//        int month=c.get(Calendar.MONTH);
//        int day=c.get(Calendar.DAY_OF_MONTH);
//        final Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day, Integer.valueOf(TIM[0]), Integer.valueOf(TIM[1]),0);
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new  Date();
        String ymds=ymd.format(dt);
        String date = ymds+" "+time;
        try {
            Date ddate = sdf.parse(date);
            mmdate = ddate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mtimes.get(i).add(String.valueOf(mmdate));
        Log.d("nowwwwwww",mtimes.get(i).get(1));
    }

    public void add(final Activity activity , String time , String TimeInMillis) //資料庫time顯示
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.timelinearLayout);
        final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.medicine_calrow, null);
        newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView timetxt= (TextView) newView.findViewById(R.id.time);
        final TextView timeinmillstxt=(TextView)newView.findViewById(R.id.timeinmills);
        timetxt.setText(time);
        timeinmillstxt.setText(TimeInMillis);
        Log.d("fff", String.valueOf(timetxt));
        linearLayoutForm.addView(newView);
    }


    private void checkDialog() {
        m_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(medicine_cal.this)
                        .setTitle("刪除用藥排程")
                        .setMessage("是否刪除?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //按下按鈕後執行的動作，沒寫則退出Dialog
                                        m_delete();
                                    }
                                }
                        )
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //按下按鈕後執行的動作，沒寫則退出Dialog
                                    }
                                }
                        )
                        .show();
            }
        });


    }


    public void m_delete(){     //刪除用藥排程
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, deletem_caledarUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DeviceData.getBeacon(getApplicationContext());
                DeviceData.getAP(getApplicationContext());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read deletem_caledar.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id",m_calid);
                Log.d("nnnaaa",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(medicine_cal.this);
        requestQueue.add(request);
        Intent it = new Intent(medicine_cal.this,m_calendarlist.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);

    }


    public void drug(final Activity activity, ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.newdrugcal);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                getdrugs(medicine_cal.this);
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.m_search, null);
                newView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                final TextView drugtxt= (TextView) newView.findViewById(R.id.drugtxt);
                final TextView drugidtxt=(TextView)newView.findViewById(R.id.drugid);
                if (entertype == 1 && checkbtn !=-1 && checkbtn !=-2){       //塞值
                    Log.d("druggg","111");
                    drugtxt.setText(drug0);
                    drugidtxt.setText(drug1);
//                    Log.d("drug222",mdrugs.get(checkbtn).get(1));
                }
                else if (checkbtn == -2){//新增剛加入的藥品
                    Log.d("druggg","6");
                    drugtxt.setText(drugname);
                    drugidtxt.setText(drugid);
                    Log.d("drug22222",drugid);
                    checkbtn=-1;
                }
                else if(entertype == 0){
                    Log.d("drugggggg",dbmdrugs.get(checkbtn).get(0));
                    Log.d("drugggggg", String.valueOf(checkbtn));

                    drugtxt.setText(dbmdrugs.get(checkbtn).get(0));
                    drugidtxt.setText(dbmdrugs.get(checkbtn).get(1));
                    entertype = -1;
                }
                else {
                    store();
                }
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                        String drugid=drugidtxt.getText().toString();
                        deletdrug(drugid);  //刪除drug;
                    }
                });
                linearLayoutForm.addView(newView);

            }
        });
        if(entertype == 0){ //第一次抓資料庫的藥品
            checkbtn=0;
            Log.d("druggg","8");
            int size=dbmdrugs.size();
            for (int i=0;i<size;i++){
                entertype = 0;
                checkbtn=i;
                modifydrug.performClick();
            }


        }
        if(entertype == 1){
            Log.d("druggg","7");
            checkbtn=0;     //          判斷是手動觸發adddrug
            mdrugs=mcaldata.getMcaldrugs();
            int size=mdrugs.size();
            Log.d("drugarray1",String.valueOf(size));
            for (int i=0;i<size;i++){
                Log.d("drugaaaccc",String.valueOf(i));
                Log.d("druggg",mdrugs.get(i).get(0));
                if (!mdrugs.get(i).get(0).equals("")){
                    checkbtn=i;
                    drug0=mdrugs.get(checkbtn).get(0);
                    drug1=mdrugs.get(checkbtn).get(1);
                    Log.d("druggg",mdrugs.get(checkbtn).get(0));
                    Log.d("druggg",mdrugs.get(checkbtn).get(1));
                    modifydrug.performClick();
                }
            }
            checkbtn=-2;//最新的藥
            modifydrug.performClick();

            checkbtn=-1;
        }
    }

    public void  store(){
        mdrugs.clear();
        getdrugs(medicine_cal.this);
        mcaldata.setMcaldrugs(mdrugs);
        Intent it = new Intent(medicine_cal.this,druginfo.class);
        Log.d("qqqqq111",m_calid);
        Bundle bundle3 = new Bundle();
        bundle3.putString("my_id", "0");
        bundle3.putString("my_google_id", "0");
        bundle3.putString("my_supervise_id", "0");
        bundle3.putString("m_calid",m_calid);
        it.putExtras(bundle3);
        startActivity(it);
//                    mdrugs=mcaldata.getMcaldrugs();
//                    Log.d("drug", String.valueOf(mdrugs.size()));

    }

    public  void getdrugs(final Activity activity)//取吃藥次數
    {
        counttime=0;
        LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.newdrugcal);
        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
        {
            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
            TextView txt = (TextView) innerLayout.findViewById(R.id.drugtxt);
            TextView id = (TextView) innerLayout.findViewById(R.id.drugid);
            if (txt.getText().toString().equals("")){
            }else {
                mdrugs.add(new ArrayList<String>());//存入另一個class在跳頁
                Log.d("druggggggg",txt.getText().toString());
                mdrugs.get(counttime).add(txt.getText().toString());
                mdrugs.get(counttime).add(id.getText().toString());
                counttime++;
            }

        }
    }
    public void deletdrug(final String drugid){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, deletem_drugUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnn", error.toString());
                Toast.makeText(getApplicationContext(), "Error read deletem_drug.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",m_calid);
                parameters.put("drug_id",drugid);
                Log.d("nn11",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }


    public void update_mcalname(){ //更新m_calendar的名稱
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, updatem_calnameUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DeviceData.getBeacon(getApplicationContext());
                DeviceData.getAP(getApplicationContext());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnn", error.toString());
                Toast.makeText(getApplicationContext(), "Error read deletem_drug.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("mcalid",m_calid);
                parameters.put("name",m_cal_nameid.getText().toString());
                Log.d("nn11",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }

    public void check_dbmdrug(){ //確認藥品是否已存在m_cal的藥品資料庫,如不存在就新增
        mdrugs.clear();
        getdrugs(medicine_cal.this);
        update_mcalname();
        for (int i=0;i<mdrugs.size();i++){
            boolean stored=false;
            Log.d("drug1111", String.valueOf(dbmdrugs.size()));
            for (int j=0;j<dbmdrugs.size();j++){
                if (mdrugs.get(i).get(1).equals(dbmdrugs.get(j).get(1))){
                    Log.d("drug1111",mdrugs.get(i).get(1));
                    Log.d("drug1111",dbmdrugs.get(j).get(1));
                    stored=true;
                }
            }
            if (stored == false){
                update_mdrug(i);
            }
        }
        Intent it = new Intent(this,m_calendarlist.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void update_mdrug(final int i){  //新增m_calendar的藥品
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, insertmcaldrugsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnn", error.toString());
                Toast.makeText(getApplicationContext(), "Error read deletem_drug.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Medicine_Calendar_id",m_calid);
                parameters.put("Drug_id",mdrugs.get(i).get(1));
                Log.d("nn11",parameters.toString());
                return parameters;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }

    private void requestFocus(View view){
        if (view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
