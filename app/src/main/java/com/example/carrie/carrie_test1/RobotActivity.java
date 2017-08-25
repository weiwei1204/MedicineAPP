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
    String insertUrl = "http://140.136.47.56/client2/insert.php/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);

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
                        Log.d("my1", response);
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
                Log.d("pp",request.toString());
                requestQueue.add(request);
            }

        });



    }

    public void gotoChatActivity(View v) { //連到chat頁面
        Intent it = new Intent(this, chat.class);

    }
    public void gotologinActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,LoginActivity.class);

        startActivity(it);
    }

    public void goback(View v){
        finish();
    }
}
