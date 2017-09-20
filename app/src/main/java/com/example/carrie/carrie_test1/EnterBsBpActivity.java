package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class EnterBsBpActivity extends AppCompatActivity {
    private PagerAdapter BsBpPagerAdapter;
    private ViewPager BsBpViewPager;
    public static String membercurrentid;
    Button btn ;
    public static String my_mon_id;
    public static String my_google;
    String objectArray;
    String objectDetailArray;
    RequestQueue requestQueue;
    private Dialog dialog;
    String insertbpvalue = "http://54.65.194.253/Health_Calendar/insertbloodpressure.php";
    String insertbsvalue = "http://54.65.194.253/Health_Calendar/insertbloodsugar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bs_bp);
        Bundle bundle = getIntent().getExtras();
//        membercurrentid = bundle.getString("memberid");
//        my_mon_id = bundle.getString("my_supervise_id");
//        my_google = bundle.getString("my_google_id");

        membercurrentid = memberdata.getMember_id();
        my_mon_id = memberdata.getMy_mon_id();
        my_google = memberdata.getGoogle_id();

        BsBpPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        BsBpViewPager = (ViewPager) findViewById(R.id.container2);

        BsBpViewPager.setAdapter(BsBpPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);

        tabLayout.setupWithViewPager(BsBpViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.blood);
        tabLayout.getTabAt(1).setIcon(R.drawable.blood2);

        btn = (Button) findViewById(R.id.backmain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backmain();

            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("1122","id:  "+my_google);
        i.putExtra("googleid",my_google);
        startActivity(i);
        finish();
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


    public class PagerAdapter extends FragmentPagerAdapter {
        Bundle bundle = getIntent().getExtras();
        String memberid=bundle.getString("memberid");
        String my_google = bundle.getString("my_google_id");


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BsRecord tab1 = new BsRecord();
                    Intent it1 = new Intent();
                    it1.putExtra("memberid",memberid);
                    Log.d("1212","google: "+my_google);
                    it1.putExtra("googleid",my_google);
                    return tab1;
                case 1:
                    BpRecord tab2 = new BpRecord();
                    Intent it = new Intent();
                    it.putExtra("memberid",memberid);
                    it.putExtra("googleid",my_google);
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "血糖";
                case 1:
                    return "血壓";

            }
            return null;
        }

    }
    public void backmain(){
        final Intent it = new Intent(EnterBsBpActivity.this,SwipePlot.class);
        dialog = ProgressDialog.show(this,
                "讀取中", "請等待5秒...",true);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    getMonitorPillsRecord2(membercurrentid);
                    getMonitorPillsRecordTime2(membercurrentid);
                    Thread.sleep(3000);
                    Log.d("customadapter", "1");
                    it.putExtra("memberid",membercurrentid);
                    it.putExtra("googleid",my_google);
                    it.putExtra("my_supervise_id",my_mon_id);
                    it.putExtra("objectArray", objectArray);
                    it.putExtra("objectDetailArray", objectDetailArray);
                    startActivity(it);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    dialog.dismiss();
                }
            }
        }).start();


    }
    public void goback(View v){

        finish();
    }
    public void getMonitorPillsRecord2(String memberid) {//取得被監視者的用藥紀錄
        requestQueue = Volley.newRequestQueue(this);

        String getMeasureInformationURL = "http://54.65.194.253/Monitor/getMonitorPillsRecord.php?member_id="+memberid;

        Map<String, String> params = new HashMap();



        //params.put("member_id", memberid);
        //Log.d("measureInfor",params.toString());
        //JSONObject parameters = new JSONObject(params);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getMeasureInformationURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("getMonitorPillsRecord", response.toString());

                if (response.toString().contains("nodata")) {
                    Log.d("getMonitorPillsRecord", "nodata");
                    objectArray = "nodata";
                } else {
                    Log.d("getMonitorPillsRecord", response.toString());
                    objectArray = response.toString();
                    Log.d("getMonitorPillsRecord", objectArray);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getMonitorPillsRecord", error.toString());
                Toast.makeText(EnterBsBpActivity.this, "Error read getMonitorPillsRecord.php!!!", Toast.LENGTH_LONG).show();
//                refreshNormalDialogEvent();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    public void getMonitorPillsRecordTime2(String memberid) {//取得被監視者的用藥紀錄
        requestQueue = Volley.newRequestQueue(this);

        String getMeasureInformationURL = "http://54.65.194.253/Monitor/getPillRecordTime.php?member_id="+memberid;

        Map<String, String> params = new HashMap();



        //params.put("member_id", memberid);
        //Log.d("measureInfor",params.toString());
        //JSONObject parameters = new JSONObject(params);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getMeasureInformationURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("getMonitorPillsRecord", response.toString());

                if (response.toString().contains("nodata")) {
                    Log.d("getMonitorPillsRecord", "nodata");
                    objectDetailArray = "nodata";
                } else {
                    Log.d("getMonitorPillsRecord", response.toString());
                    objectDetailArray = response.toString();
                    Log.d("getMonitorPillsRecord 2", objectDetailArray);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getMonitorPillsRecord", error.toString());
                Toast.makeText(EnterBsBpActivity.this, "Error read getPillRecordTime.php!!!", Toast.LENGTH_LONG).show();
//                refreshNormalDialogEvent();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}

