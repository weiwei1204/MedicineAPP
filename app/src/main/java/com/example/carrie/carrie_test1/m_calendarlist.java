package com.example.carrie.carrie_test1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by rita on 2017/8/16.
 */

public class m_calendarlist extends Activity{
    private ListView m_callist;
    private mcallistAdapter adapter;
    private List<m_calendar> m_calendararray;
    private ArrayList<String> calarray = new ArrayList<String>();
    String memberid;
    RequestQueue requestQueue;
    private String getm_calendarUrl = "http://54.65.194.253/Medicine_Calendar/getm_calendar.php";
    private FloatingActionButton fabmcal;


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_calendarlist);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        fabmcal = (FloatingActionButton)findViewById(R.id.fabmcal);


        final Intent it = new Intent(this,ThirdActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        bundle1.putInt("entertype", 0);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        fabmcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(it);
            }
        });

        m_callist=(ListView)findViewById(R.id.m_callist);
        m_calendararray=new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Log.d("aaa","自Android 6.0开始需要打开位置权限才可以搜索到Ble设备");
                }
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_REQUEST_COARSE_LOCATION);
            }
           LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);    // 獲取系統位置服務


        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getm_calendarUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("qqq","1");
                    JSONArray mcalendars = response.getJSONArray("mcalendars");
                    Log.d("qqq","3");
                    int count=0;
                    for (int i=0 ; i<mcalendars.length() ; i++){
                        JSONObject mcalendar = mcalendars.getJSONObject(i);
                        String Member_id = mcalendar.getString("member_id");
                        if (Member_id.equals(memberid)){
                            count++;
                            Log.d("fffabc", String.valueOf(count));
                        };
                    }
                    final String[] mid=new String[count];
                    final String[] mname=new String[count];
                    final String[] mdate=new String[count];
                    final String[] mday=new String[count];
                    count=0;
                    for (int i=0 ; i<mcalendars.length() ; i++){
                        JSONObject mcalendar = mcalendars.getJSONObject(i);
                        String id = mcalendar.getString("id");
                        String name = mcalendar.getString("name");
                        String Member_id = mcalendar.getString("member_id");
                        String startDate = mcalendar.getString("startDate");
                        String day = mcalendar.getString("day");
                        if (Member_id.equals(memberid)){
                            mid[count] = id;
                            mname[count] = name;
                            mdate[count] = startDate;
                            mday[count] = day;
                            m_calendararray.add(new m_calendar(Integer.valueOf(mid[count]),mname[count],mdate[count],mday[count]));
                            count++;
                        };
                    }//取值結束
                    madapter();
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



//        m_calendararray.add(new m_calendar(1,"haha","111","aaaaa"));
//        m_calendararray.add(new m_calendar(2,"wowo","222","bbbbb"));
//        m_calendararray.add(new m_calendar(3,"yaya","333","ccccc"));




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("aaa","444");
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }
    public void madapter(){
        adapter = new mcallistAdapter(getApplicationContext(),m_calendararray);
        m_callist.setAdapter(adapter);
        final Intent it1 = new Intent(this,medicine_cal.class);

        m_callist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"id = "+view.getTag(),Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("m_calid",view.getTag().toString() );
                bundle.putString("memberid", memberid);
                bundle.putInt("entertype",0);
                it1.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                startActivity(it1);
            }
        });


    }
}
