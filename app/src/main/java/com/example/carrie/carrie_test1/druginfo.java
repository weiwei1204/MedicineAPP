package com.example.carrie.carrie_test1;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by shana on 2017/8/7.
 */

public class druginfo extends AppCompatActivity {

    String insertUrl = "http://54.65.194.253/Drug/search.php";
    String[] items;
    ArrayList<String> listitems;
    ArrayAdapter<String> adapter1;
    ListView listView;
    EditText editText;
    public static final int PERMISSION_REQUEST = 200;
    private RelativeLayout btmbar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter2 adapter;
    private List<MyData> data_list;
    private List<MyData> data_list1;
    private List<MyData> data_list2;
    public  String my_google_id = "";
    public  String my_id = "";
    public  String my_mon_id = "";//Supviser的id
    private String m_calid="0";
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druginfo);
        Bundle bundle = getIntent().getExtras();
        my_id = bundle.getString("my_id");//get 自己 id
        my_google_id = bundle.getString("my_google_id");//get 自己google_ id
        my_mon_id = bundle.getString("my_supervise_id");
        m_calid = bundle.getString("m_calid");

        Log.d("qqqqq",m_calid);


        btmbar = (RelativeLayout) findViewById(R.id.btmbar);
        if (my_id.equals("0") && my_google_id.equals("0") && my_mon_id.equals("0")){
            //如果從藥袋跳頁過來就不顯示頁面下面的跳頁鈕
            btmbar.setVisibility(View.GONE);
        }
        else {
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.ic_list:
                            Intent intent0 = new Intent(druginfo.this,Choice.class);
                            Bundle bundle0 = new Bundle();
                            bundle0.putString("memberid", my_id);
                            bundle0.putString("my_google_id", my_google_id);
                            bundle0.putString("my_supervise_id", my_mon_id);
                            intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                            startActivity(intent0);
                            break;

                        case R.id.ic_eye:
                            Intent intent1 = new Intent(druginfo.this,MonitorActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("my_id", my_id);
                            bundle1.putString("my_google_id", my_google_id);
                            bundle1.putString("my_supervise_id", my_mon_id);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                            break;

                        case R.id.ic_home:
                            Intent intent2 = new Intent(druginfo.this, MainActivity.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("googleid", my_google_id);
                            intent2.putExtras(bundle2);
                            startActivity(intent2);
                            break;

                        case R.id.ic_information:
                            Intent intent3 = new Intent(druginfo.this, druginfo.class);
                            Bundle bundle3 = new Bundle();
                            bundle3.putString("my_id", my_id);
                            bundle3.putString("my_google_id", my_google_id);
                            bundle3.putString("my_supervise_id", my_mon_id);
                            intent3.putExtras(bundle3);
                            startActivity(intent3);
                            break;

                        case R.id.ic_beacon:
                            Intent intent4 = new Intent(druginfo.this, Beacon.class);
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

        listView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.textsearch);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    //reset listview

//                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//                    data_list = new ArrayList<>();
//                    load_data_from_server(0);
//
//                    gridLayoutManager = new GridLayoutManager(getBaseContext(), 2);
//                    recyclerView.setLayoutManager(gridLayoutManager);
//
//                    adapter = new CustomAdapter2(getBaseContext(), data_list);
//                    recyclerView.setAdapter(adapter);
//
//                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                            if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
//                                load_data_from_server(data_list.get(data_list.size() - 1).getId());
//                            }
//
//                        }
//                    });
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    data_list2 = new ArrayList<>();
                    Log.d("searchtest", "3");
                    load_data_from_server_search(s.toString());

                    Log.d("searchtest", "1");
                    gridLayoutManager = new GridLayoutManager(getBaseContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    Log.d("searchtest", "2");
                    adapter = new CustomAdapter2(getBaseContext(), data_list2,m_calid);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


                    gridLayoutManager = new GridLayoutManager(getBaseContext(), 2);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    adapter = new CustomAdapter2(getBaseContext(), data_list,m_calid);
                    recyclerView.setAdapter(adapter);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter2(this, data_list,m_calid);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(data_list.get(data_list.size() - 1).getId());
                }

            }
        });

    }
//    public void searchItem( String textToSearch){
//
//                for(MyData item: data_list){
//                    Log.d("searchtest","a");
//                    if(!item.getChineseName().contains(textToSearch)||!item.getEnglishName().contains(textToSearch)||!item.getIndication().contains(textToSearch)){
//                        Log.d("searchtest","b");
//                        data_list.remove(item);
//                        Log.d("searchtest","c");
//                    }
//                }
//                //adapter.notifyDataSetChanged();
//
//    }


    public void goback(View v) {
        finish();
    }


    private void load_data_from_server(int id) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Drug/ShowDrug.php?id=" + integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        MyData mydata = new MyData(object.getInt("id"),object.getString("chineseName"),
                                object.getString("image") ,object.getString("indication"),object.getString("englishName"),object.getString("licenseNumber")
                                ,object.getString("category"), object.getString("component"), object.getString("maker_Country"), object.getString("applicant")
                                ,object.getString("maker_Name"),object.getString("QRCode"));

                        data_list.add(mydata);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }
    private void load_data_from_server_notsearch(int id) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Drug/ShowDrug.php?id=" + integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        MyData mydata = new MyData(object.getInt("id"),object.getString("chineseName"),
                                object.getString("image") ,object.getString("indication"),object.getString("englishName"),object.getString("licenseNumber")
                                ,object.getString("category"), object.getString("component"), object.getString("maker_Country"), object.getString("applicant")
                                ,object.getString("maker_Name"),object.getString("QRCode"));


                        data_list1.add(mydata);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }

    private void load_data_from_server_search(final String search) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Drug/search.php?search=" + search)
                        .build();
                Log.d("searchtest", "4");
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("searchtest", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        MyData mydata = new MyData(object.getInt("id"),object.getString("chineseName"),
                                object.getString("image") ,object.getString("indication"),object.getString("englishName"),object.getString("licenseNumber")
                                ,object.getString("category"), object.getString("component"), object.getString("maker_Country"), object.getString("applicant")
                                ,object.getString("maker_Name"),object.getString("QRCode") );


                        data_list2.add(mydata);
                    }
                    Log.d("searchtest", "5");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(1);
    }

    public void gotoscandrug(View v) { //連到搜尋藥品資訊頁面
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        Intent it = new Intent(this, scandrug.class);
        startActivity(it);
    }
    public void gotofourth(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FourthActivity.class);

        startActivity(it);
    }


}