package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by rita on 2017/5/19.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtDate;
    public DateDialog(View view){
        txtDate=(EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(getActivity(), this,year,month,day);

    }

    public void onDateSet(DatePicker view,int year,int month,int day){
        String dbmonth="-",dbday="-";
        if (month+1<10){
            dbmonth="-0";
        }
        if (day<10){
            dbday="-0";
        }
        String date=year+dbmonth+(month+1)+dbday+day;
        txtDate.setText(date);
    }

}
