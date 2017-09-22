package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button scanbtn;
    String memberid;
    private ListView lv ;
    RequestQueue requestQueue;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mybeacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scanbtn = (Button)findViewById(R.id.action_add);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        lv = (ListView) findViewById(R.id.mlistView);
        putDataToListView();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mybeacon, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add){
            Intent it = new Intent(this,ScanBeaconActivity.class);
            Bundle bundle = new Bundle();
//            bundle.putString("memberid", memberid);
//            Log.d("fffaaa",memberid);
            it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
    //Beacon資訊列表

//    @Override
//
//    protected void onResume() {
//
//        super.onResume();
//
//        onCreate(null);
//        Intent intent = new Intent(MyBeaconActivity.this, MyBeaconActivity.class);
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("memberid", memberid);
//        intent.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
//        startActivity(intent);
//        finish();

//    }

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

//    public void myAP(View v){ //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,MyAPActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("memberid", memberid);
//        Log.d("fffaaa","*"+memberid);
//        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
//        startActivity(it);
//    }


//    public void deletebeacon(final Activity activity) {
//
//        Log.d("bcon","1h6");
//        ItemButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("bcon","1");
//
//                BtnAdapter_mybeacon btnAdapter_mybeacon = new BtnAdapter_mybeacon();
//
//                requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                final StringRequest request = new StringRequest(Request.Method.POST, deletebeaconUrl, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("nnnmm", error.toString());
//                        Toast.makeText(getApplicationContext(), "Error read deletebeacon.php!!!", Toast.LENGTH_LONG).show();
//                    }
//                })
//                {
//                    protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
//                        Map<String, String> parameters = new HashMap<String, String>();
//                        parameters.put("UUID",BtnAdapter_mybeacon.getUuid());
//                        Log.d("bcon",parameters.toString());
//
//
//                        return parameters;
//
//                    }
//                };
//                Log.d("bcon","2");
//                RequestQueue requestQueue = Volley.newRequestQueue(MyBeaconActivity.this);
//                requestQueue.add(request);
//            }
//        });
//
//    }
    public void goback(View v){
        finish();
    }
}
