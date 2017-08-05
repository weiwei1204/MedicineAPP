package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class checkinfrmationActivity extends Activity {

    private TextView Namec,Emailc,heightc,weightc,agec,effectc;
    private RadioButton radiomanc,radiowomanc;
    private String googleid;
    private Button btnreturn,btnok;
    String email,name,height,weight,age,effect,gender;
    RequestQueue requestQueue;
    String insertUrl = "http://54.65.194.253/Member/insert.php";
    String inserMonitortUrl = "http://54.65.194.253/Monitor/addMonitor.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinfrmation);

        Namec = (TextView)findViewById(R.id.Namec);
        Emailc = (TextView)findViewById(R.id.emailc);
        heightc = (TextView)findViewById(R.id.heightc);
        weightc = (TextView)findViewById(R.id.weightc);
        agec = (TextView)findViewById(R.id.agec);
        effectc = (TextView)findViewById(R.id.effectc);
        radiomanc = (RadioButton)findViewById(R.id.radiomanc);
        radiowomanc = (RadioButton)findViewById(R.id.radiowomanc);
        btnreturn = (Button)findViewById(R.id.btnreturn);
        btnok = (Button)findViewById(R.id.btnok);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        name = bundle.getString("name");
        height = bundle.getString("height");
        weight = bundle.getString("weight");
        age = bundle.getString("age");
        effect = bundle.getString("effect");
        gender = bundle.getString("gender");
        googleid=bundle.getString("googleid");

        Namec.setText(name);
        Emailc.setText(email);
        heightc.setText(height);
        weightc.setText(weight);
        agec.setText(age);
        effectc.setText(effect);
        if (gender.equalsIgnoreCase("1")){
            radiomanc.setChecked(true);
            Log.d("zz",gender);
        }else if (gender.equalsIgnoreCase("2")){
            radiowomanc.setChecked(true);
            Log.d("zz",gender);

        }


    }


    public void insertmember() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("name", name);
                parameters.put("email", email);
                parameters.put("gender_man",gender);
                parameters.put("weight",weight);
                parameters.put("height",height);
                parameters.put("birth",age);
                parameters.put("google_id", googleid);
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    public void insertMonitor() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, inserMonitortUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read addMonitor.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Mname", name);
                parameters.put("Memail", email);
                parameters.put("Mgender_man",gender);
                parameters.put("Mweight",weight);
                parameters.put("Mheight",height);
                parameters.put("Mbirth",age);
                parameters.put("Mgoogle_id", googleid);
                Log.d("monitoradd", parameters.toString());
                Log.d("monitoraddCheck","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void gotoinActivity(View v){ //連到個人資訊頁面
        Intent it = new Intent(this,informationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",Namec.getText().toString());
        bundle.putString("email", Emailc.getText().toString());
//        bundle.putString("height", heightc.getText().toString());
//        bundle.putString("weight", weightc.getText().toString());
//        bundle.putString("age", agec.getText().toString());
//        bundle.putString("effect", effectc.getText().toString());
//        bundle.putString("gender", radioresult);
        bundle.putString("googleid", googleid);
//        Log.d("mm1",radioresult);
//        Log.d("mm",bundle.getString("age"));
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoMainActivity(View v){ //連到首頁
        insertmember();
        insertMonitor();
        Intent it = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }

}
