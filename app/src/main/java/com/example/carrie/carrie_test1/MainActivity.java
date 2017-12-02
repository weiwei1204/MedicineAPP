package com.example.carrie.carrie_test1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class MainActivity extends LoginActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton SignOut;
    ImageButton robot;
    private Intent serviceIntent;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String googleid;
    String getidUrl = "http://54.65.194.253/Member/getid.php";
    private Button nav_gallery;
    SharedPreferences sharedPref;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    String memberid;
    String my_mon_id;
    BsBpMeasureObject bsBpMeasureObject;
    RepairData repairData;
    String nname;
    String gender;
    String weight;
    String height;
    String birth;
    private ArrayList<String> needBeacon = new ArrayList<String>();
    private ArrayList<String> Beaconcal = new ArrayList<String>();
    private ArrayList<String> storeAPBSSID = new ArrayList<String>();
    private int UUIDnum = 0 ;
    private int SSIDnum = 0 ;
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";
    String getAPUrl = "http://54.65.194.253/Beacon/getAP.php";
    SQLiteDatabase sqLiteDatabase;
    public static final String DATABASE_NAME = "MedicineTest.db";
    public static final String TABLE_NAME = "Member";
    Button mfakebtn,hfakebtn;
    PendingIntent pending_intent;
    AlarmManager alarm_manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        mfakebtn = (Button)findViewById(R.id.mfakebtn);
        hfakebtn = (Button)findViewById(R.id.hfakebtn);
        Bundle bundle = getIntent().getExtras();
        googleid = bundle.getString("googleid");
        Log.d("GOOGLEID",googleid);
        nname = bundle.getString("name");
        gender = bundle.getString("gender_man");
        weight=bundle.getString("weight");
        height=bundle.getString("height");
        birth=bundle.getString("birth");


//        Log.d("GOOGLEID",nname);


        //int googleid = Log.d("GOOGLEID", nname);

//        Log.d("GOOGLEID",nname);
//        Log.d("GOOGLEID",googleid);
//        name = bundle.getString("name");
//        gender=bundle.getString("gender_man");
//        weight=bundle.getString("weight");
//        height=bundle.getString("height");
//        birth=bundle.getString("birth");

        memberdata.setGoogle_id(this.googleid);

//        Log.d("GOOGLEID",name);
//        Log.d("GOOGLEID",gender);
//        Log.d("GOOGLEID",weight);
//        Log.d("GOOGLEID",height);
//        Log.d("GOOGLEID",birth);
        getMonitorId();
        getid();
        getpersonal_sql();
        //getpersonal();

        Log.d("UUIDnum123",Integer.toString(UUIDnum));
        Log.d("SSIDnum123",Integer.toString(SSIDnum));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Log.d("iddddd: ",memberdata.getMember_id());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView memberName = (TextView) hView.findViewById(R.id.namee);
        Log.d("nameeee: ",memberdata.getName());
        memberName.setText(memberdata.getName());
        TextView memberEmail = (TextView) hView.findViewById(R.id.emaill);
        memberEmail.setText(memberdata.getEmail());
        navigationView.setNavigationItemSelectedListener(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_list:
                        Intent intent0 = new Intent(MainActivity.this, Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("memberid", memberid);
                        bundle0.putString("my_google_id", MainActivity.this.googleid);
                        bundle0.putString("my_supervise_id", my_mon_id);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        if(isNetworkAvailable()){
                            Intent intent1 = new Intent(MainActivity.this, MonitorActivity.class);
                            Bundle bundle1 = new Bundle();
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }else {
                            networkCheck();
                        }

                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", MainActivity.this.googleid);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(MainActivity.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", memberid);
                        bundle3.putString("my_google_id", MainActivity.this.googleid);
                        bundle3.putString("my_supervise_id", my_mon_id);
                        bundle3.putString("m_calid","-1");
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(MainActivity.this, MyBeaconActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("my_id", memberid);
                        bundle4.putString("my_google_id", MainActivity.this.googleid);
                        bundle4.putString("my_supervise_id", my_mon_id);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

        robot = (ImageButton) findViewById(R.id.robotbtn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent pintent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(pintent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        }

        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceIntent = new Intent(MainActivity.this, ChatHeadService.class);
                startService(serviceIntent);
                Log.d("8989", "clicked");
            }
        });


        memberdata.setNeedBeacon(needBeacon);
        memberdata.setBeaconcal(Beaconcal);
        UUIDnum = needBeacon.size();
        SSIDnum = storeAPBSSID.size();
