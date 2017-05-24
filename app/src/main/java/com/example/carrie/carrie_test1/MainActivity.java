package com.example.carrie.carrie_test1;

import android.app.ActionBar;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
