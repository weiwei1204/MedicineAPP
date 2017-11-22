package com.example.carrie.carrie_test1;


import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by filipp on 9/16/2016.
 */
public class MesureBpAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<BsBpMeasureObject> my_measure;
    LayoutInflater inflater;
    String bp1 ;
    String bp2 ;
    String bp3 ;
    int[] imageResource = {R.drawable.morning,R.drawable.noon,R.drawable.night};

    public MesureBpAdapter(Context context, ArrayList<BsBpMeasureObject> my_measure) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.measure_list, parent,false);
        final EditText medittextView = (EditText) convertView.findViewById(R.id.TimeMeasureDisplay);
        TextView mtextView = (TextView) convertView.findViewById(R.id.TimeMeasure);
        ImageView mimageView = (ImageView) convertView.findViewById(R.id.imageMeasure);
        mimageView.setImageResource(imageResource[position]);
        if(position==0){
            medittextView.setText(memberdata.getBp_first());
            mtextView.setText("早上");

        }else if(position==1){
            medittextView.setText(memberdata.getBp_second());
            mtextView.setText("中午");

        }else {
            medittextView.setText(memberdata.getBp_third());
            mtextView.setText("晚上");
        }
        medittextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"helllo : "+position,Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;

                timePickerDialog = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
                        if(minute<10&&hourOfDay<10){
                            medittextView.setText("0"+hourOfDay+":0"+minute);
//                            bp_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
//                            Log.d("bp_third : ",bp_third);
                        }else if(minute<10){
                            medittextView.setText(hourOfDay+":0"+minute);
//                            bp_third = "2017-09-18 "+String.valueOf(hourOfDay)+":0"+String.valueOf(minute)+":00";
//                            Log.d("bp_third : ",bp_third);
                        }
                        else if(hourOfDay<10){
                            medittextView.setText("0"+hourOfDay+":"+minute);
//                            bp_third = "2017-09-18 0"+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
//                            Log.d("bp_third : ",bp_third);
                        }
                        else {
                            medittextView.setText(hourOfDay+":"+minute);
//                            bp_third = "2017-09-18 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
//                            Log.d("bp_third : ",bp_third);
                        }
//                        medittextView.setText(hourOfDay+":"+minute);
                    }
                },hour,minute,true);

                timePickerDialog.show();
            }
        });





        return convertView;
    }

}
