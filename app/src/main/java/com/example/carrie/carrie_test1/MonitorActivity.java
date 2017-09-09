package com.example.carrie.carrie_test1;

import android.Manifest;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MonitorActivity extends AppCompatActivity {
    Button scanbtn;
    private FragmentManager manager;
    private FragmentTransaction transaction;
//    TextView result;
    public static String my_google_id = "";
    public static String google_id = "";//欲監控對象的google_id
    public static String my_id = "";
    public static String my_mon_id = "";//Supviser的id
    public static String mon_id = "";//欲監控對象的id
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyMonitorData> dataList;
    int lastId=0;
//    private List<BloodPressure> data_list;
//    public static int userid;
//    public static String member_id; //從資料庫抓的
//    public static String highmmhg="";
//    public static String lowmmhg="";
//    public static String bpm="";
//    public static String sugarvalue="";
//    public static String savetime="";
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Bundle bundle = getIntent().getExtras();
        my_id = bundle.getString("my_id");//get 自己 id
        my_google_id = bundle.getString("my_google_id");//get 自己google_ id
        my_mon_id = bundle.getString("my_supervise_id");//取得自己supervise id
//        data_list = new ArrayList<>();
        manager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//find the tooolbar id
        setSupportActionBar(toolbar);//set toolbar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);//control which button is active
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){//define which view need to be direct
                    case R.id.ic_list:
                        Intent intent0 = new Intent(MonitorActivity.this,Choice.class);
                        Bundle bundle0 = new Bundle();
                        bundle0.putString("memberid", my_id);//put the parameter to bundle,so it can get this value
                        bundle0.putString("my_google_id", my_google_id);
                        bundle0.putString("my_supervise_id", my_mon_id);
                        intent0.putExtras(bundle0);   // 記得put進去，不然資料不會帶過去哦
                        startActivity(intent0);
                        break;

                    case R.id.ic_eye:
                        Intent intent1 = new Intent(MonitorActivity.this,MonitorActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("my_id", my_id);
                        bundle1.putString("my_google_id", my_google_id);
                        bundle1.putString("my_supervise_id", my_mon_id);
                        intent1.putExtras(bundle1);
                        startActivity(intent1);
                        break;

                    case R.id.ic_home:
                        Intent intent2 = new Intent(MonitorActivity.this, MainActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("googleid", my_google_id);
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;

                    case R.id.ic_information:
                        Intent intent3 = new Intent(MonitorActivity.this, druginfo.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("my_id", my_id);
                        bundle3.putString("my_google_id", my_google_id);
                        bundle3.putString("my_supervise_id", my_mon_id);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);
                        break;

                    case R.id.ic_beacon:
                        Intent intent4 = new Intent(MonitorActivity.this, Beacon.class);
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
        scanbtn = (Button) findViewById(R.id.action_add);//find menu's add button id

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view1);//find recycle view id
        dataList  = new ArrayList<>();//create a arraylist to save the object
        load_data_from_server(0);//call method to show monitor's friend

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this,dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == dataList.size()-1){
                    load_data_from_server(lastId);
                }

            }
        });
        ShowDialog();
    }
    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Monitor/testGeyAllMonitor.php?id="+integers[0]+"&superviser_id="+my_mon_id)
                        .build();//get all the monitor
                try {
                    Response response = client.newCall(request).execute();
                    //\\Log.d("printResponse",response.body().string());
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("printArray",array.toString());
                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        MyMonitorData data = new MyMonitorData(object.getInt("m_id"),object.getString("name"),
                                "http://s3-ap-northeast-1.amazonaws.com/appmedicine/monitoricon.png");//set default image
                        lastId=object.getInt("m_id");
                        dataList.add(data);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(MonitorActivity.this, ScanActivity.class);
            startActivityForResult(intent, REQUEST_CODE);//when click add button ,it turn to scanactivity
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCOde, Intent data) {
        if (requestCode == REQUEST_CODE && resultCOde == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                google_id = barcode.displayValue;
                Log.d("monitorGoogle", google_id);
                checkMonitorExist();//check the monitor exist

            }
        }
    }
