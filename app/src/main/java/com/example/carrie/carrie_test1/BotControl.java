package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BotControl extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BotControl";
    private Button textDemoButton;
    private Button speechDemoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_control);
        init();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void init() {
        Log.e(TAG, "Initializing app");
        textDemoButton = (Button) findViewById(R.id.button_select_text);
        speechDemoButton = (Button) findViewById(R.id.button_select_voice);
        textDemoButton.setOnClickListener(this);
        speechDemoButton.setOnClickListener(this);
    }
    @Override
    public void onClick(final View v) {
        switch ((v.getId())) {
            case R.id.button_select_text:
                Intent textIntent = new Intent(this, TextActivity.class);
                startActivity(textIntent);
                break;
            case R.id.button_select_voice:
                Intent voiceIntent = new Intent(this,LexConnect.class);
                startActivity(voiceIntent);
                break;
        }
    }
}
