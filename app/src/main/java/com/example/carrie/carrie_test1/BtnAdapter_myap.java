package com.example.carrie.carrie_test1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class BtnAdapter_myap extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] keyString;
    private int[] valueViewID;

    private ItemView itemView;

    private class ItemView {
        ImageView ItemImage;
        TextView ItemSSID;
        TextView ItemBSSID;
        TextView ItemCapabilities;
        TextView ItemLevel;
        TextView ItemFrequency;
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
            itemView.ItemButton.setOnClickListener(new ItemButton_Click(ssid));
        }

        return convertView;
    }

    class ItemButton_Click implements OnClickListener {
        private String ssid;

        ItemButton_Click(String pos) {
            ssid = pos;
        }

        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if (vid == itemView.ItemButton.getId())
                Log.v("abc",ssid);
        }
    }
}
