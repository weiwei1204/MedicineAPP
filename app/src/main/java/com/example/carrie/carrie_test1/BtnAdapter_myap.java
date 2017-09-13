package com.example.carrie.carrie_test1;

import android.content.Context;
import android.content.DialogInterface;
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

public class BtnAdapter_myap extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] keyString;
    private int[] valueViewID;
    RequestQueue requestQueue;
    String deleteAPUrl = "http://54.65.194.253/Beacon/deleteAP.php";
    private ItemView itemView;
    private class ItemView {
        ImageView ItemImage;
        TextView ItemSSID;
        TextView ItemBSSID;
        TextView ItemCapabilities;
        TextView ItemLevel;
        TextView ItemFrequency;
        TextView ItemID;
        Button ItemButton;
    }

    public BtnAdapter_myap(Context c, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to) {
        mAppList = appList;
        mContext = c;
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
            itemView.ItemID = (TextView)convertView.findViewById(valueViewID[7]);
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
            String ID = (String) appInfo.get(keyString[7]);
            int bid = (Integer)appInfo.get(keyString[6]);
            itemView.ItemSSID.setText(ssid);
            itemView.ItemBSSID.setText(bssid);
            itemView.ItemCapabilities.setText(capabilities);
            itemView.ItemLevel.setText(level);
            itemView.ItemFrequency.setText(frequency);
            itemView.ItemImage.setImageDrawable(itemView.ItemImage.getResources().getDrawable(mid));
            itemView.ItemButton.setBackgroundDrawable(itemView.ItemButton.getResources().getDrawable(bid));
            itemView.ItemButton.setOnClickListener(new ItemButton_Click(ID));
        }

        return convertView;
    }

    class ItemButton_Click implements OnClickListener {
        private String id;

        ItemButton_Click(String pos) {
            id = pos;
        }

        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if (vid == itemView.ItemButton.getId())
                checkDialog(id);
        }
    }
    private void checkDialog(final String id) {
        new AlertDialog.Builder(mContext)
                .setTitle("刪除AP")
                .setMessage("是否刪除"+id.substring(1)+"?")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //按下按鈕後執行的動作，沒寫則退出Dialog
                                deleteAP(id);
                                Toast.makeText(mContext,"成功刪除"+ id.substring(1),Toast.LENGTH_LONG).show();
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
    public void deleteAP(final String id){
        requestQueue = Volley.newRequestQueue(mContext);
        final StringRequest request = new StringRequest(Request.Method.POST, deleteAPUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(mContext, "Error read deleteAP.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id",id);
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

}
