package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends LoginActivity {

    private ImageButton SignOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignOut = (ImageButton) findViewById(R.id.bkimg);
        SignOut.setOnClickListener(this);

    }
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.btn_login:
//                break;
            case R.id.bkimg:
                signOut();
                break;
        }
    }
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
    public void gotoThirdActivity(View v){  //連到用藥排成設定頁面
        Intent it = new Intent(this,ThirdActivity.class);
        startActivity(it);
    }
    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FirstActivity.class);
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
