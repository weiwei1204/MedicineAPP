package com.example.carrie.carrie_test1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;

// 注意這裡, Android Studio 預設會幫您引入 import android.widget.SearchView
// 但我們要的是 android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView;



public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }


    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FourthActivity.class);
        startActivity(it);
    }

    public void gotoScanner(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,Scanner.class);
        startActivity(it);
    }
    public void goback(View v){
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // 顯示完成鈕
        searchView.setSubmitButtonEnabled(true);

        return true;
    }
}
