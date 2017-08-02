package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Choice extends Activity {

    String memberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        Log.d("qwe123455",memberid);

    }

    public void gotoThirdActivity(View v){  //連到用藥排成設定頁面
        Intent it = new Intent(this,ThirdActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }

}
