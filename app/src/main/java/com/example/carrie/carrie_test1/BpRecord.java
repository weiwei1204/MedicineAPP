package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.carrie.carrie_test1.R.id.list;

public class BpRecord extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private String[]list = {"1","2","3","4","5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String memberid = bundle.getString("memberid");
        String highmmhg = bundle.getString("highmmhg");
        String lowmmhg = bundle.getString("lowmmhg");
        String bpm = bundle.getString("bpm");
        setContentView(R.layout.activity_bp_record);
        listView = (ListView)findViewById(R.id.list_view);
        getdata(0);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void getdata(int id) {

    AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
        @Override
        protected Void doInBackground(Integer... integers) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://54.65.194.253/Health_Calendar/ShowBp.php?id="+integers[0]).build();
            try {
                Response response = client.newCall(request).execute();

                JSONArray array = new JSONArray(response.body().string());

                for (int i=0; i<array.length(); i++){

                    JSONObject object = array.getJSONObject(i);

                    String data = object.getString("savetime");


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
            listAdapter.notifyDataSetChanged();
        }
    };

        task.execute(id);
}

}
