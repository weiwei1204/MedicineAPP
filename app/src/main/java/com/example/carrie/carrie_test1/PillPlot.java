package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PillPlot extends Fragment {
    private ExpandableListView listView ;
    private ExpandableListAdapter listAdapter;
    private ArrayList<PillListName> listDataHeader;
    private HashMap<String,ArrayList<PillListObject>> listHashMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_pillschedule, container, false);
        listView = (ExpandableListView) rootView.findViewById(R.id.showpillschedule);
        initData();
        Log.d("testExpand","4");
        listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(),listDataHeader,listHashMap);
        Log.d("testExpand","5");
        listView.setAdapter(listAdapter);
        Log.d("testExpand","6");
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
        if(id == R.id.action_settings){
            return true;
        }
        if(id == R.id.action_myinfo){

            return true;
        }
        if(id == R.id.action_myschedule){
            gotoschedule();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void gotoschedule(){
        Intent it = new Intent(this.getActivity(),Choice.class);
        this.startActivity(it);
        Toast.makeText(getActivity(), "have done this method!", Toast.LENGTH_SHORT).show();

    }
    private void  initData(){
        listDataHeader = new ArrayList<PillListName>();
        listHashMap = new HashMap<>();
        ArrayList<PillListObject> pillListObjectsArray= new ArrayList<PillListObject>();
        PillListName pillListName = new PillListName(418,"doremi",3,0,3);

        listDataHeader.add(pillListName);
        Log.d("testExpand","1");
        PillListObject pillListObject1 = new PillListObject(1,418,"2017-09-08","18:03:00",1);
        PillListObject pillListObject2 = new PillListObject(2,418,"2017-09-09","19:03:00",0);
        PillListObject pillListObject3 = new PillListObject(3,418,"2017-09-010","18:03:00",0);
        Log.d("testExpand","2");
        pillListObjectsArray.add(pillListObject1);
        pillListObjectsArray.add(pillListObject2);
        pillListObjectsArray.add(pillListObject3);

        listHashMap.put(String.valueOf(listDataHeader.get(0).getId()),pillListObjectsArray);
        Log.d("testExpand",listHashMap.get("418").get(0).getEatDate().toString());
        Log.d("testExpand", "size :"+String.valueOf(listHashMap.size()));
    }
    public void goback(View v)
    {
        getActivity().onBackPressed();
    }
}
