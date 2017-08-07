package com.example.carrie.carrie_test1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
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

import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//測試換行
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;

// 注意這裡, Android Studio 預設會幫您引入 import android.widget.SearchView
// 但我們要的是 android.support.v7.widget.SearchView;


public class FirstActivity extends AppCompatActivity {

    String getDrugUrl = "http://54.65.194.253/test/testDrugAll.php";
    RequestQueue requestQueue;
    TextView chname;
    TextView engname;
    TextView indication;
    ArrayList<Drug> DrugList = new ArrayList<Drug>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        chname=(TextView)findViewById(R.id.chname);
        engname=(TextView)findViewById(R.id.engname);
        indication=(TextView)findViewById(R.id.indication);

        getDrug();

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



    public void getDrug(){//取此用藥資訊
        Log.d("aaa","4");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("aaa","3");

        final StringRequest request = new StringRequest(Request.Method.POST, getDrugUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaa","1");

                    JSONArray drugs = new JSONArray(response);

                    final String[] drugarray=new String[drugs.length()];
                    final String[] drugaidrray=new String[drugs.length()];

                    Log.d("aaa",drugs.toString());
                    for (int i=0 ; i<drugs.length() ; i++){
                        Log.d("aaa","2");

                        JSONObject drug = drugs.getJSONObject(i);
                        int id = Integer.parseInt(drug.getString("id"));
                        String chineseName = drug.getString("chineseName");
                        String licenseNumber=drug.getString("licenseNumber");
                        String indication=drug.getString("indication");
                        String englishName=drug.getString("englishName");
                        String category=drug.getString("category");
                        String image=drug.getString("image");
                        //String component=drug.getString("component");
                        String delivery=drug.getString("delivery");
                        String maker_Name=drug.getString("maker_Name");
                        String maker_Country=drug.getString("maker_Country");
                        String applicant=drug.getString("applicant");
                        //String sideEffect=drug.getString("sideEffect");
                        //String QRCode=drug.getString("QRCode");
                        //int searchTimes= Integer.parseInt(drug.getString("searchTimes"));
//                        String chinesename = drug.getString("chineseName");
//                        String indication = drug.getString("indication");
//                        String id = drug.getString("id");
                        Drug Adrug = new Drug( id,  chineseName,  licenseNumber,  indication,  englishName,  category,  image,  delivery,  maker_Name,  maker_Country,  applicant);
                        DrugList.add(Adrug);
//                       drugaidrray[i] = id;
//                        drugarray[i] = chineseName;

                    }//取值結束
                   chname.setText(DrugList.get(0).getChineseName());
       //             Log.d("aaa",drugarray[0]);
                    for (int j=0;j<DrugList.size();j++){
                        Log.d("vvvvv",DrugList.get(j).getChineseName());
                    }

                    engname.setText(DrugList.get(0).getEnglishName());
                    for(int i=0;i<DrugList.size(); i++){
                        Log.d("vvvvv",DrugList.get(i).getEnglishName());
                    }
                    indication.setText(DrugList.get(0).getIndication());
                    for(int k=0; k<DrugList.size(); k++){
                        Log.d("vvvvv",DrugList.get(k).getIndication());
                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
