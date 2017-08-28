package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.carrie.carrie_test1.R.id.list;

public class BpRecord extends AppCompatActivity {
    private ListView listView;
    private SwipeRefreshLayout laySwipe;
    private ArrayAdapter<BloodPressure> listAdapter;
    RequestQueue requestQueue;
    private String[] list1 = {"Mindy","Rita","Jonathan","Shana","Carrie"};
    public String url = "http://54.65.194.253/Health_Calendar/ShowBp.php";
    public List<BloodPressure> record_list;
    private BloodPressure data ;
    public static ArrayList<BloodPressure>bloodPressureList;
    public static String memberid;//傳進來的

    public static int userid;
    public static String member_id; //從資料庫抓的
    public static String highmmhg="";
    public static String lowmmhg="";
    public static String bpm="";
    public static String savetime="";

    public static String[]higharr;
    public static String[]lowarr;
    public static String[]bpmarr;
    public static String[]datearr;
    public static String[]array;
    public static int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        memberid = bundle.getString("memberid");
        record_list = new ArrayList<>();
        getData();



//        highmmhg = bundle.getString("highmmhg");
//        lowmmhg = bundle.getString("lowmmhg");
//        bpm = bundle.getString("bpm");

        setContentView(R.layout.activity_bp_record);


        listView = (ListView)findViewById(R.id.list_view);
        initView();


    }
    public void start(){
        listAdapter = new ArrayAdapter<BloodPressure>(this,android.R.layout.simple_selectable_list_item,record_list);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "你選擇的是" + list1[position], Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void initView() {
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(onSwipeToRefresh);
        laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
    }
    private SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    laySwipe.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
        }
    };
    private AbsListView.OnScrollListener onListScroll = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                laySwipe.setEnabled(true);
            }else{
                laySwipe.setEnabled(false);
            }
        }
    };


    public void getData(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("777","in response");
                try {
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());
                        count=0;

                    for (int i = response.length()-1; i >= 0; i--) {
                        JSONObject object = response.getJSONObject(i);
                        data = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));

                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        if (member_id.equals(memberid)) {
                            highmmhg = object.getString("highmmhg");
                            lowmmhg = object.getString("lowmmhg");
                            bpm = object.getString("bpm");
                            savetime = object.getString("savetime");

                            record_list.add(data);




                            higharr = new String[response.length()];
                            lowarr = new String[response.length()];
                            bpmarr = new String[response.length()];
                            datearr = new String[response.length()];

                            count++;

                            higharr[count] = highmmhg;
                            lowarr[count] = lowmmhg;
                            bpmarr[count] = bpm;
                            datearr[count] = savetime;

                            Log.d("5888","List"+higharr[count]);
                            Log.d("5555", "member_id:" + member_id);
                            Log.d("5555", "highmmhg:" + highmmhg);
                            Log.d("5555", "lowmmhg:" + lowmmhg);
                            Log.d("5555", "bpm:" + bpm);
                            Log.d("5555", "savetime:" + savetime);
                        }
                    }

                    start();


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("777",error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }


}