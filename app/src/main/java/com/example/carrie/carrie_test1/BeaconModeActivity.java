package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class BeaconModeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private FloatingActionButton fabscanap;
    String memberid;
    private ListView lv ;
    private Switch modeSwitch ;

    RequestQueue requestQueue;
    private static int mode = 1;
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";
    String getm_BeaconUrl = "http://54.65.194.253/Beacon/getm_Beacon.php";
    private SwipeRefreshLayout laySwipe;
    private ArrayList<String> timeArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beaconmode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        memberid=memberdata.getMember_id();
        modeSwitch = (Switch) findViewById(R.id.switch3);
        modeSwitch.setOnCheckedChangeListener(BeaconModeActivity.this);
        if(mode == 1){
            findViewById(R.id.linearlayout1).setVisibility( View.INVISIBLE );
            findViewById(R.id.linearlayout2).setVisibility( View.INVISIBLE );
            modeSwitch.setChecked(false);
        }else{
            modeSwitch.setChecked(true);
        }
        for (int i = 0; i < 7; i ++){
            timeArray.add(Integer.toString(i+1));
        }
        timeArray.set(5,"10");
        timeArray.set(6,"15");
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        modespinner myspinner = new modespinner(BeaconModeActivity.this,R.layout.modespinner,R.id.spinner,timeArray);
        spinner.setAdapter(myspinner);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                            Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"selected",Toast.LENGTH_LONG).show();
////                            beaconUUID= (String) parent.getItemAtPosition(position);
//                beaconid=bconarray.get(position).get(1);
//                Log.d("bbbbbbcon", beaconid);
//            }
//        ArrayAdapter<CharSequence> arrAdapSpn = ArrayAdapter.createFromResource(
//                BeaconModeActivity.this, //對應的Context
//                R.array.spinner_list, //選項資料內容
//                R.layout.spinner_item); //自訂getView()介面格式(Spinner介面未展開時的View)
//        arrAdapSpn.setDropDownViewResource(R.layout.spinner_dropdown_item); //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
//        spinner.setAdapter(arrAdapSpn); //將宣告好的 Adapter 設定給 Spinner
        spinner.setOnItemSelectedListener(spnRegionOnItemSelected);



        //bottom bar設定
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_list:
                        Intent intent0 = new Intent(BeaconModeActivity.this, Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("memberid", memberdata.getMember_id());
                        bundle0.putString("my_google_id", memberdata.getGoogle_id());
                        bundle0.putString("my_supervise_id", memberdata.getMy_mon_id());
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(BeaconModeActivity.this, MonitorActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("my_id", memberdata.getMember_id());
                        bundle1.putString("my_google_id", memberdata.getGoogle_id());
                        bundle1.putString("my_supervise_id", memberdata.getMy_mon_id());
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(BeaconModeActivity.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", memberdata.getGoogle_id());
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(BeaconModeActivity.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", memberdata.getMember_id());
                        bundle3.putString("my_google_id", memberdata.getGoogle_id());
                        bundle3.putString("my_supervise_id", memberdata.getMy_mon_id());
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(BeaconModeActivity.this, BeaconModeActivity.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("memberid", memberdata.getMember_id());
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switch3:
                if(compoundButton.isChecked() && mode == 1){
                    Toast.makeText(this,"離線掃描開啟",Toast.LENGTH_SHORT).show();
                    findViewById(R.id.linearlayout1).setVisibility( View.VISIBLE );
                    findViewById(R.id.linearlayout2).setVisibility( View.VISIBLE );
                    mode = 2;
                    stopService(new Intent(BeaconModeActivity.this, CheckBeacon_AP.class));
                    Intent checkBeaconIntent = new Intent(BeaconModeActivity.this,CheckBeacon_Time.class);
                    startService(checkBeaconIntent);
                }else if (compoundButton.isChecked() == false){
                    Toast.makeText(this,"離線掃描關閉",Toast.LENGTH_SHORT).show();
                    findViewById(R.id.linearlayout1).setVisibility( View.INVISIBLE );
                    findViewById(R.id.linearlayout2).setVisibility( View.INVISIBLE );
                    mode = 1;
                    stopService(new Intent(BeaconModeActivity.this, CheckBeacon_Time.class));
                    Intent checkBeaconIntent = new Intent(BeaconModeActivity.this,CheckBeacon_AP.class);
                    startService(checkBeaconIntent);
                }
                break;
        }
    }
    private AdapterView.OnItemSelectedListener spnRegionOnItemSelected = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
            // TODO Auto-generated method stub
        }
    };

    public void goback(View v){
        finish();
    }
}
