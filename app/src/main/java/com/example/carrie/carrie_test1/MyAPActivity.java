package com.example.carrie.carrie_test1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

/**
 * Created by User on 2017/8/25.
 */
public class MyAPActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private FloatingActionButton fabscanap;
    String memberid;
    private Context context;
    private ListView lv ;
    RequestQueue requestQueue;
    String getAPUrl = "http://54.65.194.253/Beacon/getAP.php";
    private SwipeRefreshLayout laySwipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        context = this ;
        lv = (ListView) findViewById(R.id.mlistView);
        Log.d("test2",memberid);
        fabscanap = (FloatingActionButton)findViewById(R.id.fabscanap);
        final Intent it = new Intent(this,ScanAPActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        fabscanap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(it);
            }
        });
        myWifiList();
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        onCreate(null);
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

    @Override

    protected void onResume() {

        super.onResume();
        lv = (ListView) findViewById(R.id.mlistView);
        myWifiList();

    }
    public void myWifiList(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, getAPUrl, new Response.Listener<String>() {
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
                        String SSID = obj.getString("SSID");
                        String BSSID = obj.getString("BSSID");
                        String Capabilities = obj.getString("capabilities");
                        String Level = obj.getString("level");
                        String Frequency = obj.getString("frequency");
                        String ID = obj.getString("id");


                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("ItemImage", R.drawable.wifi);
                        map.put("ItemSSID", " " + SSID);
                        map.put("ItemBSSID", "BSSID:"+BSSID);
                        map.put("ItemCapabilities",""+Capabilities);
                        map.put("ItemLevel", "信號強度:"+Level);
                        map.put("ItemFrequency", "頻道:"+Frequency);
                        map.put("ItemID",ID);
                        map.put("ItemButton", R.drawable.trash);
                        Item.add(map);
                    }

                    BtnAdapter_myap btnadapter_myap = new BtnAdapter_myap(context, Item, R.layout.ap_adapter,
                            new String[]{"ItemImage","ItemSSID", "ItemBSSID", "ItemCapabilities","ItemLevel","ItemFrequency","ItemButton","ItemID"},
                            new int[] {R.id.ItemImage,R.id.ItemSSID,R.id.ItemBSSID,R.id.ItemCapabilities,R.id.ItemLevel,R.id.ItemFrequency,R.id.ItemButton,R.id.ItemID});
                    lv.setAdapter(btnadapter_myap);
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

    @Override
    public void onRefresh() {
        onCreate(null);
    }
}
