package com.example.carrie.carrie_test1;

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
    public void gotoFirstActivity(View v){
        Intent it = new Intent(this,FirstActivity.class);
        startActivity(it);
    }
    public void gotoSecondActivity(View v){
        Intent it = new Intent(this,SecondActivity.class);
        startActivity(it);
    }
    public void gotoThirdActivity(View v){
        Intent it = new Intent(this,ThirdActivity.class);
        startActivity(it);
    }
}
