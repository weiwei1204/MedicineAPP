package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Choice extends Activity {

    String memberid;
    public static String my_google_id = "";
    public static String my_mon_id = "";//欲監控對象的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Bundle bundle = getIntent().getExtras();
        memberid=bundle.getString("my_id");
        my_google_id = bundle.getString("my_google_id");//get 自己google_ id
        my_mon_id = bundle.getString("my_supervise_id");
        Log.d("qwe123455",memberid);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_list:
                        Intent intent0 = new Intent(Choice.this,Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("my_id", memberid);
                        bundle0.putString("my_google_id", my_google_id);
                        bundle0.putString("my_supervise_id", my_mon_id);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(Choice.this,MonitorActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("my_id", memberid);
                        bundle1.putString("my_google_id", my_google_id);
                        bundle1.putString("my_supervise_id", my_mon_id);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(Choice.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", my_google_id);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(Choice.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", memberid);
                        bundle3.putString("my_google_id", my_google_id);
                        bundle3.putString("my_supervise_id", my_mon_id);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(Choice.this, Beacon.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("my_id", memberid);
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

    public void gotoThirdActivity(View v){  //連到用藥排成設定頁面
        Intent it = new Intent(this,ThirdActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void gotoEnterBsBpActivity(View v){
        Intent it = new Intent(this, EnterBsBpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }

}
