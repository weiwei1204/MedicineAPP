package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");


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

    public void gotodruginfoActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,druginfo.class);
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
    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FirstActivity.class);
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

    public void goback(View v){
        finish();
    }
}
