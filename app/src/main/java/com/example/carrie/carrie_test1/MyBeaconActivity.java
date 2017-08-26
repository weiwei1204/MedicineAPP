package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

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

import java.util.*;

public class MyBeaconActivity extends AppCompatActivity {
    Button scanbtn;
    String memberid;
    private ListView lv ;
    RequestQueue requestQueue;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String deletebeaconUrl = "http://54.65.194.253/Medicine/deletem_calendar.php";
    Button ItemButton;




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
            bundle.putString("memberid", memberid);
            Log.d("fffaaa",memberid);
            it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
    //Beacon資訊列表



    public void putDataToListView(){//取此會員的beacon
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
                        };
                    }
                    count=0;
                    ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
                    for (int i=0 ; i<beacons.length() ; i++){
                        JSONObject beacon = beacons.getJSONObject(i);
                        String UUID = beacon.getString("UUID");
                        String id =beacon.getString("id");
                        String address =beacon.getString("address");
                        String RSSI = beacon.getString("RSSI");
                        String beaconname = beacon.getString("name");
                        String Member_id = beacon.getString("member_id");
                        String ID = beacon.getString("id");
                        if (Member_id.equals(memberid)){

                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("ItemImage", R.drawable.wifi);
                            map.put("ItemName", "Name:"+beaconname);
                            map.put("ItemAddress", "Address:"+address);
                            map.put("ItemUUID", "UUID:"+UUID);
                            map.put("ItemRSSI", "RSSI:"+RSSI);
                            map.put("ItemButton", R.drawable.trash);
                            map.put("ItemID",ID);
                            Item.add(map);
                            count++;
                        };
                        BtnAdapter_mybeacon btnadapter_mybeacon = new BtnAdapter_mybeacon(getApplicationContext(),MyBeaconActivity.this, Item, R.layout.beacon_adapter,
                                new String[] {"ItemImage","ItemName", "ItemAddress","ItemUUID","ItemRSSI","ItemButton","ItemID"},
                                new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemAddress,R.id.ItemUUID,R.id.ItemRSSI,R.id.ItemButton,R.id.ItemID} );
                        lv.setAdapter(btnadapter_mybeacon);
                    }
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

    public void deletebeacon(final Activity activity) {

        Log.d("bcon","1h6");
        ItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bcon","1");

                BtnAdapter_mybeacon btnAdapter_mybeacon = new BtnAdapter_mybeacon();

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                final StringRequest request = new StringRequest(Request.Method.POST, deletebeaconUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nnnmm", error.toString());
                        Toast.makeText(getApplicationContext(), "Error read deletebeacon.php!!!", Toast.LENGTH_LONG).show();
                    }
                })
                {
                    protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("UUID",BtnAdapter_mybeacon.getUuid());
                        Log.d("bcon",parameters.toString());


                        return parameters;

                    }
                };
                Log.d("bcon","2");
                RequestQueue requestQueue = Volley.newRequestQueue(MyBeaconActivity.this);
                requestQueue.add(request);
            }
        });

    }




    public void goback(View v){
        finish();
    }
}
