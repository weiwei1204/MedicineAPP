package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
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
        memberid= memberdata.getMember_id();
        my_google_id = memberdata.getGoogle_id();//get 自己google_ id
        my_mon_id = memberdata.getMy_mon_id();
//        Log.d("qwe123455",memberid);
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
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        if (isNetworkAvailable()){
                            Intent intent1 = new Intent(Choice.this,MonitorActivity.class);
                            Bundle bundle1 = new Bundle();
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }
                        else {
                            networkCheck();
                        }
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(Choice.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(Choice.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(Choice.this, MyBeaconActivity.class);
                        Bundle bundle4 = new Bundle();
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                        
                }


                return false;
            }
        });

    }

    public void gotoThirdActivity(View v){  //連到用藥排成設定頁面
        Intent it = new Intent(this,m_calendarlist.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }
    public void gotoEnterBsBpActivity(View v){
        Intent it = new Intent(this, EnterBsBpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        bundle.putString("my_google_id", my_google_id);
        bundle.putString("my_supervise_id", my_mon_id);
        it.putExtras(bundle);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }
    public  boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public  void networkCheck() {
        new AlertDialog.Builder(this)
                .setMessage("請確認網路連線")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

}
