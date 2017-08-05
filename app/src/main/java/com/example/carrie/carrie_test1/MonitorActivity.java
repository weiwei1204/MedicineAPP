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
import android.widget.TextView;
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
    TextView result;
    public String google_id;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    RequestQueue requestQueue;
    String insertUrl = "http://54.65.194.253/Monitor/checkMonitor.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scanbtn = (Button)findViewById(R.id.action_add);
        result = (TextView)findViewById(R.id.result);
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
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
                google_id=barcode.displayValue;
                Log.d("monitorGoogle", google_id);
                checkMonitorExist();
            }
        }
    }

    public void gotoBpBsPlot(View v){ //連到圖表頁面
        Intent it = new Intent(this,BpBsPlot.class);
        startActivity(it);
    }
    public void checkMonitorExist(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("rrr", "1");
//                Log.d("rrr", response);

                if(response.contains("success")){//檢查是否為新會員
                    //gotoMain();

                }
                else if(response.contains("nodata")){
                    Log.d("monitor_check", "success");
                    normalDialogEvent();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
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
    public void addMonitor(){

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


}
