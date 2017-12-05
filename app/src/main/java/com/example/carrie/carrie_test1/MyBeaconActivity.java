package com.example.carrie.carrie_test1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyBeaconActivity extends AppCompatActivity {
    private FloatingActionButton fabscanap;
    String memberid;
    private ListView lv ;
    RequestQueue requestQueue;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";
    private SwipeRefreshLayout laySwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mybeacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        memberid=memberdata.getMember_id();
        lv = (ListView) findViewById(R.id.mlistView);
        Log.d("test2",memberid);
        fabscanap = (FloatingActionButton)findViewById(R.id.fabscanap);
        final Intent it = new Intent(this,ScanBeaconActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        fabscanap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(it);
            }
        });
        putDataToListView();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_list:
                        Intent intent0 = new Intent(MyBeaconActivity.this, Choice.class);
                        Bundle bundle0 = new Bundle();
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        if(isNetworkAvailable()){
                            Intent intent1 = new Intent(MyBeaconActivity.this, MonitorActivity.class);
                            Bundle bundle1 = new Bundle();
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }
                        else {
                            networkCheck();
                        }
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(MyBeaconActivity.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(MyBeaconActivity.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(MyBeaconActivity.this, MyBeaconActivity.class);
                        Bundle bundle4 = new Bundle();
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        lv = (ListView) findViewById(R.id.mlistView);
        putDataToListView();
    }
    public void putDataToListView() {//取此會員的beacon
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest request = new StringRequest(Request.Method.POST, getBeaconUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("nn11",response);
                    try {
                        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
                        laySwipe.setOnRefreshListener(onSwipeToRefresh);
                        laySwipe.setColorSchemeResources(
                                android.R.color.holo_red_light,
                                android.R.color.holo_blue_light,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light);
                        JSONArray jarray = new JSONArray(response);
                        ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
                        for (int i=0;i<jarray.length();i++){
                            JSONObject obj = jarray.getJSONObject(i);
//                        Log.d("nn11", String.valueOf(obj.length()));
//                        Log.d("nn11", String.valueOf(obj));
                        String UUID = obj.getString("UUID");
                        String address =obj.getString("address");
                        String RSSI = obj.getString("RSSI");
                        String beaconname = obj.getString("name");
                        String Member_id = obj.getString("member_id");
                        String ID = obj.getString("id");
                        String newname = obj.getString("newname");
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("ItemImage", R.drawable.bluetooth1);
                            map.put("ItemName", " "+newname+" ("+beaconname+")");
                            map.put("ItemAddress", address);
                            map.put("ItemUUID", UUID);
                            map.put("ItemRSSI", RSSI);
                            map.put("ItemButton", R.drawable.trash);
                            map.put("ItemID",ID);
                            Item.add(map);
                        }
                        BtnAdapter_mybeacon btnadapter_mybeacon = new BtnAdapter_mybeacon(getApplicationContext(),MyBeaconActivity.this, Item, R.layout.beacon_adapter,
                                new String[] {"ItemImage","ItemName", "ItemAddress","ItemUUID","ItemRSSI","ItemButton","ItemID"},
                                new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemAddress,R.id.ItemUUID,R.id.ItemRSSI,R.id.ItemButton,R.id.ItemID} );
                        lv.setAdapter(btnadapter_mybeacon);
                        lv.setOnScrollListener(onListScroll);
                    } catch (JSONException e) {
                        Log.d("nn11",e.toString());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("nn11", error.toString());
                    Toast.makeText(getApplicationContext(), "Error read getAP.php!!!", Toast.LENGTH_LONG).show();
                }
            })
            {
                protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("member_id",memberdata.getMember_id());
                    Log.d("nn11",parameters.toString());
                    return parameters;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);
            lv = (ListView) findViewById(R.id.mlistView);
            putDataToListView();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    laySwipe.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
                }
            }, 4000);
        }
    };
    private AbsListView.OnScrollListener onListScroll = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                laySwipe.setEnabled(true);

            }else{
                laySwipe.setEnabled(false);
            }
        }
    };
    public void goback(View v){
        finish();
    }
    public  boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public  void networkCheck() {
        new AlertDialog.Builder(this)
                .setMessage("請確認網路連線")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }
}
