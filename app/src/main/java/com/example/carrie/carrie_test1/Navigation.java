package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/8.
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
public class Navigation extends AppCompatActivity {
    private static final String TAG = "Navigation";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.analytics);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.presentation);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.firstbtn:

                        break;

                    case R.id.secondbtn:
                        Intent intent1 = new Intent(Navigation.this, MonitorActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.thirdbtn:
                        Intent intent2 = new Intent(Navigation.this, SwipePlot.class);
                        startActivity(intent2);
                        break;

                    case R.id.fourthbtn:
                        Intent intent3 = new Intent(Navigation.this, ThirdActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.fifthbtn:
                        Intent intent4 = new Intent(Navigation.this, DrugListActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
        }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new Tab1Fragment());
//        adapter.addFragment(new Tab2Fragment());
//        adapter.addFragment(new Tab3Fragment());
        viewPager.setAdapter(adapter);
    }

}
