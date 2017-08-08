package com.example.carrie.carrie_test1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import okhttp3.Response;


public class MonitorActivity extends AppCompatActivity {
    Button scanbtn;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Bundle bundle = getIntent().getExtras();
        my_id = bundle.getString("my_id");//get 自己 id
        my_google_id = bundle.getString("my_google_id");//get 自己google_ id
        my_mon_id = bundle.getString("my_supervise_id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scanbtn = (Button) findViewById(R.id.action_add);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        dataList  = new ArrayList<>();
        load_data_from_server(0);

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
    }

    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Monitor/testGeyAllMonitor.php?id="+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    //\\Log.d("printResponse",response.body().string());
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("printArray",array.toString());
                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        MyMonitorData data = new MyMonitorData(object.getInt("m_id"),object.getString("name"),
                                "http://s3-ap-northeast-1.amazonaws.com/appmedicine/monitoricon.png");
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
            startActivityForResult(intent, REQUEST_CODE);
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
                checkMonitorExist();

            }
        }
    }

    public void gotoBpBsPlot(View v) { //連到圖表頁面
        Intent it = new Intent(this, BpBsPlot.class);
        startActivity(it);
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

    public void addMonitor() {
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
