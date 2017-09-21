package com.example.carrie.carrie_test1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by mindy on 2017/9/12.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<PillListName> listDataHeader;
    private HashMap<String,ArrayList<PillListObject>> listHashMap;

    public ExpandableListAdapter(Context context,ArrayList<PillListName> listDataHeader, HashMap<String, ArrayList<PillListObject>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        Log.d("expand ","1");
    }

    @Override
    public int getGroupCount() {
        Log.d("expand getGroupCount", String.valueOf(listDataHeader.size()));
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("expand ","2");
        return listDataHeader.get(groupPosition).getTime_count()+1;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(String.valueOf(listDataHeader.get(groupPosition).getId())).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.d("expand ","3");
        String headerTitle = listDataHeader.get(groupPosition).getName();
        Log.d("expand headerTitle",headerTitle);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_pillschedule_name,null);
        }
        TextView listHeader = (TextView) convertView.findViewById(R.id.listHeader);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);
        Log.d("expand headerTitle1", (String) listHeader.getText());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        PillListObject pillListObject = listHashMap.get(String.valueOf(listDataHeader.get(groupPosition).getId())).get(childPosition);
        String displayDate = pillListObject.getEatDate();
        String displayTime="";
        try {
            if (pillListObject.getEatDate().equals("用藥日期")){
                displayDate = pillListObject.getEatDate();
            }else {
                displayDate = formatDate(pillListObject.getEatDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (pillListObject.getEatTime().equals("用藥時間")){
                displayTime = pillListObject.getEatTime();
            }else {
                displayTime = formatTime(pillListObject.getEatTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int finishCheck = pillListObject.getFinishcheck();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_pillschedule_1,null);
        }

        TextView listDate = (TextView) convertView.findViewById(R.id.nothingdisplay);
        listDate.setText(displayDate);

        TextView listTime = (TextView) convertView.findViewById(R.id.date1display);
        listTime.setText(displayTime);

        TextView listFinish = (TextView) convertView.findViewById(R.id.date2display);
        if (finishCheck==0){
            listFinish.setText("Ｘ");
        }else if(finishCheck ==1) {
            listFinish.setText("Ｏ");
        }else if (finishCheck==2){
            listFinish.setText("是否完成用藥");
        }
            listDate.setBackgroundColor(Color.parseColor("#DAF1F5"));
            listFinish.setBackgroundColor(Color.parseColor("#DAF1F5"));
            listTime.setBackgroundColor(Color.parseColor("#DAF1F5"));
        Log.d("return view","aaa");
        return convertView;
    }
    public static String formatTime(String dateString) throws ParseException {//時間格式轉換
        String strDate = "";
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");//format yyyy-MM-dd HH:mm:ss to HH:mm
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Calendar calendar = new GregorianCalendar();

        Date date = sdfDate.parse(dateString);
        calendar.setTime(date);

            strDate = sdf.format(date);

        Log.d("dateformat",strDate);
        return strDate;
    }
    public static String formatDate(String dateString) throws ParseException {//時間格式轉換
        String strDate = "";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//format yyyy-MM-dd HH:mm:ss to HH:mm
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        Calendar calendar = new GregorianCalendar();

        Date date = sdfDate.parse(dateString);
        calendar.setTime(date);

        strDate = sdf.format(date);

        Log.d("dateformat",strDate);
        return strDate;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
