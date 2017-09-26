package com.example.carrie.carrie_test1;

import android.content.Context;
import android.util.Log;
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
 * Created by User on 2017/9/26.
 */

public class DeviceData {
    static String memberid = memberdata.getMember_id();
    static RequestQueue requestQueue;
    private static ArrayList<String> needBeacon = new ArrayList<String>();
    private static ArrayList<String> Beaconcal = new ArrayList<String>();
    private static ArrayList<String> storeAPBSSID = new ArrayList<String>();
    private static int UUIDnum = 0 ;
    private static int SSIDnum = 0 ;
    static String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";
    static String getAPUrl = "http://54.65.194.253/Beacon/getAP.php";

    public static void getBeacon(final Context mContext){
        requestQueue = Volley.newRequestQueue(mContext);
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_BeaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nn1111",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    UUIDnum = jarray.length() ;
                    needBeacon.clear();
                    Beaconcal.clear();
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String UUID = obj.getString("UUID");
                        String name = obj.getString("name");
                        needBeacon.add(i,UUID);
                        Beaconcal.add(i,name);
                        Log.d("needBeacon",needBeacon.get(i));
                    }
                    memberdata.setNeedBeacon(needBeacon);
                    memberdata.setBeaconcal(Beaconcal);
                    UUIDnum = needBeacon.size();
                    SSIDnum = storeAPBSSID.size();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error read getm_Beacon.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1111",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(drugrequest);
    }
    public static void getAP(final Context mContext){
        requestQueue = Volley.newRequestQueue(mContext);
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getAPUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("nn1122",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] SSIDarray=new String[jarray.length()];
                    final String[] BSSIDarray=new String[jarray.length()];
                    storeAPBSSID.clear();
                    SSIDnum = jarray.length();
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String SSID = obj.getString("SSID");
                        String BSSID = obj.getString("BSSID");
                        SSIDarray[i]=SSID;
                        storeAPBSSID.add(i,BSSID);
                        Log.d("nn11",BSSID);
                    }
                    memberdata.setStoreAPBSSID(storeAPBSSID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error read getm_AP.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1122",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(drugrequest);
    }
}
