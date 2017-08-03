package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScheduleActivity extends Activity {
    private ListView listView;
    private ListView listView1;

    private String[] list = {"感冒","頭痛","流鼻水"};
    private String[] list1 = {"100%","50%","75%"};
    private ArrayAdapter<String> listAdapter;
    private ArrayAdapter<String> listAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        listView = (ListView) findViewById(R.id.schedule_list_view);
        listAdapter = new ArrayAdapter(this, R.layout.schedulerow, R.id.textView38, list);
        listView.setAdapter(listAdapter);

        listView1 = (ListView) findViewById(R.id.schedule_list_view);
        listAdapter1 = new ArrayAdapter(this, R.layout.schedulerow, R.id.textView39, list1);
        listView1.setAdapter(listAdapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ScheduleActivity.this,ThirdActivity.class);

            }
        });
    }

    public void goback(View v){
        finish();
    }
}
