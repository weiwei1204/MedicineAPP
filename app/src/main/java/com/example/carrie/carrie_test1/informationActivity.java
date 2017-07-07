package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class informationActivity extends AppCompatActivity{

    private TextView Name,Email;
    private static final int REQ_CODE = 9001;
    RequestQueue requestQueue;
    String insertUrl = "http://140.136.47.47/client2/insert.php/";
    String showtUrl = "http://140.136.47.47/client2/showmember.php/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Name = (TextView)findViewById(R.id.Name);
        Email = (TextView)findViewById(R.id.email);

        LoginActivity la = new LoginActivity();

        Bundle bundle = getIntent().getExtras();
        String gemail = bundle.getString("email");
        String gname = bundle.getString("name");
        Name.setText(gname);
        Email.setText(gemail);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

    }


    public void showmember(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showtUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray students=response.getJSONArray("tbl_client");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }

        });
    }



    public void gotoMainActivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }
}
