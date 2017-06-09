package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends Activity {




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void gotoMainActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }



    }
