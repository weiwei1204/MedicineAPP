package com.example.carrie.carrie_test1;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScanAPActivity extends AppCompatActivity{

    String memberid;
    // 定義WifiManager對象
    private WifiManager mWifiManager;
    // 定義WifiInfo對象
    private WifiInfo mWifiInfo;
    // 掃描出的網络連接列表
    private List<ScanResult> mWifiList;
    // 網络連接列表
    private List<WifiConfiguration> mWifiConfiguration;
    // 定義一個WifiLock
    WifiManager.WifiLock mWifiLock;
    private Context context;
    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        context = this ;
        lv = (ListView) findViewById(R.id.mlistView);
        WifiAdmin(getApplicationContext());
        Log.d("123",Integer.toString(checkState()));
        openWifi();
        getWifi();
        startScan();
        Log.d("123",Integer.toString(checkState()));

        String SSID= mWifiInfo.getSSID() ;//Wi-Fi名稱
        int NETWORKID= mWifiInfo.getNetworkId() ;//Wi-Fi連線ID
        int IPADRRESS= mWifiInfo.getIpAddress() ;//Wi-Fi連線位置
        String IP= String.format("%d.%d.%d.%d", (IPADRRESS & 0xff), (IPADRRESS >> 8 & 0xff), (IPADRRESS >> 16 & 0xff),( IPADRRESS >> 24 & 0xff)) ;//Wi-Fi IP位置
        Log.d("789",SSID+"~"+Integer.toString(NETWORKID)+"~"+IP);
        Log.d("33333",lookUpScan().toString());

        scanResultList();
    }
    public void WifiAdmin(Context context) {
        // 取得WifiManager對象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo對象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    // 打開WIFI
    public void openWifi() {
        //若wifi狀態為關閉則將它開啟
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }
    private void getWifi() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0x12345);
        } else {
            startScan(); // the actual wifi scanning
        }
    }
    // 關閉WIFI
    public void closeWifi() {
        //若wifi狀態為開啟則將它關閉
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 檢查當前WIFI狀態
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    public void startScan() {
        mWifiManager.startScan();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 得到掃描結果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的網络連接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        //目前已連線的Wi-Fi資訊
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    // 得到網络列表
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    // 查看掃描結果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
            // 將ScanResult信息轉換成一個字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
            Log.d("11111",mWifiList.get(i).SSID);
            Log.d("22222",Integer.toString(mWifiList.get(i).level));
        }
        return stringBuilder;
    }

    public void scanResultList(){
        ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i< mWifiList.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.wifi);
            map.put("ItemSSID", "SSID:" + mWifiList.get(i).SSID);
            map.put("ItemBSSID", "BSSID:" + mWifiList.get(i).BSSID);
            map.put("ItemCapabilities", mWifiList.get(i).capabilities);
            map.put("ItemLevel", "信號強度:" + mWifiList.get(i).level);
            map.put("ItemFrequency", "頻道:" + mWifiList.get(i).frequency);
            map.put("ItemButton", R.drawable.add);
            Item.add(map);
        }
        BtnAdapter_scanap btnadapter_scanap = new BtnAdapter_scanap(context, Item, R.layout.ap_adapter,
                new String[]{"ItemImage","ItemSSID", "ItemBSSID", "ItemCapabilities","ItemLevel","ItemFrequency","ItemButton"},
                new int[] {R.id.ItemImage,R.id.ItemSSID,R.id.ItemBSSID,R.id.ItemCapabilities,R.id.ItemLevel,R.id.ItemFrequency,R.id.ItemButton});
        lv.setAdapter(btnadapter_scanap);
    }

    public void goback(View v){
        finish();
    }
}
