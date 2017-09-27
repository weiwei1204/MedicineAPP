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

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;


public class CheckBeacon extends Service {
    String memberid;
    private Handler handler = new Handler( );
    private Runnable runnable;
    // 定義WifiManager對象
    public static WifiManager mWifiManager;
    // 定義WifiInfo對象
    private WifiInfo mWifiInfo;
    private int status = 1 ;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SEARCH_TIMEOUT = 10000;
    private static List<BluetoothDevice> mBleDevices = new ArrayList<BluetoothDevice>();
    private ArrayList<ArrayList<String>> bringBeacon = new ArrayList<ArrayList<String>>();
    private ArrayList<String> needBeacon = new ArrayList<String>();
    private ArrayList<String> Beaconcal = new ArrayList<String>();
    private ArrayList<String> storeAPBSSID = new ArrayList<String>();
    private int beaconNum = 0;
    private int APnum = 0 ;
    RequestQueue requestQueue;
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";
    String getAP = "http://54.65.194.253/Beacon/getAP.php";
    private int UUIDnum = 0 ;
    private int SSIDnum = 0 ;


    @Override
    public IBinder onBind(Intent arg0) {
// TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        super.onCreate();
//        Bundle bundle = getIntent().getExtras();
//        memberid=bundle.getString("memberid");

        checkWifi();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service start", Toast.LENGTH_SHORT).show();
        needBeacon = memberdata.getNeedBeacon();
        Beaconcal = memberdata.getBeaconcal();
        storeAPBSSID = memberdata.getStoreAPBSSID();
        UUIDnum = needBeacon.size();
        SSIDnum = storeAPBSSID.size();
//        getAP();
//        getbeacon();
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
                WifiAdmin(getApplicationContext());
                needBeacon = memberdata.getNeedBeacon();
                Beaconcal = memberdata.getBeaconcal();
                storeAPBSSID = memberdata.getStoreAPBSSID();
                UUIDnum = needBeacon.size();
                SSIDnum = storeAPBSSID.size();
                Log.d("UUIDnum",Integer.toString(UUIDnum));
                Log.d("SSIDnum",Integer.toString(SSIDnum));
                if(UUIDnum != 0 && SSIDnum != 0){
                    String SSID= mWifiInfo.getSSID() ;//Wi-Fi名稱
                    int NETWORKID= mWifiInfo.getNetworkId() ;//Wi-Fi連線ID
                    int LinkSpeed= mWifiInfo.getLinkSpeed() ;
                    int Rssi= mWifiInfo.getRssi() ;
                    String BSSID= mWifiInfo.getBSSID() ;
                    String MacAddress= mWifiInfo.getMacAddress() ;
                    int IPADRRESS= mWifiInfo.getIpAddress() ;//Wi-Fi連線位置
                    String IP= String.format("%d.%d.%d.%d", (IPADRRESS & 0xff), (IPADRRESS >> 8 & 0xff), (IPADRRESS >> 16 & 0xff),( IPADRRESS >> 24 & 0xff)) ;//Wi-Fi IP位置
                    Log.d("qq","[SSID="+SSID+"],[NetworkID="+Integer.toString(NETWORKID)+"],[LinkSpeed="+Integer.toString(LinkSpeed)+"],[Rssi="+Integer.toString(Rssi)+"],[BSSID="+BSSID+"],[MacAddress="+MacAddress+"],[IPAdrress="+IP+"]");
                    Log.d("storeAPBSSIDsize",Integer.toString(storeAPBSSID.size()));
                    int countAP = 0 ;
                    for(int i = 0; i < storeAPBSSID.size(); i++){
                        if(BSSID.equals(storeAPBSSID.get(i))){
                            countAP ++ ;
                        }
                    }
                    if(status == 1 && countAP != 0){
                        status = 2 ;
                        Log.d("qq",Integer.toString(status));
                        checkBeacon();
                    }else if(status == 3 && countAP == 0){
                        status = 4 ;
                        Log.d("qq",Integer.toString(status));
                        checkBeacon();
                    }
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
//            handler.removeCallbacks(runnable);
            InitBLE ();
            SearchForBLEDevices();
            status = 3 ;
        }else if (status == 4){
            Log.d("qq","333");
//            handler.removeCallbacks(runnable);
            InitBLE ();
            SearchForBLEDevices();
            status = 1 ;

        }
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
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
    }

    private void SearchForBLEDevices () {
        new Thread() {
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mBleScanCallback);
                try {
                    Thread.sleep(SEARCH_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mBluetoothAdapter.stopLeScan(mBleScanCallback);
                int countBeacon = 0 ;
                Log.d("needBeaconSize", Integer.toString(needBeacon.size()));
                Log.d("bringBeaconSize", Integer.toString(bringBeacon.size()));
                Log.d("BeaconcalSize", Integer.toString(Beaconcal.size()));
                ArrayList<String> lost = new ArrayList<String>();
                for(int i = 0; i < needBeacon.size(); i++){
                    Boolean check=false;
                    for(int j = 0; j < bringBeacon.size(); j++ ){
                        if(bringBeacon.get(j).get(0).equals(needBeacon.get(i))){
                            check=true;
                            Log.d("bbb", needBeacon.get(i));
                        }else{}
                    }
                    Log.d("bbb", "!!!!!!!!!!"+check);
                    if (check==false){
                        Log.d("bbb", "!!!!!!!!!!"+Beaconcal.get(i));
                        Log.d("bbb", "!!!!!!!!!!"+needBeacon.get(i));
                        lost.add(countBeacon,Beaconcal.get(i));
                        Log.d("bbb",Beaconcal.get(i));
                        countBeacon ++ ;
                    }
                }
                if (lost.size()!=0){
                    Intent my_intent=new Intent(getApplicationContext(),OnBootReceiver.class);
                    my_intent.putExtra("extra",lost);
                    sendBroadcast(my_intent);
                }
//                if(lost.size()!=0){
//                    Log.d("bbb", "沒帶Beacon!!!!!!!!!!"+lost.get(0));
//                    for (int i=0;i<lost.size();i++) {
//                        Toast.makeText(getApplicationContext(), "沒帶"+lost.get(0)+"!!!!", Toast.LENGTH_LONG).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckBeacon.this);
//                        builder.setMessage(lost.get(i)+"\n")
//                                .setTitle("藥忘記帶囉")
//                                .setCancelable(false)
//                                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                });

//                        AlertDialog alert = builder.create();
//                        alert.show ();
//                    }
//                }
//                needBeacon.clear();
//                bringBeacon.clear();
                mBleDevices.clear();
                Log.d("needBeaconSize", Integer.toString(needBeacon.size()));
                Log.d("bringBeaconSize", Integer.toString(bringBeacon.size()));
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
                            ArrayList<String> beaconInfo = new ArrayList<String>();
                            beaconInfo.add(0,uuid);
                            beaconInfo.add(1,Integer.toString(rssi));
                            bringBeacon.add(beaconNum,beaconInfo);
                            Log.d("qqqq",bringBeacon.toString());
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
}