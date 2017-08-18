//package com.example.carrie.carrie_test1;
//
//import android.app.SearchManager;
//import android.content.Context;
//import android.content.Intent;
//
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SearchView;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//
//// 注意這裡, Android Studio 預設會幫您引入 import android.widget.SearchView
//// 但我們要的是 android.support.v7.widget.SearchView;
//
//
//
//
//
//public class FirstActivity extends AppCompatActivity {
//
//
//    private String TAG = FirstActivity.class.getSimpleName();
//    //    private String getdata;
//    private ListView lv;
////    private EditText sendchname;
////    private EditText sendengname;
////    ArrayAdapter<String> drugadapter;
//
//
//    ArrayList<HashMap<String, String>> contactList;
//
//
//
//    String getDrugUrl = "http://54.65.194.253/test/testDrugAll.php";
//    RequestQueue requestQueue;
//    TextView chname;
//    ArrayList<Drug> DrugList = new ArrayList<Drug>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_first);
//        contactList = new ArrayList<>();
////        sendchname = (EditText) findViewById(R.id.chname);
////        sendengname = (EditText) findViewById(R.id.engname);
//        lv = (ListView) findViewById(R.id.list);
//
//        new GetContacts().execute();
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//
//
//    }
//
//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(FirstActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
//
//        }
//        @Override
//        protected Void doInBackground(Void... arg0) {
////            HttpHandler sh = new HttpHandler();
//            // Making a request to url and getting response
//            String url = "http://54.65.194.253/test/testDrugAll.php";
////            String jsonStr = sh.makeServiceCall(url);
////            Log.d("123","0");
////            Log.e(TAG, "Response from url: " + jsonStr);
////            if (jsonStr != null) {
////                Log.d("123","1");
////                try {
////                    Log.d("123","1.5");
////                    Log.d("123","1");
////                    // Getting JSON Array node
////                    JSONArray  drugs = new JSONArray(jsonStr);
////                    Log.d("123","2");
////                    // looping through All Contacts
////                    for (int i = 0; i < drugs.length(); i++) {
////
////                        JSONObject c = drugs.getJSONObject(i);
////                        String id = c.getString("id");
////                        String chineseName = c.getString("chineseName");
////                        String englishName = c.getString("englishName");
////                        String image = c.getString("image");
////                        Log.d("123","3");
////
////
////
////                        // Phone node is JSON Object
////
////
////                        // tmp hash map for single contact
////                        HashMap<String, String> druginfo = new HashMap<>();
////
////                        // adding each child node to HashMap key => value
////                        druginfo.put("id", id);
////                        druginfo.put("chineseName", chineseName);
////                        druginfo.put("englishName", englishName);
////                        druginfo.put("image",image);
////                        Log.d("123","4");
////                        // adding contact to contact list
////                        contactList.add(druginfo);
////                    }
////                } catch (final JSONException e) {
////                    Log.e(TAG, "Json parsing error: " + e.getMessage());
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Toast.makeText(getApplicationContext(),
////                                    "Json parsing error: " + e.getMessage(),
////                                    Toast.LENGTH_LONG).show();
////                        }
////                    });
////
////                }
////
////            } else {
////                Log.e(TAG, "Couldn't get json from server.");
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Toast.makeText(getApplicationContext(),
////                                "Couldn't get json from server. Check LogCat for possible errors!",
////                                Toast.LENGTH_LONG).show();
////                    }
////                });
////            }
////
////            return null;
//        }
//        protected void showdrugdata(){
//
//
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            String data;
//            List<String> r = new ArrayList<String>();
//            ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,r);
////            ListAdapter adapter = new SimpleAdapter(FirstActivity.this, contactList,
////                    R.layout.activity_first, new String[]{ "chineseName","englishName"},
////                    new int[]{R.id.chname,R.id.engname});
////            lv.setAdapter(adapter);
//
//
//
//        }
//    }
//
//
//
//
//    public void gotoFourthActivity(View v){ //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,FourthActivity.class);
//        startActivity(it);
//    }
//
//    public void gotoScanner(View v){ //連到搜尋藥品資訊頁面
//        Intent it = new Intent(this,Scanner.class);
//        startActivity(it);
//    }
//    public void goback(View v){
//        finish();
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        // 顯示完成鈕
//        searchView.setSubmitButtonEnabled(true);
//
//        return true;
//    }
//
//
//
//
//    public void getDrug(){//取此用藥資訊
//        Log.d("aaa","4");
//
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        Log.d("aaa","3");
//
//        final StringRequest request = new StringRequest(Request.Method.POST, getDrugUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.d("aaa","1");
//
//                    JSONArray drugs = new JSONArray(response);
//
//                    final String[] drugarray=new String[drugs.length()];
//                    final String[] drugaidrray=new String[drugs.length()];
//
//                    Log.d("aaa",drugs.toString());
//                    for (int i=0 ; i<drugs.length() ; i++){
//                        Log.d("aaa","2");
//
//                        JSONObject drug = drugs.getJSONObject(i);
//                        int id = Integer.parseInt(drug.getString("id"));
//                        String chineseName = drug.getString("chineseName");
//                        String licenseNumber=drug.getString("licenseNumber");
//                        String indication=drug.getString("indication");
//                        String englishName=drug.getString("englishName");
//                        String category=drug.getString("category");
//                        String image=drug.getString("image");
//                        //String component=drug.getString("component");
//                        String delivery=drug.getString("delivery");
//                        String maker_Name=drug.getString("maker_Name");
//                        String maker_Country=drug.getString("maker_Country");
//                        String applicant=drug.getString("applicant");
//                        //String sideEffect=drug.getString("sideEffect");
//                        //String QRCode=drug.getString("QRCode");
//                        //int searchTimes= Integer.parseInt(drug.getString("searchTimes"));
////                        String chinesename = drug.getString("chineseName");
////                        String indication = drug.getString("indication");
////                        String id = drug.getString("id");
//                        Drug Adrug = new Drug( id,  chineseName,  licenseNumber,  indication,  englishName);
//                        DrugList.add(Adrug);
////                        drugaidrray[i] = id;
////                            drugarray[i] = chineseName;
//
//                    }//取值結束
////                    chname.setText(drugarray[0]);
////                    Log.d("aaa",drugarray[0]);
//                    for (int j=0;j<DrugList.size();j++){
//                        Log.d("vvvvv",DrugList.get(j).getChineseName());
//                    }
//
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//
//
//}