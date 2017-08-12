package com.example.carrie.carrie_test1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// 注意這裡, Android Studio 預設會幫您引入 import android.widget.SearchView
// 但我們要的是 android.support.v7.widget.SearchView;


public class FirstActivity extends AppCompatActivity {

    String getDrugUrl = "http://54.65.194.253/Drug/GetDrug.php";
    String getBeaconUrl = "http://54.65.194.253/Beacon/getBeacon.php";

    RequestQueue requestQueue;
    TextView chname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        chname = (TextView) findViewById(R.id.chname);
        getDrug();

    }


    public void gotoFourthActivity(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, FourthActivity.class);
        startActivity(it);
    }

    public void gotoScanner(View v) { //連到搜尋藥品資訊頁面
        Intent it = new Intent(this, Scanner.class);
        startActivity(it);
    }

    public void goback(View v) {
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // 顯示完成鈕
        searchView.setSubmitButtonEnabled(true);

        return true;
    }

    public void getDrug() {//取此會員的beacon
        Log.d("aaa", "4");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("aaa", "3");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getDrugUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("aaa", "1");

                    JSONArray drugs = response.getJSONArray("Drugs");

                    Log.d("vvv123", String.valueOf(drugs.length()));

                    final String[] drugarray = new String[drugs.length()];
                    final String[] drugaidrray = new String[drugs.length()];
                    for (int i = 0; i < drugs.length(); i++) {
                        Log.d("aaa", "2");
                        JSONObject drug = drugs.getJSONObject(i);
                        String chinesename = drug.getString("chineseName");
//                        String indication = drug.getString("indication");
                        String id = drug.getString("");
                        drugaidrray[i] = id;
                        drugarray[i] = chinesename;
                        Log.d("vvvvv", drugarray[i]);
                    }//取值結束
                    chname.setText(drugarray[0]);
                    Log.d("aaa", drugarray[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("vvv",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
