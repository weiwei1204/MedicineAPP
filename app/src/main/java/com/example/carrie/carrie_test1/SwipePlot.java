package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SwipePlot extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    RequestQueue requestQueue;
    private List<BloodPressure> data_list;
    MenuItem menuItem1, menuItem2, menuItem3;
    public String url = "http://54.65.194.253/Health_Calendar/ShowBp.php";
    public static String memberid; //從上頁傳進來的
    public static int userid;
    public static String member_id; //從資料庫抓的
    public static String highmmhg="";
    public static String lowmmhg="";
    public static String bpm="";
    public static String savetime="";
    private boolean getdb=false;
    private BloodPressure data ;
    public final static String key ="bp";

    /////////////////////////////////
    public static String usrhighmmhg ="";
    public static String usrlowmmhg ="";
    public static String usrbpm ="";
    public static String usrsavetime ="";
    public String urls = "http://54.65.194.253/Health_Calendar/getBpRecordDate.php";
    private BloodPressure record ;
    public static int [] highvaluearray ;
    public static int [] lowvaluearray ;
    public static int [] bpmvaluearray ;
    public static String [] datearray;
    ArrayList list1=new ArrayList<Integer>();
    ArrayList list2=new ArrayList<Integer>();
    FragmentManager fragmentManager;
    public static String current_id;
    public static String myid;
    public static String my_google_id;
    public static String my_supervise_id;
    public String objectArray="";
    public  String objectDetailArray="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getValue(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_plot);

        Bundle bundle = getIntent().getExtras();
        int monid = bundle.getInt("monitor_who");
        memberid = String.valueOf(monid);//監控頁傳監控者的id
        current_id = bundle.getString("memberid");//從排成頁案過來看自己id也傳自己id
        myid = bundle.getString("userid");//從監控頁傳自己的id
        my_google_id = bundle.getString("googleid");
        my_supervise_id = bundle.getString("my_supervise_id");
        objectArray = bundle.getString("objectArray");
        objectDetailArray = bundle.getString("objectDetailArray");
        if(current_id!=null){
            memberid = current_id;
        }



//        memberid = bundle.getString("memberid");
        Log.d("6789","id: "+memberid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        data_list = new ArrayList<>();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//        getRecord();

        getData();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Log.d("8888","highmmhg:"+highmmhg);
        Log.d("8888","lowmmhg:"+lowmmhg);
        Log.d("8888","bpm:"+bpm);
        fragmentManager = getSupportFragmentManager();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("googleid",my_google_id);
        startActivity(i);
        finish();

//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//
//            Log.d("9876","do");
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//            Log.d("9876","do do do");
//        }

    }


    public void start(){
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.analytics);
        tabLayout.getTabAt(1).setIcon(R.drawable.presentation);
        tabLayout.getTabAt(2).setIcon(R.drawable.drugs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe_plot, menu);
        menuItem1 = menu.findItem(R.id.action_settings);
        menuItem2 = menu.findItem(R.id.action_myinfo);
        menuItem3 = menu.findItem(R.id.action_myschedule);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_myinfo){
            return true;
        }
        if (id == R.id.action_myschedule){
//            Intent it = new Intent(SwipePlot.this,Choice.class);
//            Bundle bundle = new Bundle();
//            if(myid!=null) {
//                bundle.putString("memberid", myid);
//                bundle.putString("my_google_id",my_google_id);
//                bundle.putString("my_supervise_id",my_supervise_id);
//            }else{
//                bundle.putString("memberid", current_id);
//                bundle.putString("my_google_id",my_google_id);
//                bundle.putString("my_supervise_id",my_supervise_id);
//            }
//            it.putExtras(bundle);
//            startActivity(it);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment = mSectionsPagerAdapter.getFragment(position);
        if (fragment != null) {
            fragment.onResume();
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private double mLattitude;
        private Map<Integer, String> mFragmentTags;
        private FragmentManager mFragmentManager;
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }



        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fragmentManager;
            mFragmentTags = new HashMap<Integer, String>();

        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BsPlotTab tab1 = new BsPlotTab();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("memberid", memberid);
                    tab1.setArguments(bundle1);
                    return tab1;
                case 1:
                    Log.d("1111", "highmmhg:" + highmmhg);
                    Bundle bundle2 = new Bundle();
                    BpPlotTab tab2 = new BpPlotTab();
                    bundle2.putString("memberid", memberid);
                    Log.d("1122","id: "+memberid);
//                    bundle2.putString("highmmhg", highmmhg);
//                    bundle2.putString("lowmmhg", lowmmhg);
//                    bundle2.putString("bpm", bpm);
//                    bundle2.putString("savetime", savetime);
                    bundle2.putParcelableArrayList("data_list", (ArrayList<? extends Parcelable>) data_list);
                    bundle2.putIntArray("high",highvaluearray);
                    bundle2.putIntegerArrayList("higharr", list2);
                    tab2.setArguments(bundle2);
                    Log.d("1111:",bundle2.toString());
                    return tab2;


                case 2:
                    PillPlot tab3 = new PillPlot();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("memberid", memberid);
                    bundle3.putString("objectArray",objectArray);
                    bundle3.putString("objectDetailArray", objectDetailArray);
                    tab3.setArguments(bundle3);
                    Log.d("bundle3:",bundle3.toString());
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "血糖趨勢圖";
                case 1:
                    return "血壓趨勢圖";
                case 2:
                    return "用藥排程紀錄";
            }
            return null;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                String tag = fragment.getTag();
                mFragmentTags.put(position, tag);
            }
            return object;
        }

        public Fragment getFragment(int position) {
            Fragment fragment = null;
            String tag = mFragmentTags.get(position);
            if (tag != null) {
                fragment = mFragmentManager.findFragmentByTag(tag);
            }
            return fragment;
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
//                        if (member_id.equals(memberid)) {
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
    public void getData(){
        Log.d("777","in method");
       requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("777","in response");
                try {
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());
                    getdb=true;

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        data = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
                            data_list.add(data);
                            userid = object.getInt("id");
                            member_id = object.getString("member_id");
                        if (member_id.equals(memberid)) {
                            highmmhg = object.getString("highmmhg");
                            lowmmhg = object.getString("lowmmhg");
                            bpm = object.getString("bpm");
                            savetime = object.getString("savetime");
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
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("777",error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
    public  void getRecord(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, urls, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("777","in response");
                int count = 0;
                try {
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());



                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        record = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        Log.d("1234","saw id:" +member_id);
                        if (member_id.equals(memberid)) {
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            count++;

                            highvaluearray = new int[count];
                            lowvaluearray = new int[count];
                            bpmvaluearray = new int[count];
                            datearray = new String[count];



                        }


                    }
                    for (int j = 0; j < highvaluearray.length; j++) {
                        JSONObject object2 = response.getJSONObject(j);
                        highvaluearray[j] = Integer.parseInt(object2.getString("highmmhg"));
                        list1.add(highvaluearray[j]);
                        Log.d("3434","arr:  "+highvaluearray[j]);
                        Log.d("5678","arr:  "+list1);

                    }


                    list2.add(list1);
                    Log.d("9909","list1:  "+list1);
                    start();

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("777",error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);



    }








    public void goback(View v) {
        finish();
        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(0);
        getSupportFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().executePendingTransactions();
        Log.d("5577","do this");
    }
}
