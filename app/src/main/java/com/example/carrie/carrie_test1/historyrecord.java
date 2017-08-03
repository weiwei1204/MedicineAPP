package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class historyrecord extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyrecord);
    }

    public void gotoThirdActivity(View v){
        Intent it = new Intent(this,ThirdActivity.class);
        startActivity(it);
    }

    public void gotocomment(View v){
        Intent it = new Intent(this,comment.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }
}