//    private void getValue(int id) {
//        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
//            @Override
//            protected Void doInBackground(Integer... params) {
//                String url = "http://54.65.194.253/Health_Calendar/ShowBp.php";
//                OkHttpClient client = new OkHttpClient();
//                okhttp3.Request request = new okhttp3.Request.Builder()
//                        .url("http://54.65.194.253/Health_Calendar/ShowBp.php?id=" + params[0])
//                        .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    JSONArray array = new JSONArray(response.body().string());
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.getJSONObject(i);
//                        BloodPressure data = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("sugarvalue"), object.getString("savetime"));
//                        data_list.add(data);
//                        userid = data.getId();
//                        member_id = data.getMember_id();
//                        if (member_id.equals(my_id)) {
//                            highmmhg = data.getHighmmhg();
//                            lowmmhg = data.getLowmmhg();
//                            bpm = data.getBpm();
//                            sugarvalue = data.getSugarvalue();
//                            savetime = data.getSavetime();
//                            Log.d("0000", "member_id:" + member_id);
//                            Log.d("0000", "highmmhg:" + highmmhg);
//                            Log.d("0000", "lowmmhg:" + lowmmhg);
//                            Log.d("0000", "bpm:" + bpm);
//                            Log.d("0000", "sugarvalue:" + sugarvalue);
//                            Log.d("0000", "savetime:" + savetime);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    System.out.println("End of content");
//                }
//                return null;
//            }
//
//            protected void onPostExecute(Void aVoid) {
//
//            }
//        };
//        task.execute(id);
//    }

    public void gotoBpBsPlot(View v){ //連到圖表頁面
//        getValue(0);
//        Button btn = (Button) findViewById(R.id.button3);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
//                BsBpDialogFragment fragment = new BsBpDialogFragment();
//                fragment.show(fm,"BsBpDialogFragment");
//            }
//        });

//
        Bundle bundle = new Bundle();
        bundle.putString("memberid", my_id);

//
////        bundle.putString("highmmhg", highmmhg);
////        bundle.putString("lowmmhg", lowmmhg);
////        bundle.putString("bpm", bpm);
////        bundle.putString("sugarvalue", sugarvalue);
////        bundle.putString("savetime", savetime);
        Intent it = new Intent(this,SwipePlot.class);
        it.putExtras(bundle);
        startActivity(it);

    }
    public void ShowDialog(){

        Button btn;
        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BsBpDialogFragment f1 = new BsBpDialogFragment();
                f1.show(getSupportFragmentManager(),"yuanma_dialog");

            }
        });

    }

    public void checkMonitorExist() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Monitor/checkMonitor.php?google_id_monitor=";
                OkHttpClient client = new OkHttpClient();
                Log.d("111112323", google_id);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://54.65.194.253/Monitor/checkMonitor.php?google_id_monitor=" + google_id)
                        .build();
                Log.d("okhttp", "2222");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("okhttp", "1111");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("okhttp", "33333");
                    JSONObject object = array.getJSONObject(0);
                    Log.d("okhttp", "16666");
                    Log.d("okhttp", object.getString("id"));
                    if ((object.getString("id")).equals("nodata")) {
                        Log.d("okht2tp", "4442222");
                        //normalDialogEvent();
                    } else {

                        mon_id=object.getString("id");
                        Log.d("mon_idtest", "1221");
                        addMonitor();
                        //addNormalDialogEvent();
                        Log.d("mon_idtest", "12221231");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        };
        task.execute();
    }

    public void addMonitor() {//新增監視者至監視列表 使用insert sql
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("my_mon_id1", my_mon_id)
                        .addFormDataPart("mon_id1", mon_id)
                        .build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://54.65.194.253/Monitor/addMember.php")
                .post(formBody)
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            Log.d("mon_idte213st", "12212321231");
            Log.d("mon_idte213st", "http://54.65.194.253/Monitor/addMember.php?my_mon_id1=" + my_mon_id + "&mon_id1=" + mon_id);
            Log.d("mon_idte213st", "122");

        } catch (IOException e) {
            e.printStackTrace();
        }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        };
        task.execute();
    }

    public void normalDialogEvent() {
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage(R.string.notFindMonitor)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "請重新新增", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void addNormalDialogEvent() {
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage("新增好友成功")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "已完成新增", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void refreshNormalDialogEvent() {
        new AlertDialog.Builder(MonitorActivity.this)
                .setMessage("請重新掃描")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "掃描again", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


}
