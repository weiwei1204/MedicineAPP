package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends LoginActivity {

    private ImageButton SignOut;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String googleid;
    String getidUrl = "http://54.65.194.253/Member/getid.php";
    RequestQueue requestQueue;
    String memberid;
    String my_mon_id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");

        getMonitorId();
        getid();
//        SignOut = (ImageButton) findViewById(R.id.bkimg);
//        SignOut.setOnClickListener(this);
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.mainspinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(0,false);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
            if (parent.getItemAtPosition(position).equals("QR_Code")){
                gotoGenerate_Qrcode();
            }
            else if (parent.getItemAtPosition(position).equals("SignOut")){
                signOut();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_list:
                        Intent intent0 = new Intent(MainActivity.this,Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("memberid", memberid);
                        Log.d("fffaaa",memberid);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(MainActivity.this,MonitorActivity.class);
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
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(MainActivity.this, Beacon.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }



//    public void onClick(View v) {
//
//        switch (v.getId()) {
////            case R.id.btn_login:
////                break;
//            case R.id.bkimg:
//                signOut();
//                break;
//        }
//    }
    private  void  signOut() {

        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
        Intent it = new Intent(this,LoginActivity.class);
        startActivity(it);

    }


    public void gotoSecondActivity(View v){ //連到iBeacon頁面
        Intent it = new Intent(this,SecondActivity.class);
        startActivity(it);
    }

    public void gotoMonitorActivity(View v) {
        Intent it = new Intent(this,MonitorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("my_id", memberid);
        bundle.putString("my_google_id", googleid);
        bundle.putString("my_supervise_id", my_mon_id);
        it.putExtras(bundle);
        startActivity(it);
    }

    public void gotoChoice(View v){  //連到排程選擇頁面
        Intent it = new Intent(this,Choice.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        Log.d("fffaaa",memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotodruginfo(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,druginfo.class);
        startActivity(it);
    }
    public void gotoFirstctivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FirstActivity.class);
        startActivity(it);
    }
    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,DrugListActivity.class);
        startActivity(it);
    }
    public void gotoGenerate_Qrcode(){ //連到製造qrcode頁面
        Intent it = new Intent(this,Generate_Qrcode.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoLoginActivity(View v){ //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,LoginActivity.class);
//        Log.d("hh","4");
//        LoginActivity la=new LoginActivity();
//        Log.d("hh","3");
//        la.signoutbtn="1";
////        la.signOut();
//        Log.d("hh","1");
//        startActivity(it);

    }

    public void getid(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, getidUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rrr123", response);
                memberid = response;
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
    }
    public void getMonitorId(){//取得監控者的id
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getMonitorIdUrl = "http://54.65.194.253/Monitor/getMonitorId.php";
        final StringRequest request = new StringRequest(Request.Method.POST, getMonitorIdUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("rrr", "1");
//                Log.d("rrr", response);
                Log.d("my_mon_id",response);

                if(response.contains("nodata")){
                    Log.d("monitorId_check", "success");
                    //normalDialogEvent();

                }
                else{
                    //Log.d("monitor_response",response);
                    my_mon_id=response;
                    Log.d("my_mon_id222", my_mon_id);
                    //addMonitor();//新增監控者至監視列表
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getMonitorId.php!!!", Toast.LENGTH_LONG).show();
//                refreshNormalDialogEvent();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("username", gname);
//                parameters.put("password", gemail);
                parameters.put("google_id_mymonitor", googleid);
                Log.d("google_id_monitor", parameters.toString());
                return parameters;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goback(View v){
        finish();
    }
}
