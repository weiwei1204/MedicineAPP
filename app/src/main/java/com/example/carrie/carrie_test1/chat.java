package com.example.carrie.carrie_test1;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class chat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    public void goback(View v){
        finish();
    }
}
