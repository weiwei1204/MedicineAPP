package com.example.carrie.carrie_test1;

import android.bluetooth.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.Manifest;

import com.android.volley.*;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class ScanBeaconActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback{

    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SEARCH_TIMEOUT = 10000; //10 seconds should be plenty
    private ListView lv ;
    private static List<BluetoothDevice> mBleDevices = new ArrayList<BluetoothDevice>();
    private Context context;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    RequestQueue requestQueue;
//    String beacon_insertUrl = "http://54.65.194.253/Beacon/beacon_insert.php";
//    String memberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanbeacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Bundle bundle = getIntent().getExtras();
//        memberid=bundle.getString("memberid");
        openBluetooth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("aaa","111");
            // Android M Permission check
            if (ContextCompat.checkSelfPermission(ScanBeaconActivity.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("aaa","222");
                requestPermissions(new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                Log.d("aaa","333");
            }

        }
        context = this;
        lv = (ListView) findViewById(R.id.mlistView);
        SearchForBLEDevices ();
//        putDataToListView();
        //insertbeacon();
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

    public void openBluetooth(){

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == false) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        //获取蓝牙适配器
        final BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        //开启蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (mBluetoothAdapter.isEnabled() == false) {
            //弹出对话框提示用户是后打开
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enabler, REQUEST_ENABLE_BT);
        }
    }

    private void SearchForBLEDevices ()
    {
        new Thread() {
            @Override
            public void run() {
//                Log.v("aaa", Boolean.toString(mBluetoothAdapter.startDiscovery()));
//                Log.v("aaa", "6666");
                try{
                    mBluetoothAdapter.startLeScan(mBleScanCallback);
                    Log.v("aaa", "5555");
                }catch(Exception obj){
                    Log.v("aaa", "4444");
                }

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
                Log.v("aaa", "2222");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.v("aaa", "3333");
                        if (device != null) {
                            Log.v("aaa", "123+"+ mBleDevices.indexOf(device)+"+321");
                            if (mBleDevices.indexOf(device) == -1) //only add new devices
                            {
                                mBleDevices.add(device);

//                            ArrayList<ArrayList<String>> listValues = new ArrayList<ArrayList<String>>();
                                ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
                                for (BluetoothDevice device : mBleDevices)
                                {
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
                                    HashMap<String, Object> map = new HashMap<String, Object>();
                                    map.put("ItemImage", R.drawable.wifi);
                                    map.put("ItemName", "Name:"+device.getName());
                                    map.put("ItemAddress", "Address:"+device.getAddress());
                                    map.put("ItemUUID", "UUID:"+uuid);
                                    Log.d("uuid123",uuid);
                                    map.put("ItemRSSI", "RSSI:"+Integer.toString(rssi));
                                    map.put("ItemButton", R.drawable.add);
                                    Item.add(map);
                                    Log.d("uuid",Item.toString());


//                                ArrayList<String> dataList = new ArrayList<String>();
//                                dataList.add("Name:"+device.getName());
//                                dataList.add("Address:"+device.getAddress());
//                                dataList.add("UUID:"+ UUID.randomUUID());
//                                dataList.add("Connection Status :"+Integer.toString(device.getBondState()));
//                                dataList.add("RSSI:"+Integer.toString(rssi));
//                                device.createBond();
//                                //listValues.add(result.getR);
//                                Log.v("test1", "123+"+ Arrays.toString(device.getUuids())+"+321");
//                                Log.v("test2 ", "123+"+Integer.toString(device.getBondState())+"+321");
//                                //listValues.add(Integer.toString(device.getUuids().length));
//                                listValues.add(dataList);
                                }
                                BtnAdapter_scanbeacon btnadapter_scanbeacon = new BtnAdapter_scanbeacon(context, Item, R.layout.beacon_adapter,
                                        new String[]{"ItemImage","ItemName", "ItemAddress","ItemUUID","ItemRSSI","ItemButton"},
                                        new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemAddress,R.id.ItemUUID,R.id.ItemRSSI,R.id.ItemButton});
                                lv.setAdapter(btnadapter_scanbeacon);
                                //ArrayAdapter myAdapter = new ArrayAdapter(context, R.layout.row_layout, R.id.listText, listValues);

                                //setListAdapter(myAdapter);
                            }

                        }
                    }



                });
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


//    public void insertbeacon() {
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//        final StringRequest request = new StringRequest(Request.Method.POST, beacon_insertUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("rrr", error.toString());
//                Toast.makeText(getApplicationContext(), "Error read beacon_insert.php!!!", Toast.LENGTH_LONG).show();
//            }
//        })  {
//            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
//                Map<String, String> parameters = new HashMap<String, String>();
//                parameters.put("UUID", "123");
//                parameters.put("member_id", memberid);
//                Log.d("my111", parameters.toString());
//                Log.d("my","checck!!!");
//                return parameters;
//
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }




//    //Beacon資訊列表
//    protected void putDataToListView()
//    {
//        ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
//        for(int i=0; i<20; i++)
//        {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.drawable.wifi);
//            map.put("ItemName", "Name:"+i);
//            map.put("ItemAddress", "Address:");
//            map.put("ItemUUID", "UUID:");
//            map.put("ItemRSSI", "RSSI:");
//            map.put("ItemButton", R.drawable.add);
//            Item.add(map);
//            Log.d("uuiddd",Item.toString());
//        }
//
//        BtnAdapter_scanbeacon Btnadapter = new BtnAdapter_scanbeacon(
//                this,
//                Item,
//                R.layout.beacon_adapter,
//                new String[] {"ItemImage","ItemName", "ItemAddress","ItemUUID","ItemRSSI","ItemButton"},
//                new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemAddress,R.id.ItemUUID,R.id.ItemRSSI,R.id.ItemButton}
//        );
//        lv.setAdapter(Btnadapter);
//    }
    public void goback(View v){
        finish();
    }
}
