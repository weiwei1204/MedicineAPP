package com.example.carrie.carrie_test1;

/**
 * Created by rita on 2017/7/19.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyLayoutOperation extends AppCompatActivity{
    private int count=0;

    public static void display(final Activity activity, Button btn)
    {
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
                java.util.ArrayList<String> msg = new ArrayList<String>();
                for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
                {
                    LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
                    TextView edit = (TextView) innerLayout.findViewById(R.id.settimetxt);
                    msg.add(edit.getText().toString());
                }
                Toast t = Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    public void add(final Activity activity, ImageButton btn)
    {
        final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
        btn.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.activity_edt, null);
                newView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageButton btnRemove = (ImageButton) newView.findViewById(R.id.btnRemove);
                btnRemove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        linearLayoutForm.removeView(newView);
                    }
                });
                linearLayoutForm.addView(newView);
            }
        });
    }

//    public void show(){
//        final Calendar c=Calendar.getInstance();
//        int hour=c.get(Calendar.HOUR_OF_DAY);
//        int minute=c.get(Calendar.MINUTE);
//        return new TimePickerDialog(getActivity(), this,hour,minute, DateFormat.is24HourFormat(getActivity()));
//    }
//    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//
//        try {
//
//            Field childFragmentManager = android.app.Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//
//        } catch (NoSuchFieldException e) {
//            Log.d("ffffffff1",e.toString());
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            Log.d("ffffffff2",e.toString());
//            throw new RuntimeException(e);
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void  onStart(){//設定日期和時間
//super.onStart();
//                Log.d("fffff","5");
//
//        LinearLayout edt = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_edt, null);
//        EditText txtTime= (EditText) edt.findViewById(R.id.timetxt);
//        Log.d("fffff","6");
//
////        EditText txtTime=(EditText)findViewById(R.id.timetxt);//時間
//        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.d("fffff","7");
//
//                if (hasFocus){
//                    TimeDialog tdialog=new TimeDialog(v);
//                    Log.d("fffff","8");
//                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
//                    tdialog.show(ft,"TimePicker");
//                }
//            }
//        });
//
//    }
}