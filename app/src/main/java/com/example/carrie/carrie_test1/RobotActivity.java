package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class RobotActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
    }

    public void gotoChatActivity(View v) { //連到chat頁面
        Intent it = new Intent(this, chat.class);

    }
    public void gotologinActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,LoginActivity.class);

        startActivity(it);
    }
    public void goback(View v){
        finish();
    }
}
