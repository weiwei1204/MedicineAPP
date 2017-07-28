package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {


    Button btnDisplay;
    ImageButton btnAdd;
    EditText txtTime;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String m_caledarUrl = "http://54.65.194.253/Medicine_Calendar/m_calendar.php";
    ArrayAdapter<CharSequence> adapterbeacon;
    Spinner spinnerbeacon;
    String memberid,beaconUUID;
    RequestQueue requestQueue;





    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");

        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        MyLayoutOperation.display(this, btnDisplay);
//        MyLayoutOperation.add(this, btnAdd);
        add(this,btnAdd);
        Log.d("ssdf",this.toString());
        spinnerbeacon = (Spinner)findViewById(R.id.spinnerbeacon);
        getBeacon();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void getBeacon(){
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
                        }
                    }
                    Log.d("vvv123", String.valueOf(count));
                    final String[] beaconarray=new String[count];
                    count=0;
                    for (int i=0 ; i<beacons.length() ; i++){
                        JSONObject beacon = beacons.getJSONObject(i);
                        String UUID = beacon.getString("UUID");
                        String Member_id = beacon.getString("member_id");
                        if (Member_id.equals(memberid)){
                            beaconarray[count] = UUID;
                            Log.d("vvvvv",beaconarray[count]);
                            count++;
                        }
                    }
                    adapterbeacon = new ArrayAdapter(ThirdActivity.this,android.R.layout.simple_spinner_item,beaconarray);
                    adapterbeacon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerbeacon.setSelection(0,false);
                    spinnerbeacon.setAdapter(adapterbeacon);
                    spinnerbeacon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
                            beaconUUID= (String) parent.getItemAtPosition(position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
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





    public void gotoalarm(View v){ //連到親友認證頁面
        Intent it = new Intent(this,alarm.class);
        startActivity(it);
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
    }
    public void insertm_calendar() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, m_caledarUrl, new Response.Listener<String>() {
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

//                parameters.put("google_id", googleid);
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


}
