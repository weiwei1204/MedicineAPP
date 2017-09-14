package com.example.carrie.carrie_test1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    String memberid;
    ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
    private LocationManager locationManager;
    private LocationListener locationListener;

    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanbeacon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        memberid = bundle.getString("memberid");
        openBluetooth();
        Log.v("Build", String.valueOf(Build.VERSION.SDK_INT));
        Log.v("Build", String.valueOf(Build.VERSION_CODES.M));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("aaa","111");
            // Android M Permission check
            Log.v("aaa", "00000");
            Log.v("11111jjjjj","a : "+String.valueOf(ContextCompat.checkSelfPermission(ScanBeaconActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)));
            Log.v("11111jjjjj","b : "+String.valueOf(PackageManager.PERMISSION_GRANTED));
            if (ContextCompat.checkSelfPermission(ScanBeaconActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                Log.v("aaa", "0123");
            }

        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
//            //判断是否具有权限
//            if (PermissionChecker.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //判断是否需要向用户解释为什么需要申请该权限
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    Log.d("aaa","自Android 6.0开始需要打开位置权限才可以搜索到Ble设备");
//                }
//                //请求权限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
//            }
//            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);    // 獲取系統位置服務
//
//
//        }







        context = this;
        lv = (ListView) findViewById(R.id.mlistView);
        SearchForBLEDevices ();
//        putDataToListView();
        //insertbeacon();
    }
    private void findImage()
    {
//        LocationRequest request = LocationRequest.create();
//        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        request.setNumUpdates(1);
//        request.setInterval(0);
//        if(ContextCompat.checkSelfPermission(ScanBeaconActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(ScanBeaconActivity.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//        {
//            Log.d("aaa", "Location Permissions OK");
//        }
//        else
//        {
//            if(ContextCompat.checkSelfPermission(ScanBeaconActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            {
//                Log.d("aaa", "ACCESS_FINE_LOCATION permission error");
//                ActivityCompat.requestPermissions(ScanBeaconActivity.this, new String[] {ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
//            }
//            if(ContextCompat.checkSelfPermission(ScanBeaconActivity.this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            {
//                Log.d("aaa", "ACCESS_COARSE_LOCATION permission error");
//                ActivityCompat.requestPermissions(ScanBeaconActivity.this, new String[] {ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
//            }
//            return;
//        }

    }

    public static boolean isLocationOpen(final Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //gps定位
        boolean isGpsProvider = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //网络定位
        boolean isNetWorkProvider = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsProvider|| isNetWorkProvider;
    }
    public static final boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean networkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (networkProvider || gpsProvider) return true;
        return false;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //用户允许改权限，0表示允许，-1表示拒绝 PERMISSION_GRANTED = 0， PERMISSION_DENIED = -1
//                //permission was granted, yay! Do the contacts-related task you need to do.
//                //这里进行授权被允许的处理
//                if (!isLocationEnable(ScanBeaconActivity.this)){
//                  Log.d("aaa","notopen");
//                };
//            } else {
//                //permission denied, boo! Disable the functionality that depends on this permission.
//                //这里进行权限被拒绝的处理
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }


   @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       Log.v("aaa", "11111");
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
            Log.d("Ble","open");
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
                                if (mBleDevices.size()!=0)
                                {
                                    for (int i=0; i<1; i++)
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
                                        map.put("ItemName", " "+device.getName());
                                        map.put("ItemAddress", device.getAddress());
                                        Log.d("uuid123"," "+uuid);
                                        map.put("ItemRSSI", Integer.toString(rssi));
                                        map.put("ItemButton", R.drawable.add);
                                        Item.add(map);
                                        Log.d("uuid",Item.toString());
                                    }
                                    BtnAdapter_scanbeacon btnadapter_scanbeacon = new BtnAdapter_scanbeacon(ScanBeaconActivity.this,context, Item, R.layout.beacon_adapter,
                                            new String[]{"ItemImage","ItemName", "ItemAddress","ItemUUID","ItemRSSI","ItemButton"},
                                            new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemAddress,R.id.ItemUUID,R.id.ItemRSSI,R.id.ItemButton});
                                    lv.setAdapter(btnadapter_scanbeacon);
                                }
                                else {return;}
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
