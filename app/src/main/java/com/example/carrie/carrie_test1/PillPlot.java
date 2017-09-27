package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PillPlot extends Fragment {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<PillListName> listDataHeader;
    private HashMap<String, ArrayList<PillListObject>> listHashMap;

    RequestQueue requestQueue;
    String monitor_id;
    String objectArray;
    String objectDetailArray;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tab3_pillschedule, container, false);
        listView = (ExpandableListView) rootView.findViewById(R.id.showpillschedule);
        //initData();
        Bundle bundle = this.getArguments();
        monitor_id = bundle.getString("memberid");
        objectArray = bundle.getString("objectArray");
        objectDetailArray = bundle.getString("objectDetailArray");
        Log.d("testExpand mon", monitor_id);
        Log.d("testExpand objectArray", objectArray);
        Log.d("testExpand objectArray", objectDetailArray);
        try {
            if (objectArray.equals("nodata")) {
                nodateCheck();
            } else {
                initObjectData(objectArray, objectDetailArray);
                Log.d("testExpand", "4");
                listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listHashMap);
                Log.d("testExpand", "5");
                listView.setAdapter(listAdapter);
                Log.d("testExpand size", String.valueOf(listDataHeader.size()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_swipe_plot, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_myinfo) {

            return true;
        }
        if (id == R.id.action_myschedule) {
            gotoschedule();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoschedule() {
        Intent it = new Intent(this.getActivity(), Choice.class);
        this.startActivity(it);
        Toast.makeText(getActivity(), "have done this method!", Toast.LENGTH_SHORT).show();

    }

    private void initObjectData(String NameobjectArray, String TimeobjectArray) throws JSONException {
        listDataHeader = new ArrayList<PillListName>();
        listHashMap = new HashMap<>();

        JSONArray jsonArray = new JSONArray(NameobjectArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Get current json object
            JSONObject pillListNameObject = jsonArray.getJSONObject(i);
            PillListName pillListName = new PillListName(pillListNameObject.getInt("Medicine_calendar_id"), pillListNameObject.getString("name"), pillListNameObject.getInt("day"), pillListNameObject.getInt("finish"), pillListNameObject.getInt("count(time)"));
            Log.d("getMonitorPillsRecord", pillListNameObject.getString("Medicine_calendar_id"));
            listDataHeader.add(pillListName);
            Log.d("getMonitorPillsRecord", String.valueOf(listDataHeader.size()));

            Log.d("getMonitorPillsRecord", "1");
        }

        for (int s = 0; s < jsonArray.length(); s++) {
            JSONArray jsonArrayTime = new JSONArray(TimeobjectArray);
            ArrayList<PillListObject> pillListObjectsArray = new ArrayList<PillListObject>();
            PillListObject pillListObjectTitle = new PillListObject(1, listDataHeader.get(s).getId(), "用藥日期", "用藥時間", 2);
            pillListObjectsArray.add(pillListObjectTitle);
            for (int i = 0; i < jsonArrayTime.length(); i++) {
                // Get current json object
                JSONObject pillListTimeObject = jsonArrayTime.getJSONObject(i);
                if (listDataHeader.get(s).getId() == pillListTimeObject.getInt("id")) {
                    Log.d("getMonitorPillsRecord", String.valueOf(listDataHeader.get(s).getId()));
                    Log.d("getMonitorPillsRecord", pillListTimeObject.getString("id"));
                    PillListObject pillListObject1 = new PillListObject(1, pillListTimeObject.getInt("id"), pillListTimeObject.getString("date"), pillListTimeObject.getString("time"), pillListTimeObject.getInt("finish"));
                    Log.d("getMonitorPillsRecord", pillListTimeObject.getString("date"));
                    Log.d("getMonitorPillsRecord", pillListTimeObject.getString("time"));

                    pillListObjectsArray.add(pillListObject1);
                }
            }
            listHashMap.put(String.valueOf(listDataHeader.get(s).getId()), pillListObjectsArray);
            Log.d("getMonitorPillsRecord", listHashMap.get(String.valueOf(listDataHeader.get(s).getId())).toString());
        }


//        PillListObject pillListObject1 = new PillListObject(1,418,"2017-09-08","18:03:00",1);
//        PillListObject pillListObject2 = new PillListObject(2,418,"2017-09-09","19:03:00",0);
//        PillListObject pillListObject3 = new PillListObject(3,418,"2017-09-010","18:03:00",0);
//        Log.d("testExpand","2");
//        pillListObjectsArray.add(pillListObject4);
//        pillListObjectsArray.add(pillListObject1);
//        pillListObjectsArray.add(pillListObject2);
//        pillListObjectsArray.add(pillListObject3);
//
//        listHashMap.put(String.valueOf(listDataHeader.get(0).getId()),pillListObjectsArray);
//        Log.d("testExpand",listHashMap.get("418").get(0).getEatDate().toString());
//        Log.d("testExpand", "size :"+String.valueOf(listHashMap.size()));
    }

    public void goback(View v) {
        getActivity().onBackPressed();
    }

    public void nodateCheck() {
        new AlertDialog.Builder(getActivity())
                .setMessage("近期無用藥紀錄")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }
}
