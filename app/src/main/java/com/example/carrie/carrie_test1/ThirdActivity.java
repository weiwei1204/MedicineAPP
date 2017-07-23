package com.example.carrie.carrie_test1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ThirdActivity extends AppCompatActivity {


    Button btnDisplay;
    ImageButton btnAdd;
    EditText txtTime;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        MyLayoutOperation.display(this, btnDisplay);
//        MyLayoutOperation.add(this, btnAdd);
        MyLayoutOperation myLayoutOperation=new MyLayoutOperation();
        myLayoutOperation.add(this,btnAdd);
//        myLayoutOperation.time();
        LinearLayout edt = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_edt, null);
        txtTime= (EditText) edt.findViewById(R.id.timetxt);
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void onClick(View v) {
//                TimeDialog tdialog=new TimeDialog(v);
//                android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
//                tdialog.show(ft,"TimePicker");
//                Log.d("fffff","4");
//
//            }
//        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void  onStart(){//設定日期和時間
        super.onStart();
        EditText txtDate=(EditText)findViewById(R.id.txtdate);//日期
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
//        super.onStart();
//        LinearLayout scrollViewlinerLayout = (LinearLayout) findViewById(R.id.linearLayoutForm);
//        for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
//        {
//            LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
//            EditText edit = (EditText) innerLayout.findViewById(R.id.timetxt);
//            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    TimeDialog tdialog=new TimeDialog(v);
//                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
//                    tdialog.show(ft,"TimePicker");
//                }
//            }
//        });
//        }

//        super.onStart();
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimeDialog tdialog=new TimeDialog(v);
//                android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
//                tdialog.show(ft,"TimePicker");
//            }
//        });
//        super.onStart();
//        LinearLayout edt = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_edt, null);
//        EditText txtTime= (EditText) edt.findViewById(R.id.timetxt);
////        EditText txtTime=(EditText)findViewById(R.id.timetxt);//時間
//            Log.d("fff","okokokok");
//
//        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.d("fff","ok1111");
//                if (hasFocus){
//                    Log.d("fff","ok222");
//                    TimeDialog tdialog=new TimeDialog(v);
//                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();
//                    tdialog.show(ft,"TimePicker");
//                }
//            }
//
//        });

    }





    public void gotoalarm(View v){ //連到親友認證頁面
        Intent it = new Intent(this,alarm.class);
        startActivity(it);
    }


    public void gotohistoryrecord(View v){ //連到親友認證頁面
        Intent it = new Intent(this,historyrecord.class);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }



    /*
    public void gotoThirdverify(View v){
        Intent it = new Intent(this,thirdverify.class);
        startActivity(it);
    }
    */
}
