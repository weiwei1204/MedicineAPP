package com.example.carrie.carrie_test1;

 import android.os.AsyncTask;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.support.v7.widget.GridLayoutManager;
 import android.support.v7.widget.RecyclerView;
 import android.view.View;

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

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter2 adapter;
    private List<MyData> data_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druginfo);

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
                                object.getString("indication"),object.getString("englishName"),object.getString("image"));

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



}