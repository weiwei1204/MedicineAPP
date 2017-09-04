package com.example.carrie.carrie_test1;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by filipp on 9/16/2016.
 */
public class MesureAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<BsBpMeasureObject> my_measure;
    LayoutInflater inflater;
    int[] imageResource = {R.drawable.morning,R.drawable.noon,R.drawable.night};

    public MesureAdapter(Context context, ArrayList<BsBpMeasureObject> my_measure) {
        this.context = context;
        this.my_measure=my_measure;
        inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return my_measure.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.measure_list, parent,false);
        EditText medittextView = (EditText) convertView.findViewById(R.id.TimeMeasureDisplay);
        TextView mtextView = (TextView) convertView.findViewById(R.id.TimeMeasure);
        ImageView mimageView = (ImageView) convertView.findViewById(R.id.imageMeasure);
        mimageView.setImageResource(imageResource[position]);
        if(position==0){
            medittextView.setText(my_measure.get(0).getBs_first());
            mtextView.setText("早上");
        }else if(position==1){
            medittextView.setText(my_measure.get(0).getBs_second());
            mtextView.setText("中午");
        }else {
            medittextView.setText(my_measure.get(0).getBs_third());
            mtextView.setText("晚上");
        }


        return convertView;
    }

}
