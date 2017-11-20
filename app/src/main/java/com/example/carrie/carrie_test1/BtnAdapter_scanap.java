package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BtnAdapter_scanap extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mAppList;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private Context mContext;
    private String[] keyString;
    private int[] valueViewID;
    private ItemView itemView;
    RequestQueue requestQueue;
    String insert_apUrl = "http://54.65.194.253/Beacon/insert_ap.php";
    private WifiManager mWifiManager;

    private class ItemView {
        ImageView ItemImage;
        TextView ItemSSID;
        TextView ItemBSSID;
        TextView ItemCapabilities;
        TextView ItemLevel;
        TextView ItemFrequency;
        Button ItemButton;
    }

    public BtnAdapter_scanap(Activity activity, Context c, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to) {
        mAppList = appList;
        mContext = c;
        mActivity=activity;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        keyString = new String[from.length];
        valueViewID = new int[to.length];
        System.arraycopy(from, 0, keyString, 0, from.length);
        System.arraycopy(to, 0, valueViewID, 0, to.length);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //return 0;
        return mAppList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        //return null;
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        //return 0;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return null;

        if (convertView != null) {
            itemView = (ItemView) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.ap_adapter, null);
            itemView = new ItemView();
            itemView.ItemImage = (ImageView)convertView.findViewById(valueViewID[0]);
            itemView.ItemSSID = (TextView)convertView.findViewById(valueViewID[1]);
            itemView.ItemBSSID = (TextView)convertView.findViewById(valueViewID[2]);
            itemView.ItemCapabilities = (TextView)convertView.findViewById(valueViewID[3]);
            itemView.ItemLevel = (TextView)convertView.findViewById(valueViewID[4]);
            itemView.ItemFrequency = (TextView)convertView.findViewById(valueViewID[5]);
            itemView.ItemButton = (Button)convertView.findViewById(valueViewID[6]);
            convertView.setTag(itemView);
        }

        HashMap<String, Object> appInfo = mAppList.get(position);
        if (appInfo != null) {

            int mid = (Integer)appInfo.get(keyString[0]);
            String ssid = (String) appInfo.get(keyString[1]);
            String bssid = (String) appInfo.get(keyString[2]);
            String capabilities = (String) appInfo.get(keyString[3]);
            String level = (String) appInfo.get(keyString[4]);
            String frequency = (String) appInfo.get(keyString[5]);
            int bid = (Integer)appInfo.get(keyString[6]);
            itemView.ItemSSID.setText(ssid);
            itemView.ItemBSSID.setText(bssid);
            itemView.ItemCapabilities.setText(capabilities);
            itemView.ItemLevel.setText(level);
            itemView.ItemFrequency.setText(frequency);
            itemView.ItemImage.setImageDrawable(itemView.ItemImage.getResources().getDrawable(mid));
            itemView.ItemButton.setBackgroundDrawable(itemView.ItemButton.getResources().getDrawable(bid));
            itemView.ItemButton.setOnClickListener(new ItemButton_Click(ssid.substring(5),bssid.substring(6),capabilities,level.substring(5),frequency.substring(3)));
        }

        return convertView;
    }

    class ItemButton_Click implements OnClickListener {
        private String ssid;
        private String bssid;
        private String capabilities;
        private String level;
        private String frequency;

        ItemButton_Click(String ssid,String bssid,String capabilities,String level,String frequency) {
            this.ssid = ssid;
            this.bssid = bssid;
            this.capabilities = capabilities;
            this.level = level;
            this.frequency = frequency;
        }

        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if (vid == itemView.ItemButton.getId()){
                Log.v("abc",ssid);
                checkDialog(ssid,bssid,capabilities,level,frequency);
            }
        }
    }
    private void checkDialog(final String ssid,final String bssid,final String capabilities,final String level,final String frequency) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        checkname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                insertAP(ssid,bssid,capabilities,level,frequency);
//                alertDialog.dismiss();
//            }
//        });
        new AlertDialog.Builder(mContext)
                .setTitle("新增AP")
                .setMessage("是否新增"+ssid+"\n("+bssid+")?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //按下按鈕後執行的動作，沒寫則退出Dialog
//                                WifiAdmin(mContext);
                                insertAP(ssid,bssid,capabilities,level,frequency);
                                Toast.makeText(mContext,"加入"+ ssid,Toast.LENGTH_LONG).show();
//                                mActivity.finish();
                            }
                        }
                )
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //按下按鈕後執行的動作，沒寫則退出Dialog
                            }
                        }
                )
                .show();
    }
    public void insertAP(final String ssid,final String bssid,final String capabilities,final String level,final String frequency){
        requestQueue = Volley.newRequestQueue(mContext);
        final StringRequest request = new StringRequest(Request.Method.POST, insert_apUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "加入成功", Toast.LENGTH_LONG).show();
                DeviceData.getBeacon(mContext);
                DeviceData.getAP(mContext);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(mContext, "Error read insert_ap.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                parameters.put("SSID",ssid);
                parameters.put("BSSID",bssid);
                parameters.put("capabilities",capabilities);
                parameters.put("level",level);
                parameters.put("frequency",frequency);
                Log.d("nnnmm", parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }
//    public void WifiAdmin(Context context) {
//        // 取得WifiManager對象
//        mWifiManager = ScanAPActivity.mWifiManager;
//        mWifiManager.enableNetwork(25,true);
//    }
//    WifiConfiguration CreatettgWifiInfo(String SSID, String Password, int Type)
//    {
//        WifiConfiguration config = new WifiConfiguration();
//        config.allowedAuthAlgorithms.clear();
//        config.allowedGroupCiphers.clear();
//        config.allowedKeyManagement.clear();
//        config.allowedPairwiseCiphers.clear();
//        config.allowedProtocols.clear();
//        config.SSID = "\"" + SSID + "\"";
//        if(Type == Data.WIFICIPHER_NOPASS)
//        {
//            config.wepKeys[0] = "";
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            config.wepTxKeyIndex = 0;
//        }
//        if(Type == Data.WIFICIPHER_WEP)
//        {
//            config.hiddenSSID = true;
//            config.wepKeys[0]= "\""+Password+"\"";
//            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//            config.wepTxKeyIndex = 0;
//        }
//        if(Type == Data.WIFICIPHER_WPA)
//        {
//            config.preSharedKey = "\""+Password+"\"";
//            config.hiddenSSID = true;
//            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
//            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//            config.status = WifiConfiguration.Status.ENABLED;
//        }
//        return config;
//    }

}
