package com.example.carrie.carrie_test1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by User on 2017/8/25.
 */
public class MyAPActivity extends AppCompatActivity {
    private FloatingActionButton fabscanap;
    String memberid;
    private Context context;
    private ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("memberid");
        context = this ;
        lv = (ListView) findViewById(R.id.mlistView);
        Log.d("test2",memberid);
        fabscanap = (FloatingActionButton)findViewById(R.id.fabscanap);
        final Intent it = new Intent(this,ScanAPActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("memberid", memberid);
        it.putExtras(bundle1);   // 記得put進去，不然資料不會帶過去哦
        fabscanap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(it);
            }
        });
        myWifiList();
    }
    public void myWifiList(){
        ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<5; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.wifi);
            map.put("ItemSSID", "SSID:" + i);
            map.put("ItemBSSID", "BSSID:");
            map.put("ItemCapabilities","");
            map.put("ItemLevel", "信號強度:");
            map.put("ItemFrequency", "頻道:");
            map.put("ItemButton", R.drawable.add);
            Item.add(map);
        }
        BtnAdapter_myap btnadapter_myap = new BtnAdapter_myap(context, Item, R.layout.ap_adapter,
                new String[]{"ItemImage","ItemSSID", "ItemBSSID", "ItemCapabilities","ItemLevel","ItemFrequency","ItemButton"},
                new int[] {R.id.ItemImage,R.id.ItemSSID,R.id.ItemBSSID,R.id.ItemCapabilities,R.id.ItemLevel,R.id.ItemFrequency,R.id.ItemButton});
        lv.setAdapter(btnadapter_myap);
    }
    public void goback(View v){
        finish();
    }
}
