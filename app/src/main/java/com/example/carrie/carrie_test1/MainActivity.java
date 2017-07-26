package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends LoginActivity {

    private ImageButton SignOut;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String googleid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");


//        SignOut = (ImageButton) findViewById(R.id.bkimg);
//        SignOut.setOnClickListener(this);
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.mainspinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(0,false);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
            if (parent.getItemAtPosition(position).equals("QR_Code")){
                gotoGenerate_Qrcode();
            }
            else if (parent.getItemAtPosition(position).equals("SignOut")){
                signOut();
            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



//    public void onClick(View v) {
//
//        switch (v.getId()) {
////            case R.id.btn_login:
////                break;
//            case R.id.bkimg:
//                signOut();
//                break;
//        }
//    }
    private  void  signOut() {

        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
        Intent it = new Intent(this,LoginActivity.class);
        startActivity(it);

    }

    public void gotoFirstActivity(View v){ //連到聊天機器人頁面

        Intent it = new Intent(this,RobotActivity.class);
        startActivity(it);
    }
    public void gotoSecondActivity(View v){ //連到iBeacon頁面
        Intent it = new Intent(this,SecondActivity.class);
        startActivity(it);
    }
    public void gotoChoice(View v){  //連到排程選擇頁面
        Intent it = new Intent(this,Choice.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FirstActivity.class);
        startActivity(it);
    }
    public void gotoGenerate_Qrcode(){ //連到製造qrcode頁面
        Intent it = new Intent(this,Generate_Qrcode.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoLoginActivity(View v){ //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,LoginActivity.class);
//        Log.d("hh","4");
//        LoginActivity la=new LoginActivity();
//        Log.d("hh","3");
//        la.signoutbtn="1";
////        la.signOut();
//        Log.d("hh","1");
//        startActivity(it);

    }
    public void goback(View v){
        finish();
    }
}
