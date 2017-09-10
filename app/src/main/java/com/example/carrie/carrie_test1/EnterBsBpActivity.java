package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EnterBsBpActivity extends AppCompatActivity {
    private PagerAdapter BsBpPagerAdapter;
    private ViewPager BsBpViewPager;
    public static String membercurrentid;
    Button btn ;
    public static String my_mon_id;
    public static String my_google;
    String insertbpvalue = "http://54.65.194.253/Health_Calendar/insertbloodpressure.php";
    String insertbsvalue = "http://54.65.194.253/Health_Calendar/insertbloodsugar.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bs_bp);
        Bundle bundle = getIntent().getExtras();
        membercurrentid = bundle.getString("memberid");
        my_mon_id = bundle.getString("my_supervise_id");
        my_google = bundle.getString("my_google_id");

        BsBpPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        BsBpViewPager = (ViewPager) findViewById(R.id.container2);

        BsBpViewPager.setAdapter(BsBpPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab2);

        tabLayout.setupWithViewPager(BsBpViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.blood);
        tabLayout.getTabAt(1).setIcon(R.drawable.blood2);

        btn = (Button) findViewById(R.id.backmain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backmain();

            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class PagerAdapter extends FragmentPagerAdapter {
        Bundle bundle = getIntent().getExtras();
        String memberid=bundle.getString("memberid");

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BsRecord tab1 = new BsRecord();
                    Intent it1 = new Intent();
                    it1.putExtra("memberid",memberid);
                    return tab1;
                case 1:
                    BpRecord tab2 = new BpRecord();
                    Intent it = new Intent();
                    it.putExtra("memberid",memberid);
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "血糖";
                case 1:
                    return "血壓";

            }
            return null;
        }

    }
    public void backmain(){
        Intent it = new Intent(EnterBsBpActivity.this,SwipePlot.class);
        it.putExtra("memberid",membercurrentid);
        it.putExtra("googleid",my_google);
        it.putExtra("my_supervise_id",my_mon_id);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }
    }

