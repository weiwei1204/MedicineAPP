package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.carrie.carrie_test1.R.id.dark;
import static com.example.carrie.carrie_test1.R.id.drawer_layout;
import static com.example.carrie.carrie_test1.R.id.list;

public class BpRecord extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout laySwipe;
    private ArrayAdapter<BloodPressure> listAdapter;
    RequestQueue requestQueue;
    private String[] list1 = {"Mindy","Rita","Jonathan","Shana","Carrie"};
    public String url = "http://54.65.194.253/Health_Calendar/ShowBp.php";
    public String url2 = "http://54.65.194.253/Health_Calendar/BpOnTime.php?member_id="+memberdata.getMember_id();
    public List<BloodPressure> record_list;
    private BloodPressure data ;
    public static ArrayList<BloodPressure>bloodPressureList;
    public static String memberid;//傳進來的
    public static String high;
    public static String low;
    public static String bpmm;
    public static String googleid;

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
    ImageButton btn;
    FloatingActionButton press;
    View rootView;
    private static int save = -1;
    public static String settingtime;
    public static String type;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        memberid = bundle.getString("memberid");
        high = bundle.getString("highmmhg");
        low = bundle.getString("lowmmhg");
        bpmm = bundle.getString("bpm");
        googleid = bundle.getString("googleid");
        Log.d("3434","googleid: "+googleid);

        record_list = new ArrayList<>();

        rootView = inflater.inflate(R.layout.activity_bp_record, container, false);
        listView = (ListView)rootView.findViewById(R.id.list_view);
        press = (FloatingActionButton) rootView.findViewById(R.id.press1);
        getData();
//        getTime();
        initView();
//        btn = (ImageButton) rootView.findViewById(R.id.Bpbtn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddBp();
//            }
//        });


        press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBp();
            }
        });


       return  rootView;

    }

    public void start(){
        listAdapter = new ArrayAdapter<BloodPressure>(getActivity(),android.R.layout.simple_selectable_list_item,record_list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLACK);
                return textView;
            }
        };
        listAdapter.notifyDataSetChanged();

        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.holo_orange_light);
        listView.setBackgroundResource(R.color.colorWhite);
        listView.setDivider(getResources().getDrawable(R.drawable.list_devide));
        listView.setDividerHeight(3);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "你選擇的是" + list1[position], Toast.LENGTH_SHORT).show();

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
            listAdapter.addAll(record_list);
            listAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);

    }
    public void AddBp(){
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
            public void onResponse(final JSONArray response) {

                Log.d("777","in response");
                int count = 0;
                try {
                    count=0;
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

                                    if (cur.after(settime.getTime()) && cur.before(timebefore.getTime())) {
                                        Log.d("8989", "do this ");
                                        Log.d("8989", "nowtime: " + format.format(nowtime.getTime()));
                                        Log.d("8989", "setting time: " + format.format(settime.getTime()));
                                        Log.d("8989", "after 30 minutes: " + format.format(timebefore.getTime()));
                                        press = (FloatingActionButton) rootView.findViewById(R.id.press1);
                                        Intent it = new Intent(getActivity(), EnterBpValue.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("memberid", memberid);
                                        bundle.putString("googleid", EnterBsBpActivity.my_google);
                                        it.putExtras(bundle);
                                        startActivity(it);
                                        count++;
                                    }

                           if(type==null){
                                Toast.makeText(getActivity().getApplicationContext(), "尚未設定紀錄血壓的時間哦!", Toast.LENGTH_SHORT).show();
                            }

                        }
                        if(count==0) {
                            Toast.makeText(getActivity().getApplicationContext(), "還沒到紀錄血壓的時間哦!", Toast.LENGTH_SHORT).show();
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
                }){
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1122",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);




    }
//    public void getTime(){
//        Log.d("777","in method");
//        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        Log.d("777","1");
//        final Calendar rightNow = Calendar.getInstance();
//        rightNow.add(Calendar.MINUTE,30);
//        Log.d("9898","settime: "+rightNow.getTime());
//        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        final Calendar current = Calendar.getInstance();
//
//        final String currenttime = format.format(current.getTime());
//        final String intime = format.format(rightNow.getTime());
//        Log.d("9999","Current Time :  "+currenttime);
//        Log.d("9999","time:  "+intime);
//        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(com.android.volley.Request.Method.POST, url2, new com.android.volley.Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(final JSONArray response) {
//
//                Log.d("777","in response");
//                try {
//                    count=0;
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject object = response.getJSONObject(i);
//                        String members_id = object.getString("member_id");
//                        if (members_id.equals(memberid)) {
//                            settingtime = object.getString("time");
//                            type = object.getString("type");
//                            Log.d("4567","Time: "+settingtime);
//                            Calendar timebefore = Calendar.getInstance();
//                            timebefore.setTime(format.parse(settingtime));
//
//                            timebefore.add(Calendar.MINUTE,30);//設定時間後30分鐘
//                            Calendar nowtime = Calendar.getInstance();//現在時間
//                            Date cur= format.parse(currenttime);
//
////                            Log.d("8989","nowtime: "+format.format(nowtime.getTime()));
//
//                            Calendar settime = Calendar.getInstance();
//                            settime.setTime(format.parse(settingtime));//設定的時間
////                            Log.d("8989","setting time: "+format.format(settime.getTime()));
////                            Log.d("8989","after 30 minutes: "+format.format(timebefore.getTime()));
//                            if(type.equals("bp_1") || type.equals("bp_2") || type.equals("bp_3")) {
//
//                                if (cur.after(settime.getTime()) && cur.before(timebefore.getTime())) {
//                                    Log.d("8989", "do this ");
//                                    Log.d("8989","nowtime: "+format.format(nowtime.getTime()));
//                                    Log.d("8989","setting time: "+format.format(settime.getTime()));
//                                    Log.d("8989","after 30 minutes: "+format.format(timebefore.getTime()));
//                                    press = (FloatingActionButton)rootView.findViewById(R.id.press1);
//                                    press.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            if(response==null){
//                                                Toast.makeText(getActivity().getApplicationContext(), "尚未設定紀錄血壓的時間哦!", Toast.LENGTH_SHORT).show();
//                                            }else {
////                                                AddBp();
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                            else {
//                                    press.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Toast.makeText(getActivity().getApplicationContext(), "還沒到紀錄血壓的時間哦!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//
//                            count++;
//
//                        }
//                    }
//
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("777",error.toString());
//                    }
//                });
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(jsonObjectRequest);
//
//
//    }
//
//
}
