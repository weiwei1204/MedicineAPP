package com.example.carrie.carrie_test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RobotActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myBeacon);

    }


    public void goback(View v){
        finish();
    }
}
