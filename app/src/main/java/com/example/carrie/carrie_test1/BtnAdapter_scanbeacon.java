package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class BtnAdapter_scanbeacon extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private Activity mActivity;
    private String[] keyString;
    private int[] valueViewID;
    RequestQueue requestQueue;
    String insert_beaconUrl = "http://54.65.194.253/Beacon/insert_beacon.php";


    private ItemView itemView;

    private class ItemView {
        ImageView ItemImage;
        TextView ItemName;
        TextView ItemAddress;
        TextView ItemUUID;
        TextView ItemRSSI;
        Button ItemButton;
    }

    public BtnAdapter_scanbeacon(Activity activity,Context c, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to) {
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
            convertView = mInflater.inflate(R.layout.beacon_adapter, null);
            itemView = new ItemView();
            itemView.ItemImage = (ImageView)convertView.findViewById(valueViewID[0]);
            itemView.ItemName = (TextView)convertView.findViewById(valueViewID[1]);
            itemView.ItemAddress = (TextView)convertView.findViewById(valueViewID[2]);
            itemView.ItemUUID = (TextView)convertView.findViewById(valueViewID[3]);
            itemView.ItemRSSI = (TextView)convertView.findViewById(valueViewID[4]);
            itemView.ItemButton = (Button)convertView.findViewById(valueViewID[5]);
            convertView.setTag(itemView);
        }

        HashMap<String, Object> appInfo = mAppList.get(position);
        if (appInfo != null) {

            int mid = (Integer)appInfo.get(keyString[0]);
            String name = (String) appInfo.get(keyString[1]);
            String address = (String) appInfo.get(keyString[2]);
            String uuid = (String) appInfo.get(keyString[3]);
            String rssi = (String) appInfo.get(keyString[4]);
            int bid = (Integer)appInfo.get(keyString[5]);
            itemView.ItemName.setText("Name:"+name);
            itemView.ItemAddress.setText( "Address:"+address);
            itemView.ItemUUID.setText("UUID:"+uuid);
            itemView.ItemRSSI.setText("RSSI:"+rssi);
            itemView.ItemImage.setImageDrawable(itemView.ItemImage.getResources().getDrawable(mid));
            itemView.ItemButton.setBackgroundDrawable(itemView.ItemButton.getResources().getDrawable(bid));
            itemView.ItemButton.setOnClickListener(new ItemButton_Click(name,address,uuid,rssi));
        }

        return convertView;
    }

    class ItemButton_Click implements OnClickListener {
        private String name="null",address="null",uuid="null",rssi="null";

        ItemButton_Click(String name,String address,String uuid,String rssi) {
//            if (!name.equals(null)){
//                this.name = name;
//            }
//            if (!uuid.equals(null)){
//                this.uuid = uuid;
//            }
            this.address = address;
            this.rssi = rssi;
        }

        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if (vid == itemView.ItemButton.getId())
                checkDialog(this.name,this.address,this.uuid,this.rssi);
//                Log.v("abc",this.uuid);
        }
    }
    private void checkDialog(final String name, final String address, final String uuid, final String rssi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View mView = mActivity.getLayoutInflater().inflate(R.layout.newbeaconname,null);
        final EditText newname = (EditText)mView.findViewById(R.id.newname);
        Button checkname = (Button)mView.findViewById(R.id.checkname);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        checkname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertbcon(name,address,uuid,rssi,newname.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    public void insertbcon(final String name, final String address, final String uuid, final String rssi,final String newname){
        requestQueue = Volley.newRequestQueue(mContext);
        final StringRequest request = new StringRequest(Request.Method.POST, insert_beaconUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "加入成功", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("nnnmm", error.toString());
                Toast.makeText(mContext, "Error read insert_beacon.php!!!", Toast.LENGTH_LONG).show();
            }})
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id","6");
                parameters.put("UUID",uuid);
                parameters.put("address",address);
                parameters.put("RSSI",rssi);
                parameters.put("name",name);
                parameters.put("newname",newname);
                Log.d("nnnmm", parameters.toString());

                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }


}
