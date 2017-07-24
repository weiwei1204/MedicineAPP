package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FourthActivity.class);
        startActivity(it);
    }

    public void gotoScanner(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,Scanner.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }
}
