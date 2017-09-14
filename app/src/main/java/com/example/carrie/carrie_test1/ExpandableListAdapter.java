package com.example.carrie.carrie_test1;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataHeader.get(groupPosition).getTime_count();
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
        String headerTitle = listDataHeader.get(groupPosition).getName();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_pillschedule_name,null);
        }
        TextView listHeader = (TextView) convertView.findViewById(R.id.listHeader);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("testexpand 1",listHashMap.get("418").get(0).getEatDate());
        PillListObject pillListObject = listHashMap.get(String.valueOf(listDataHeader.get(groupPosition).getId())).get(childPosition);
        String displayDate = pillListObject.getEatDate();
        String displayTime = pillListObject.getEatTime();
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
        }else {
            listFinish.setText("Ｏ");
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
