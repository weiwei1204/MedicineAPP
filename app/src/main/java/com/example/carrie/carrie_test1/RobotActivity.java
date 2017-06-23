package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RobotActivity extends Activity {

    EditText name, paasword;
    Button insert;
    RequestQueue requestQueue;
    String insertUrl = "http://140.136.47.47/client2/insert.php/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myTag", "This is my message");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        Log.d("my", "view");

        name = (EditText) findViewById(R.id.e1);
        paasword = (EditText) findViewById(R.id.e2);
        insert = (Button) findViewById(R.id.insert);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("myTag", "load");

        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("myTag", "click");

                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("my", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("my", error.toString());
                    }
                }){
                   protected Map<String,String> getParams() throws AuthFailureError{
                       Map<String, String> parameters = new HashMap<String, String>();
                       parameters.put("username", name.getText().toString());
                       parameters.put("password", paasword.getText().toString());
                       Log.d("my",parameters.toString());
                       return parameters;

                   }
                };
                Log.d("my",request.toString());
                requestQueue.add(request);
            }

        });



    }





    public void gotologinActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,LoginActivity.class);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }
}
