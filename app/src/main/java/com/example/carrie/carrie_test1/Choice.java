package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Choice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
    }

    public void gotoThirdActivity(View v){  //連到用藥排成設定頁面
        Intent it = new Intent(this,ThirdActivity.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }

}
