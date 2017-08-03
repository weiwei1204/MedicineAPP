package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/3.
 */


import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;

public class testlayout extends AppCompatActivity{
    TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        mTabLayout.addTab(mTabLayout.newTab().setText("首頁"));
        mTabLayout.addTab(mTabLayout.newTab().setText("分類"));
        mTabLayout.addTab(mTabLayout.newTab().setText("設置"));
    }
}
