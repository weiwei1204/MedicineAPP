package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class medicinevertify extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinevertify);
    }
    public void gotothirdverify(View v){ //連到親友認證頁面
        Intent it = new Intent(this,thirdverify.class);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }


}
