
package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by rita on 2017/5/20.
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EditText txtTime;
    TextView settimetxt;
    long settime;
    int year,month,day;

    public TimeDialog(View view,TextView textview){
//        final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.activity_edt, null);
        txtTime=(EditText)view;
        Log.d("fffff",view.toString());
        settimetxt=textview;
        Log.d("fffff1",settimetxt.toString());

    }


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c=Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        day=c.get(Calendar.DAY_OF_MONTH);
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);
        Log.d("now", String.valueOf(year));
        Log.d("now", String.valueOf(month));
        Log.d("now", String.valueOf(day));
        Log.d("now", String.valueOf(c.getTime()));
        return new TimePickerDialog(getActivity(), this,hour,minute, DateFormat.is24HourFormat(getActivity()));

    }
    public void onTimeSet(TimePicker view, int hour, int minute){
        String time;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute,0);
        Log.d("now",calendar.toString());
        Log.d("now",calendar.getTime().toString());
        settime=calendar.getTimeInMillis();
        Log.d("now", String.valueOf(settime));
        if (hour < 10 && minute < 10){
            time="0"+hour+":"+"0"+minute;
        }
        else if (hour < 10 && minute > 10){
            time="0"+hour+":"+minute;
        }
        else if (hour > 10 && minute < 10){
            time=hour+":"+"0"+minute;
        }
        else {
            time=hour+":"+minute;
        }
        txtTime.setText(time);
        Log.d("aaa", String.valueOf(settime));
        String set = String.valueOf(settime);
        Log.d("aaa",set);
        settimetxt.setText(set);
    }




}
