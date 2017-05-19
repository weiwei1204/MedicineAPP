package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class thirdverify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirdverify);
    }
    public void gotoThirdActivity(View v){
        Intent it = new Intent(this,ThirdActivity.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }
}
