package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rita on 2017/9/19.
 */

public class myspinner extends ArrayAdapter{

    int groupid;
    Activity context;
    LayoutInflater inflater;
    ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    private static String bconid;
    public myspinner(Activity context, int groupid, int id, ArrayList<ArrayList<String>> list){
        super(context,id,list);
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid =groupid;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = inflater.inflate(groupid,parent,false);
        TextView txt = (TextView)itemView.findViewById(R.id.mysipnner);
        txt.setText(list.get(position).get(0).toString());
        return itemView;
    }

    public View getDropDownView(int position,View convertView,ViewGroup parent){
        return getView(position,convertView,parent);
    }



}
