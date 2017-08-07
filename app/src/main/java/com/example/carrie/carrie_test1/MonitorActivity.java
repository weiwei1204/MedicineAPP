package com.example.carrie.carrie_test1;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.HashMap;
import java.util.Map;


public class MonitorActivity extends AppCompatActivity{
    Button scanbtn;
//    TextView result;
    public static String my_google_id="";
    public static String google_id="";//欲監控對象的google_id
    public static String my_id="";
    public static String my_mon_id="";//Supviser的id
    public static String mon_id="";//欲監控對象的id
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    RequestQueue requestQueue;
    String insertUrl = "http://54.65.194.253/Monitor/checkMonitor.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Bundle bundle = getIntent().getExtras();
        my_id=bundle.getString("my_id");//get 自己 id
        my_google_id=bundle.getString("my_google_id");//get 自己google_ id

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scanbtn = (Button)findViewById(R.id.action_add);
//        result = (TextView)findViewById(R.id.result);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add){
            Intent intent = new Intent(MonitorActivity.this,ScanActivity.class);
            startActivityForResult(intent,REQUEST_CODE);


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCOde,Intent data){
        if (requestCode == REQUEST_CODE && resultCOde == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
//                result.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        result.setText(barcode.displayValue);
//                    }
//                });
                google_id=barcode.displayValue;
                Log.d("monitorGoogle", google_id);
                checkMonitorExist();

            }
        }
    }

    public void gotoBpBsPlot(View v){ //連到圖表頁面
        Intent it = new Intent(this,SwipePlot.class);
        startActivity(it);
    }
    public void checkMonitorExist(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("rrr", "1");
//                Log.d("rrr", response);
                Log.d("monitor_response",response);

                if(response.contains("nodata")){//檢查是否為新會員
                    Log.d("monitor_check", "success");
                    normalDialogEvent();

                }
                else{
                    //Log.d("monitor_response",response);
                    mon_id=response;
                    Log.d("mon_id", mon_id);
                    getMonitorId();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read checkMonitors.php!!!", Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("username", gname);
//                parameters.put("password", gemail);
                parameters.put("google_id_monitor", google_id);
                Log.d("monitor", parameters.toString());
                return parameters;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
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
                    normalDialogEvent();

                }
                else{
                    //Log.d("monitor_response",response);
                    my_mon_id=response;
                    Log.d("my_mon_id222", my_mon_id);
                    addMonitor();//新增監控者至監視列表
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
                parameters.put("google_id_mymonitor", my_google_id);
                Log.d("google_id_monitor", parameters.toString());
                return parameters;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void addMonitor(){
        String addMemberUrl = "http://54.65.194.253/Monitor/addMember.php";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, addMemberUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                addNormalDialogEvent();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr111", error.toString());
                Toast.makeText(getApplicationContext(), "Error read addMember.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("my_mon_id1", my_mon_id);
                parameters.put("mon_id1", mon_id);
                Log.d("my_mon_id123", parameters.toString());
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void  normalDialogEvent(){
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage(R.string.notFindMonitor)
                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "請重新新增", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    public void  addNormalDialogEvent(){
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage("新增好友成功")
                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "已完成新增", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
    public void  refreshNormalDialogEvent(){
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage("請重新掃描")
                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "掃描again", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }



}
