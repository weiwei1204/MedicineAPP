package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BsRecord extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout laySwipe;
    private ArrayAdapter<BloodSugar> listAdapter;
    RequestQueue requestQueue;
    public String url = "http://54.65.194.253/Health_Calendar/ShowBs.php";
    public String url2 = "http://54.65.194.253/Health_Calendar/onTime.php";
    private String[]list={};
    public List<BloodSugar> record_list;
    private BloodSugar data ;
    public static String memberid;//傳進來的
    public static String member_id; //從資料庫抓的
    public static String bloodsugar="";
    public static String savetime="";
    public static String googleid;
    ImageButton btn;
    FloatingActionButton press;
    View rootView;
    public static String settingtime;
    public static String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_bs_record, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        memberid = bundle.getString("memberid");
        googleid = bundle.getString("googleid");
        Log.d("3434","googleid: "+googleid);
        record_list = new ArrayList<>();
        getData();
        listView = (ListView)rootView.findViewById(R.id.list_view);
//        btn = (ImageButton)rootView.findViewById(R.id.Bsbtn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddBs();
//
//            }
//        });
        press = (FloatingActionButton)rootView.findViewById(R.id.press2);
        getTime();
        initView();
        return rootView;

    }

    public void start(){
        listAdapter = new ArrayAdapter<BloodSugar>(getActivity(),android.R.layout.simple_selectable_list_item,record_list);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private void initView() {
        laySwipe = (SwipeRefreshLayout)rootView.findViewById(R.id.laySwipe);
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
                    Toast.makeText(getActivity().getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
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
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);

    }
    public void AddBs(){
        Intent it = new Intent(getActivity(),EnterBsValue.class);
        Bundle bundle = new Bundle();
        bundle.putString("memberid", memberid);
        bundle.putString("googleid",EnterBsBpActivity.my_google);
        it.putExtras(bundle);
        startActivity(it);
    }
    public void getTime(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MINUTE,30);
        Log.d("9898","settime: "+rightNow.getTime());
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        final Calendar current = Calendar.getInstance();

        final String currenttime = format.format(current.getTime());
        final String intime = format.format(rightNow.getTime());
        Log.d("9999","Current Time :  "+currenttime);
        Log.d("9999","time:  "+intime);
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(com.android.volley.Request.Method.POST, url2, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                Log.d("777","in response");
                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String members_id = object.getString("member_id");
                        if (members_id.equals(memberid)) {
                            settingtime = object.getString("time");
                            type = object.getString("type");
                            Log.d("4567","Time: "+settingtime);
                            Calendar timebefore = Calendar.getInstance();
                            timebefore.setTime(format.parse(settingtime));

                            timebefore.add(Calendar.MINUTE,30);//設定時間後30分鐘
                            Calendar nowtime = Calendar.getInstance();//現在時間
                            Date cur= format.parse(currenttime);

//                            Log.d("8989","nowtime: "+format.format(nowtime.getTime()));

                            Calendar settime = Calendar.getInstance();
                            settime.setTime(format.parse(settingtime));//設定的時間
//                            Log.d("8989","setting time: "+format.format(settime.getTime()));
//                            Log.d("8989","after 30 minutes: "+format.format(timebefore.getTime()));
                            if(type.equals("bs_1") || type.equals("bs_2") || type.equals("bs_3")) {

                                if (cur.after(settime.getTime()) && cur.before(timebefore.getTime())) {
                                    Log.d("7979", "do this ");
                                    Log.d("8989","nowtime: "+format.format(nowtime.getTime()));
                                    Log.d("7979","setting time: "+format.format(settime.getTime()));
                                    Log.d("7979","after 30 minutes: "+format.format(timebefore.getTime()));
                                    press.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AddBs();
                                        }
                                    });
                                }
                            }
                            else {
                                press.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getActivity().getApplicationContext(), "還沒到紀錄血糖的時間哦!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }



                        }
                    }


                }catch (JSONException e){
                    e.printStackTrace();
                } catch (ParseException e) {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);


    }

}
