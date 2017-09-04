package com.example.carrie.carrie_test1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mindy on 2017/9/4.
 */
public class BsMeasureTab extends Fragment{
    ListView bsmeasure;
    private ArrayList<BsBpMeasureObject> my_measure =new ArrayList<BsBpMeasureObject>();
    MesureAdapter mesureAdapter ;
    String[] measure_time={"9:00","12:00","18:00"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bsmeature_tab1, container, false);
        bsmeasure = (ListView)rootView.findViewById(R.id.bsfirst);
        BsBpMeasureObject bs = new BsBpMeasureObject(1,"1","9:00","12:00","18:00","9:00","12:00","18:00");
        my_measure.add(bs);
        mesureAdapter=new MesureAdapter(getActivity(),my_measure);
        bsmeasure.setAdapter(mesureAdapter);

        bsmeasure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你按下"+measure_time[position], Toast.LENGTH_SHORT).show();
            }

        });
        return rootView;
    }

}
