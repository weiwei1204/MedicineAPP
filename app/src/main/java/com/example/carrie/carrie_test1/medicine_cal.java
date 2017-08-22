package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class medicine_cal extends AppCompatActivity {
    private EditText m_cal_nameid,txtdayid;
    private TextView txtdateid;

    RequestQueue requestQueue;
    private String getm_calendarUrl = "http://54.65.194.253/Medicine_Calendar/getm_calendar.php";
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String deletem_caledarUrl = "http://54.65.194.253/Medicine_Calendar/deletem_calendar.php";
    private String m_calid,memberid,beaconUUID,beaconid;
    private ImageButton btnAddid;
    private Spinner spinnerbeaconid;
    private ArrayAdapter<CharSequence> adapterbeacon;
    int calbid,calbeacon;
    private Button m_delete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_cal);
        m_cal_nameid = (EditText)findViewById(R.id.m_cal_nameid);
        txtdateid = (TextView) findViewById(R.id.txtdateid);
        txtdayid = (EditText)findViewById(R.id.txtdayid);
        btnAddid = (ImageButton)findViewById(R.id.btnAddid);
        spinnerbeaconid = (Spinner)findViewById(R.id.spinnerbeaconid);
        m_delete = (Button)findViewById(R.id.m_delete);

        add(this,btnAddid);



        Bundle bundle = getIntent().getExtras();
        m_calid=bundle.getString("m_calid");
        m_delete();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getm_calendarUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("qqq","1");
                    JSONArray mcalendars = response.getJSONArray("mcalendars");
                    Log.d("qqq","3");

                    for (int i=0 ; i<mcalendars.length() ; i++){
                        JSONObject mcalendar = mcalendars.getJSONObject(i);
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
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
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
                            if (calbid==Integer.parseInt(id)){
                                calbeacon =count;
                                Log.d("vvvvvvvv", String.valueOf(calbeacon));
                            }
                            Log.d("vvvvv",beaconarray[count]);
                            count++;
                        };
                    }//取值結束
                    adapterbeacon = new ArrayAdapter(medicine_cal.this,android.R.layout.simple_spinner_item,beaconarray);
                    adapterbeacon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    View layout = LayoutInflater.from(medicine_cal.this).inflate(android.R.layout.simple_spinner_item, null );
//                    TextView spinnertext=(TextView)layout.findViewById(R.id.text1);
//                    spinnertext.setTextColor(Color.parseColor("#272727"));
                    spinnerbeaconid.setAdapter(adapterbeacon);
                    spinnerbeaconid.setSelection(calbeacon,false);
                    spinnerbeaconid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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



    public void add(final Activity activity, ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutFormid);
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

    public void m_delete(){//刪除用藥排程
        m_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                final StringRequest request = new StringRequest(Request.Method.POST, deletem_caledarUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                startActivity(it);}
        });


    }

}
