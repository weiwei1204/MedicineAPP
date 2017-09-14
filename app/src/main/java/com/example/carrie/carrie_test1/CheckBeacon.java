package com.example.carrie.carrie_test1;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
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
import java.util.List;
import java.util.Map;

public class CheckBeacon extends Service {
    private Handler handler = new Handler( );
    private Runnable runnable;
    // 定義WifiManager對象
    public static WifiManager mWifiManager;
    // 定義WifiInfo對象
    private WifiInfo mWifiInfo;
    private int status = 2 ;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SEARCH_TIMEOUT = 10000;
    private static List<BluetoothDevice> mBleDevices = new ArrayList<BluetoothDevice>();
    private String beacon[][] = null;
    private int beaconNum = 0;
    RequestQueue requestQueue;
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";

    @Override
    public IBinder onBind(Intent arg0) {
// TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        super.onCreate();
        checkWifi();
        WifiAdmin(getApplicationContext());
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service start", Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable, 5000);
    }
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service stop", Toast.LENGTH_SHORT).show();
    }
    public void checkWifi(){
        runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                String SSID= mWifiInfo.getSSID() ;//Wi-Fi名稱
                int NETWORKID= mWifiInfo.getNetworkId() ;//Wi-Fi連線ID
                int LinkSpeed= mWifiInfo.getLinkSpeed() ;
                int Rssi= mWifiInfo.getRssi() ;
                String BSSID= mWifiInfo.getBSSID() ;
                String MacAddress= mWifiInfo.getMacAddress() ;
                int IPADRRESS= mWifiInfo.getIpAddress() ;//Wi-Fi連線位置
                String IP= String.format("%d.%d.%d.%d", (IPADRRESS & 0xff), (IPADRRESS >> 8 & 0xff), (IPADRRESS >> 16 & 0xff),( IPADRRESS >> 24 & 0xff)) ;//Wi-Fi IP位置
                Log.d("qq","[SSID="+SSID+"],[NetworkID="+Integer.toString(NETWORKID)+"],[LinkSpeed="+Integer.toString(LinkSpeed)+"],[Rssi="+Integer.toString(Rssi)+"],[BSSID="+BSSID+"],[MacAddress="+MacAddress+"],[IPAdrress="+IP+"]");
                if(BSSID.equals("a4:ca:a0:64:3e:00")){
                    status = 2 ;
                    Log.d("qq","222");
                    checkBeacon();
                }
                handler.postDelayed(this, 5000);
            }
        };
    }
    public void WifiAdmin(Context context) {
        // 取得WifiManager對象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo對象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }
    public void checkBeacon(){
        if(status==2){
            Log.d("qq","333");
            handler.removeCallbacks(runnable);
            InitBLE ();
            SearchForBLEDevices();
            status = 3 ;
        }
//        Log.d("qqqqqqqqqqqqqqqqqqqqqq",Integer.toString(beacon.length));
//        for(int i = 0 ; i < beacon.length ; i ++){
//
//        }
    }
    private void InitBLE() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == false) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (mBluetoothAdapter.isEnabled() == false) {
            Log.d("qq","444");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void SearchForBLEDevices () {
        new Thread() {
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mBleScanCallback);
                Log.d("aaa", "1111");
                try {
                    Thread.sleep(SEARCH_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mBluetoothAdapter.stopLeScan(mBleScanCallback);
            }
        }.start();
    }

    public BluetoothAdapter.LeScanCallback mBleScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            try{
                if (device != null) {
                    if (mBleDevices.indexOf(device) == -1) //only add new devices
                    {
                        mBleDevices.add(device);
                        for (int i=0; i<1; i++) {
                            String  uuid = "" ;
                            if(scanRecord.length > 30) {
                                //從scanRecord 分辦是固定封包是6byte還是9ybge。
                                //if((scanRecord[5]  == (byte)0x4c) && (scanRecord[6] == (byte)0x00) && (scanRecord[7]  == (byte)0x02) && (scanRecord[8] == (byte)0x15)) {
                                Log.v("test1", "123+"+IntToHex2(scanRecord[5] & 0xff)+"+321");
                                Log.v("test1", "123+"+IntToHex2(scanRecord[6] & 0xff)+"+321");
                                Log.v("test1", "123+"+IntToHex2(scanRecord[7] & 0xff)+"+321");
                                Log.v("test1", "123+"+IntToHex2(scanRecord[8] & 0xff)+"+321");
                                Log.v("test1", "123+"+IntToHex2(scanRecord[9] & 0xff)+"+321");
                                Log.v("test1", "123+"+IntToHex2(scanRecord[10] & 0xff)+"+321");
                                uuid = IntToHex2(scanRecord[5] & 0xff)  +  IntToHex2(scanRecord[6] & 0xff)
                                    +  IntToHex2(scanRecord[7] & 0xff) + IntToHex2(scanRecord[8] & 0xff)
                                    +  IntToHex2(scanRecord[9] & 0xff) +  IntToHex2(scanRecord[10] & 0xff) +  "-"
                                    +  IntToHex2(scanRecord[9] & 0xff) +  IntToHex2(scanRecord[10] & 0xff)
                                    +  IntToHex2(scanRecord[11] & 0xff) +  IntToHex2(scanRecord[12] & 0xff) +  "-"
                                    +  IntToHex2(scanRecord[13] & 0xff) +  IntToHex2(scanRecord[14] & 0xff) +  "-"
                                    +  IntToHex2(scanRecord[15] & 0xff) +  IntToHex2(scanRecord[16] & 0xff) +  "-"
                                    +  IntToHex2(scanRecord[17] & 0xff) +  IntToHex2(scanRecord[18] & 0xff) +  "-"
                                    +  IntToHex2(scanRecord[19] & 0xff) +  IntToHex2(scanRecord[20] & 0xff)
                                    +  IntToHex2(scanRecord[21] & 0xff) +  IntToHex2(scanRecord[22] & 0xff)
                                    +  IntToHex2(scanRecord[23] & 0xff) +  IntToHex2(scanRecord[24] & 0xff);
                            }
                            Log.d("qq","[Name="+device.getName()+"],[Address="+device.getAddress()+"],[UUID="+uuid+"],[RSSI="+Integer.toString(rssi)+"]");
                            Log.d("qqqq","beacon.toString()");
                            beacon[beaconNum][0] = uuid ;
                            beacon[beaconNum][1] = Integer.toString(rssi);
                            beaconNum ++ ;
                            Log.d("qqqqqqqqqqqqqqqqqqqqqq","beacon.toString()");
                        }
                    }
                }
            }catch (Exception e){
                Log.d("aaa",e.toString());
            }
        }
    };
    //int整數和hex16進位轉換
    public String IntToHex2(int i) {
        char hex_2[] = {Character.forDigit((i >> 4) & 0x0f, 16), Character.forDigit(i & 0x0f, 16)};
        String hex_2_str = new String(hex_2);
        return hex_2_str.toUpperCase();
    }
    public void finish() {
        throw new RuntimeException("Stub!");
    }
    public void startActivityForResult(Intent intent, int requestCode) {
        throw new RuntimeException("Stub!");
    }
    public void getbeacon(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_BeaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nn11",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] UUIDarray=new String[jarray.length()];
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String UUID = obj.getString("UUID");
                        UUIDarray[i]=UUID;
                        Log.d("nn11",UUID);
                    }
                } catch (JSONException e) {
                    Log.d("nn11",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nn11", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_Beacon.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id","4");
                Log.d("nn11",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }

    public void getAP(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest drugrequest = new StringRequest(Request.Method.POST, getm_BeaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nn11",response);
                try {
                    JSONArray jarray = new JSONArray(response);
                    final String[] SSIDarray=new String[jarray.length()];
                    final String[] BSSIDarray=new String[jarray.length()];

                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj = jarray.getJSONObject(i);
                        String SSID = obj.getString("SSID");
                        String BSSID = obj.getString("BSSID");
                        SSIDarray[i]=SSID;
                        BSSIDarray[i]=BSSID;
                        Log.d("nn11",BSSID);
                    }
                } catch (JSONException e) {
                    Log.d("nn11",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nn11", error.toString());
                Toast.makeText(getApplicationContext(), "Error read getm_Beacon.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id","4");
                Log.d("nn11",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(drugrequest);
    }

}