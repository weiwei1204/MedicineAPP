package com.example.carrie.carrie_test1;

 import android.app.LauncherActivity;
 import android.content.Intent;
 import android.graphics.Color;
 import android.os.AsyncTask;

 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.support.v7.view.menu.ExpandedMenuView;
 import android.support.v7.widget.GridLayoutManager;
 import android.support.v7.widget.RecyclerView;
 import android.support.v7.widget.Toolbar;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.view.Menu;
 import android.view.View;

 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 import java.io.IOException;
 import java.lang.reflect.Array;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;

 import okhttp3.OkHttpClient;
 import okhttp3.Request;
 import okhttp3.Response;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.EditText;
 import android.widget.ListView;

 import com.google.zxing.client.android.Intents;

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

public class druginfo extends AppCompatActivity{

    String insertUrl = "http://54.65.194.253/Drug/search.php";
    String [] items;
    ArrayList<String> listitems;
    ArrayAdapter<String> adapter1;
    ListView listView;
    EditText editText;



    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter2 adapter;
    private List<MyData> data_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druginfo);




        //search
        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.textsearch);
        initList();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    //reset listview
                    initList();
                }
                else {
                    //preform search
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list  = new ArrayList<>();
        load_data_from_server(0);

        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter2(this,data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1){
                    load_data_from_server(data_list.get(data_list.size()-1).getId());
                }

            }
        });

    }
    public void searchItem(String textToSearch){
        for(String item: items){
            if(!item.contains(textToSearch)){
                listitems.remove(item);
            }
        }

        adapter1.notifyDataSetChanged();
    }

    public void initList(){
        items=  new String[]{"Canada","Taiwan","Japan"};
        listitems= new ArrayList<>(Arrays.asList(items));
        adapter1 = new ArrayAdapter<String>(this, R.layout.list_item2, R.id.txtitem, listitems);
        listView.setAdapter(adapter1);
    }


    public void goback(View v){
        finish();
    }


    private void load_data_from_server(int id) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://54.65.194.253/Drug/ShowDrug.php?id="+integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        MyData mydata = new MyData(object.getInt("id"),object.getString("chineseName"),
                                object.getString("image") ,object.getString("indication"),object.getString("englishName"),object.getString("licenseNumber")
                                ,object.getString("category"), object.getString("component"), object.getString("maker_Country"), object.getString("applicant")
                                ,object.getString("maker_Name"));

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
    public void gotodscanner(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,Scanner.class);
        startActivity(it);
    }
    public void gotofourth(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,FourthActivity.class);
        startActivity(it);
    }


}