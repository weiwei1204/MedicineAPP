package com.example.carrie.carrie_test1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChartActivity extends AppCompatActivity {

    int monitor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("chart","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Log.d("chart","2");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("chart","3");
        Bundle bundle = getIntent().getExtras();
        Log.d("chart","4");
        monitor_id = bundle.getInt("monitor_who");//get 自己 id
        Log.d("chart","5");
        Log.d("chart_id", String.valueOf(monitor_id));
        TextView monitorId=(TextView) findViewById(R.id.monitor_id);
        monitorId.setText(String.valueOf(monitor_id));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
