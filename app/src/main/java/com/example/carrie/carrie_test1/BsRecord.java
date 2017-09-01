package com.example.carrie.carrie_test1;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

public class BsRecord extends AppCompatActivity {
    private ListView listView;
    private SwipeRefreshLayout laySwipe;
    private ArrayAdapter<BloodSugar> listAdapter;
    RequestQueue requestQueue;
    public String url = "http://54.65.194.253/Health_Calendar/ShowBs.php";
    private String[]list={};
    public List<BloodSugar> record_list;
    private BloodSugar data ;
    public static String memberid;//傳進來的
    public static String member_id; //從資料庫抓的
    public static String bloodsugar="";
    public static String savetime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bs_record);
        Bundle bundle = getIntent().getExtras();
        memberid = bundle.getString("memberid");
        record_list = new ArrayList<>();
        getData();
        listView = (ListView)findViewById(R.id.list_view);
        initView();

    }

    public void start(){
        listAdapter = new ArrayAdapter<BloodSugar>(this,android.R.layout.simple_selectable_list_item,record_list);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


                    for (int i = response.length()-1; i >= 0; i--) {
                        JSONObject object = response.getJSONObject(i);
                        data = new BloodSugar(object.getInt("id"), object.getString("member_id"), object.getString("bloodsugar"), object.getString("savetime"));


                        member_id = object.getString("member_id");
                        if (member_id.equals(memberid)) {
                            bloodsugar = object.getString("bloodsugar");
                            savetime = object.getString("savetime");

                            record_list.add(data);


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
