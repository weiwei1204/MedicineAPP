package com.example.carrie.carrie_test1;

import android.content.Intent;
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
import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        googleid = bundle.getString("googleid");
        Log.d("GOOGLEID",googleid);
        nname = bundle.getString("name");
        gender = bundle.getString("gender_man");
        weight=bundle.getString("weight");
        height=bundle.getString("height");
        birth=bundle.getString("birth");
//        Log.d("GOOGLEID",nname);

//        Log.d("GOOGLEID",googleid);
//        name = bundle.getString("name");
//        gender=bundle.getString("gender_man");
//        weight=bundle.getString("weight");
//        height=bundle.getString("height");
//        birth=bundle.getString("birth");
        memberdata.setGoogle_id(googleid);

//        Log.d("GOOGLEID",name);
//        Log.d("GOOGLEID",gender);
//        Log.d("GOOGLEID",weight);
//        Log.d("GOOGLEID",height);
//        Log.d("GOOGLEID",birth);
        getMonitorId();
        getid();
        getpersonal();

        Log.d("UUIDnum123",Integer.toString(UUIDnum));
        Log.d("SSIDnum123",Integer.toString(SSIDnum));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
                        bundle0.putString("my_google_id", googleid);
                        bundle0.putString("my_supervise_id", my_mon_id);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(MainActivity.this, MonitorActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("my_id", memberid);
                        bundle1.putString("my_google_id", googleid);
                        bundle1.putString("my_supervise_id", my_mon_id);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", googleid);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(MainActivity.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", memberid);
                        bundle3.putString("my_google_id", googleid);
                        bundle3.putString("my_supervise_id", my_mon_id);
                        bundle3.putString("m_calid","-1");
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(MainActivity.this, MyBeaconActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("my_id", memberid);
                        bundle4.putString("my_google_id", googleid);
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
        Intent intent = new Intent(MainActivity.this,CheckBeacon.class);
        startService(intent);

    }

    private void signOut() {

        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
//        Intent it = new Intent(this, LoginActivity.class);
//        startActivity(it);

    }

    public void gotoFirstActivity(View v) { //連到MyBeacon頁面
        Intent it = new Intent(this, MyBeaconActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        Log.d("fffaaa", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoMonitorActivity(View v) {
        Intent it = new Intent(this, MonitorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("my_id", memberid);
        bundle.putString("my_google_id", googleid);
        bundle.putString("my_supervise_id", my_mon_id);
        it.putExtras(bundle);
        startActivity(it);
    }

    public void gotoChoice(View v) {  //連到排程選擇頁面
        Intent it = new Intent(this, Choice.class);
        Bundle bundle = new Bundle();
        bundle.putString("my_google_id", googleid);
        bundle.putString("my_supervise_id", my_mon_id);
        bundle.putString("memberid", memberid);
//        Log.d("fffaaa", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotodruginfo(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, druginfo.class);
        Bundle bundle3 = new Bundle();
        bundle3.putString("my_id", memberid);
        bundle3.putString("my_google_id", googleid);
        bundle3.putString("my_supervise_id", my_mon_id);
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
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoPersonalInformation() {
        Intent it = new Intent(this, PersonalInformationctivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        bundle.putString("memberid", memberid);
        bundle.putString("name", nname);
        bundle.putString("gender_man",gender );
        bundle.putString("weight",weight);
        bundle.putString("height", height);
        bundle.putString("birth", birth);
        bundle.putString("name", repairData.getName());
        bundle.putString("gender_man",repairData.getGender_man() );
        bundle.putString("weight",repairData.getWeight());
        bundle.putString("height", repairData.getHeight());
        bundle.putString("birth", repairData.getBirth());


        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoBsBpMeasure() {
        Intent it = new Intent(this, BsBpMeasureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        it.putExtra("bsBpMeasureObject", bsBpMeasureObject);
        startActivity(it);
    }
    public void gotoMyAP() {
        Intent it = new Intent(this,MyAPActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
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
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, getidUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rrr123", response);
                memberid = response;
                memberdata.setMember_id(response);
                getMeasureInformation();
                getAP();
                getbeacon();
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
                parameters.put("google_id", googleid);
                Log.d("my123", parameters.toString());
                Log.d("my123", "checck!!!");
                return parameters;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getidUrl, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray members = response.getJSONArray("Members");
//                    final String[] memberarray=new String[members.length()];
//
//                    for (int i=0 ; i<members.length() ; i++){
//                        JSONObject member = members.getJSONObject(i);
//                        String id = member.getString("id");
//                        memberarray[i] = id;
//                        Log.d("vvvvv",memberarray[i]);
//                    }
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {}
//        });
//        requestQueue.add(jsonObjectRequest);

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
                parameters.put("google_id_mymonitor", googleid);
                Log.d("google_id_monitor", parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void getMeasureInformation() {//取得血壓血糖測量時間
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getMeasureInformationURL = "http://54.65.194.253/Member/getMeasureInformation.php?member_id=" + memberid;
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

    public void getpersonal() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Member/personal.php?google_id=" + googleid;
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

                        repairData = new RepairData(object.getString("name"), object.getString("email")
                                , object.getString("gender_man"), object.getString("weight"), object.getString("height")
                                , object.getString("birth"), object.getString("google_id"));

                        Log.d("ppppp", repairData.getGender_man());

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

                Bundle bundle1 = new Bundle();

                String p1= getIntent().getExtras().getString("name", "not found");
                bundle1.putString("name", repairData.getName());
                repairData.setName(p1);


                String p2;
                p2=repairData.getGender_man();
                bundle1.putString("gender_man", repairData.getGender_man());
                repairData.setGender_man(p2);

                String p3;
                p3=repairData.getWeight();
                bundle1.putString("weight", repairData.getWeight());
                repairData.setWeight(p3);

                String p4;
                p4=repairData.getHeight();
                bundle1.putString("height", repairData.getHeight());
                repairData.setHeight(p4);

                String p5;
                p5=repairData.getBirth();
                bundle1.putString("birth", repairData.getBirth());
                repairData.setBirth(p5);

                Log.d("ppppp",p1);
                Log.d("ppppp", p2);
                Log.d("ppppp",p3);
                Log.d("ppppp",p4);
                Log.d("ppppp",p5);

//                Bundle bundle1 = new Bundle();
//
//                String p1= getIntent().getExtras().getString("name", "not found");
//                bundle1.putString("name", repairData.getName());
//                repairData.setName(p1);
//
//                String p2;
//                p2=repairData.getGender_man();
//                bundle1.putString("gender_man", repairData.getGender_man());
//                repairData.setGender_man(p2);
//
//                String p3;
//                p3=repairData.getWeight();
//                bundle1.putString("weight", repairData.getWeight());
//                repairData.setWeight(p3);
//
//                String p4;
//                p4=repairData.getHeight();
//                bundle1.putString("height", repairData.getHeight());
//                repairData.setHeight(p4);
//
//                String p5;
//                p5=repairData.getBirth();
//                bundle1.putString("birth", repairData.getBirth());
//                repairData.setBirth(p5);
//
//                Log.d("ppppp",p1);
//                Log.d("ppppp", p2);
//                Log.d("ppppp",p3);
//                Log.d("ppppp",p4);
//                Log.d("ppppp",p5);



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
                        .addFormDataPart("memberid", my_mon_id)
                        .addFormDataPart("token", token)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://54.65.194.253/Monitor/sendToken.php?memberid=" + my_mon_id + "&token=" + token)
                        .post(formBody)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("mon_idte213st", "http://54.65.194.253/Monitor/sendToken.php?memberid=" + my_mon_id + "&token=" + token);

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


}