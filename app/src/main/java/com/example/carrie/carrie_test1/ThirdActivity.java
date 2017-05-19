package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void  onStart(){
        super.onStart();
        EditText txtDate=(EditText)findViewById(R.id.txtdate);
                txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            DateDialog dialog=new DateDialog(v);
                            android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                            dialog.show(ft,"DatePicker");
                        }
                    }
                });
        /*
        super.onStart();
        EditText txtTime=(EditText)findViewById(R.id.timetxt);
        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onFocusChange(View v1, boolean hasFocus) {
                if (hasFocus){
                    TimeDialog tdialog=new TimeDialog(v1);
                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
                    tdialog.show(ft,"TimePicker");
                }
            }
        });
        */
    }


    public void goback(View v){
        finish();
    }
}