//        Intent checkBeaconIntent = new Intent(MainActivity.this,CheckBeacon_AP.class);
//        startService(checkBeaconIntent);

        //用藥排程提醒假按鈕
        mfakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                long now = calendar.getTimeInMillis();
                final Intent my_intent=new Intent(MainActivity.this,Alarm_Receiver.class);
                my_intent.putExtra("extra","alarm on");
                my_intent.putExtra("alarmid","0");
                my_intent.putExtra("mcalid","513");
                my_intent.putExtra("memberid",memberid);
                my_intent.putExtra("alarmtype","medicine");
                pending_intent= PendingIntent.getBroadcast(MainActivity.this,0,
                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, now ,pending_intent);
            }
        });

        //健康排成提醒假按鈕（血糖）
        hfakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                long now = calendar.getTimeInMillis();
                final Intent my_intent=new Intent(MainActivity.this,Alarm_Receiver.class);
                my_intent.putExtra("extra","alarm on");
                my_intent.putExtra("alarmid","331");
                my_intent.putExtra("memberid",memberid);
                my_intent.putExtra("alarmtype","healthbs");
                pending_intent= PendingIntent.getBroadcast(MainActivity.this,0,
                        my_intent,PendingIntent.FLAG_CANCEL_CURRENT);
                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, now,pending_intent);
            }
        });
    }
    private void signOut() {

        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
//        sharedPref= getApplication().getSharedPreferences("data",MODE_PRIVATE);
//        //number = sharedPref.getInt("isLogged", 0);
//        SharedPreferences.Editor prefEditor = sharedPref.edit();
//        prefEditor.putInt("isLogged",0);
//
//        prefEditor.commit();
        this.deleteDatabase(DATABASE_NAME);
        Intent it = new Intent(this, Main2.class);
        startActivity(it);

    }

    public void gotoFirstActivity(View v) { //連到MyBeacon頁面
        Intent it = new Intent(this, MyBeaconActivity.class);
        Bundle bundle = new Bundle();
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoMonitorActivity(View v) {
        if(isNetworkAvailable()){
            Intent it = new Intent(this, MonitorActivity.class);
            Bundle bundle = new Bundle();
            it.putExtras(bundle);
            startActivity(it);
        }
        else {
            networkCheck();
        }
    }

    public void gotoChoice(View v) {  //連到排程選擇頁面
        Intent it = new Intent(this, Choice.class);
        Bundle bundle = new Bundle();
//        Log.d("fffaaa", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotodruginfo(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, druginfo.class);
        Bundle bundle3 = new Bundle();
        bundle3.putString("m_calid","-1");
        it.putExtras(bundle3);
        startActivity(it);
    }

    public void gotoFirstctivity(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, DrugListActivity.class);
        startActivity(it);
    }

    public void gotoFourthActivity(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, DrugListActivity.class);
        startActivity(it);
    }

    public void gotoGenerate_Qrcode() { //連到製造qrcode頁面
        Intent it = new Intent(this, Generate_Qrcode.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", memberdata.getGoogle_id());
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoPersonalInformation() {
        Intent it = new Intent(this, PersonalInformationctivity.class);

        startActivity(it);
    }

    public void gotoBsBpMeasure() {
        Intent it = new Intent(this, BsBpMeasureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", memberdata.getGoogle_id());
        bundle.putString("memberid", memberdata.getMember_id());
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        it.putExtra("bsBpMeasureObject", memberdata.getMember_id());
        startActivity(it);
    }
    public void gotoMyAP() {
        Intent it = new Intent(this,MyAPActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberdata.getMember_id());
        Log.d("fffaaa","*"+memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void gotoBeaconMode() {
        Intent it = new Intent(this,BeaconModeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberdata.getMember_id());
        Log.d("fffaaa","*"+memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
//    public void gotoLoginActivity(View v) { //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,LoginActivity.class);
//        Log.d("hh","4");
//        LoginActivity la=new LoginActivity();
//        Log.d("hh","3");
//        la.signoutbtn="1";
////        la.signOut();
//        Log.d("hh","1");
//        startActivity(it);

    //   }

    public void getid() {
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        Cursor c  = sqLiteDatabase.rawQuery("SELECT * FROM Member",null);

        if(c.getCount()!=0){
            if(c.moveToFirst()){
                do{
                    if (c.getString(0).equals("0")){
                        updateId();

                    }
//                    memberdata.setMember_id(memberid);
//                    Log.d("idFormember",memberid);
                    memberdata.setName(c.getString(1));
                    memberdata.setEmail(c.getString(2));
                    getMeasureInformationsql();
                    getAP();
                    getbeacon();
                }while (c.moveToNext());

            }
        }else {
//            getMeasureInformation();
            getAP();
            getbeacon();
        }

    }


    public void getMonitorId() {//取得監控者的id
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getMonitorIdUrl = "http://54.65.194.253/Monitor/getMonitorId.php";
        final StringRequest request = new StringRequest(Request.Method.POST, getMonitorIdUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("rrr", "1");
//                Log.d("rrr", response);
                Log.d("my_mon_id", response);

                if (response.contains("nodata")) {
                    Log.d("monitorId_check", "success");
                    //normalDialogEvent();

                } else {
                    //Log.d("monitor_response",response);
                    my_mon_id = response;
                    Log.d("my_mon_id222", my_mon_id);
                    memberdata.setMy_mon_id(response);
                    //addMonitor();//新增監控者至監視列表
                    sendToken();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getMonitorId.php!!!", Toast.LENGTH_LONG).show();
//                refreshNormalDialogEvent();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("username", gname);
//                parameters.put("password", gemail);
                parameters.put("google_id_mymonitor", memberdata.getGoogle_id());
                Log.d("google_id_monitor", parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void getMeasureInformation() {//取得血壓血糖測量時間
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getMeasureInformationURL = "http://54.65.194.253/Member/getMeasureInformation.php?member_id=" + memberdata.getMember_id();
        Map<String, String> params = new HashMap();

        //params.put("member_id", memberid);
        //Log.d("measureInfor",params.toString());
        //JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getMeasureInformationURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("measureInfor", response.toString());

                if (response.toString().contains("null")) {
                    Log.d("measureInfor", "nodata");
                } else {
                    Log.d("measureInfor", "havedata");
                    try {
                        memberdata.measure_id = response.getInt("id");
                        memberdata.bp_first=getCurrentTimeStamp(response.getString("bp_first"));
                        memberdata.bp_second=getCurrentTimeStamp(response.getString("bp_second"));
                        memberdata.bp_third=getCurrentTimeStamp(response.getString("bp_third"));
                        memberdata.bs_first=getCurrentTimeStamp(response.getString("bs_first"));
                        memberdata.bs_second=getCurrentTimeStamp(response.getString("bs_second"));
                        memberdata.bs_third=getCurrentTimeStamp(response.getString("bs_third"));

                        bsBpMeasureObject = new BsBpMeasureObject(response.getInt("id"), response.getString("member_id"), response.getString("bs_first"), response.getString("bs_second"), response.getString("bs_third"), response.getString("bp_first"), response.getString("bp_second"), response.getString("bp_third"));
                        Log.d("measureInfor", "object " + bsBpMeasureObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("measureInfor", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getMeasureInformation.php!!!", Toast.LENGTH_LONG).show();
//                refreshNormalDialogEvent();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    public void getpersonal_sql(){
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        Cursor c  = sqLiteDatabase.rawQuery("SELECT * FROM Member",null);

        if(c.moveToFirst()){
            do{
                RepairData.google_id = memberdata.getMember_id();
                RepairData.name =  memberdata.getName();
                RepairData.email = memberdata.getEmail();
                RepairData.gender_man = c.getString(3);
                RepairData.weight = c.getString(4);
                RepairData.height = c.getString(5);
                RepairData.birth = c.getString(6);

            }while (c.moveToNext());

        }
    }
        public void getMeasureInformationsql(){
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_APPEND,null);
        Cursor c1  = sqLiteDatabase.rawQuery("SELECT * FROM Health_BsBpMeasureTime",null);

        if(c1.moveToFirst()){

            do{
                memberdata.measure_id = Integer.parseInt(c1.getString(0));
                try {
                    memberdata.bs_first=getCurrentTimeStamp(c1.getString(2));
                    memberdata.bs_second=getCurrentTimeStamp(c1.getString(3));
                    memberdata.bs_third=getCurrentTimeStamp(c1.getString(4));
                    memberdata.bp_first=getCurrentTimeStamp(c1.getString(5));
                    memberdata.bp_second=getCurrentTimeStamp(c1.getString(6));
                    memberdata.bp_third=getCurrentTimeStamp(c1.getString(7));
                    bsBpMeasureObject = new BsBpMeasureObject(Integer.parseInt(c1.getString(0)), memberdata.getMember_id(), c1.getString(2), c1.getString(3), c1.getString(4), c1.getString(5), c1.getString(6), c1.getString(7));
                    Log.d("measureInfor", "object " + bsBpMeasureObject.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }while (c1.moveToNext());


        }
    }
    public void getpersonal() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Member/personal.php?google_id=" + memberdata.getGoogle_id();
                OkHttpClient client = new OkHttpClient();
                Log.d("ppppp", insertUrl);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(insertUrl)
                        .build();
                Log.d("ppppp", "2222");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("ppppp", "1111");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("ppppp", array.toString());
                    JSONObject object = array.getJSONObject(0);
                    Log.d("ppppp", "16666");

                    if ((object.getString("id")).equals("nodata")) {
                        Log.d("okht2tp", "4442222");
                        //normalDialogEvent();
                    } else {

                    }


                    Log.d("searchtest", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        object = array.getJSONObject(i);
                        memberdata.setMember_id(object.getString("id"));
                        memberdata.setGoogle_id(object.getString("google_id"));
                        memberdata.setEmail(object.getString("email"));
                        addData(object.getString("id"),object.getString("name"), object.getString("email")
                                , object.getString("gender_man"), object.getString("weight"), object.getString("height")
                                , object.getString("birth"), object.getString("google_id"),object.getString("photo"));


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {



            }
        };
        task.execute();

    }






    public void goback(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            gotoGenerate_Qrcode();
        } else if (id == R.id.nav_gallery) {
            gotoPersonalInformation();
        } else if (id == R.id.nav_slideshow) {
            gotoBsBpMeasure();
        } else if (id == R.id.nav_manage) {
            gotoMyAP();
        } else if (id == R.id.nav_manage2) {
            gotoBeaconMode();
        } else if (id == R.id.nav_share) {
            signOut();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void getbeacon(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_BeaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nn1111",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    UUIDnum = jarray.length() ;
                    needBeacon.clear();
                    Beaconcal.clear();
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String UUID = obj.getString("UUID");
                        String name = obj.getString("name");
                        needBeacon.add(i,UUID);
                        Beaconcal.add(i,name);
                        Log.d("needBeacon",needBeacon.get(i));
                    }
                    memberdata.setNeedBeacon(needBeacon);
                    memberdata.setBeaconcal(Beaconcal);
                    UUIDnum = needBeacon.size();
                    SSIDnum = storeAPBSSID.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error read getm_Beacon.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1111",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }
    public void getAP(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getAPUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("nn1122",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] SSIDarray=new String[jarray.length()];
                    final String[] BSSIDarray=new String[jarray.length()];
                    storeAPBSSID.clear();
                    SSIDnum = jarray.length();
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String SSID = obj.getString("SSID");
                        String BSSID = obj.getString("BSSID");
                        SSIDarray[i]=SSID;
                        storeAPBSSID.add(i,BSSID);
                        Log.d("nn11",BSSID);
                    }
                    memberdata.setStoreAPBSSID(storeAPBSSID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error read getm_AP.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1122",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }
    public void sendToken(){
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("2233","Token: "+token);
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("memberid", memberdata.getMy_mon_id())
                        .addFormDataPart("token", token)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://54.65.194.253/Monitor/sendToken.php?memberid=" + memberdata.getMy_mon_id() + "&token=" + token)
                        .post(formBody)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("mon_idte213st", "http://54.65.194.253/Monitor/sendToken.php?memberid=" + memberdata.getMy_mon_id() + "&token=" + token);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        };
        task.execute();
    }
    public static String getCurrentTimeStamp(String dateString) throws ParseException {//時間格式轉換
        String strDate = "";
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");//format yyyy-MM-dd HH:mm:ss to HH:mm
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar();

        Date date = sdf.parse(dateString);
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        if (month==1){
            strDate ="";
        }else {
            strDate = sdfDate.format(date);
        }
        return strDate;
    }
    public void updateIdSql(String a ){
        ContentValues contentValues = new ContentValues(4);
        contentValues.put("id",a);
        //sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "google_id =" + memberdata.getGoogle_id(), null);

    }
    public  boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public  void networkCheck() {
        new AlertDialog.Builder(this)
                .setMessage("請確認網路連線")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }
    public void  updateId(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, getidUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rrr123", response);
                memberid = response;
                updateIdSql(response);
                memberdata.setMember_id(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr111", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("google_id", memberdata.getGoogle_id());
                Log.d("my123", parameters.toString());
                Log.d("my123", "checck!!!");
                return parameters;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



}
