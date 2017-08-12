package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;

public class Beacon extends Activity {
    public  String my_google_id = "";
    public  String my_id = "";
    public  String my_mon_id = "";//Supviser的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon2);
        Bundle bundle = getIntent().getExtras();
        my_id = bundle.getString("my_id");//get 自己 id
        my_google_id = bundle.getString("my_google_id");//get 自己google_ id
        my_mon_id = bundle.getString("my_supervise_id");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_list:
                        Intent intent0 = new Intent(Beacon.this,Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("my_id", my_id);
                        bundle0.putString("my_google_id", my_google_id);
                        bundle0.putString("my_supervise_id", my_mon_id);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(Beacon.this,MonitorActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("my_id", my_id);
                        bundle1.putString("my_google_id", my_google_id);
                        bundle1.putString("my_supervise_id", my_mon_id);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(Beacon.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", my_google_id);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(Beacon.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", my_id);
                        bundle3.putString("my_google_id", my_google_id);
                        bundle3.putString("my_supervise_id", my_mon_id);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(Beacon.this, Beacon.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("my_id", my_id);
                        bundle4.putString("my_google_id", my_google_id);
                        bundle4.putString("my_supervise_id", my_mon_id);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

}
