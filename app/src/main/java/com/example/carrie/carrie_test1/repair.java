package com.example.carrie.carrie_test1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class repair extends AppCompatActivity {

    String n_name;
    String n_email;
    String n_gender;
    String n_height;
    String n_weight;
    String n_birth;
    String n_memberid;
    String n_googleid;
    RequestQueue requestQueue2;
    String repairdata = "http://54.65.194.253/Member/repairdata.php";
    SQLiteDatabase sqLiteDatabase;
    public static final String DATABASE_NAME = "MedicineTest.db";
    public static final String TABLE_NAME = "Member";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        Bundle bundle = getIntent().getExtras();
        n_memberid = memberdata.getMember_id();
        n_googleid= memberdata.getGoogle_id();
        Log.d("wwwwww", n_memberid);
        Log.d("wwwwww", n_googleid);

        String st1= memberdata.getName();
        n_name = memberdata.getName();//get 中文名字
        TextView name=(TextView) findViewById(R.id.repairName);
        name.setText(st1);
        Log.d("rrr","1");

        String st2= memberdata.getEmail();
        n_email = memberdata.getEmail();//get 中文名字
        TextView email=(TextView) findViewById(R.id.repairemailc);
        email.setText(st2);
        Log.d("rrr","2");


        String st3= getIntent().getExtras().getString("gender_man", "not found");
        n_gender = bundle.getString("gender_man");//get 中文名字
        if(n_gender.equals("1")){
            TextView gender=(TextView) findViewById(R.id.repairgender);
            gender.setText("男生");
        }else
        {
            TextView gender=(TextView) findViewById(R.id.repairgender);
            gender.setText("女生");
        }

        Log.d("rrr","3");

        String st4= getIntent().getExtras().getString("height", "not found");
        n_height = bundle.getString("height");//get 中文名字
        EditText high=(EditText) findViewById(R.id.repairheightc);
        high.setText(st4);
        Log.d("rrr","4");

        String st5= getIntent().getExtras().getString("weight", "not found");
        n_weight = bundle.getString("weight");//get 中文名字
        EditText wei=(EditText) findViewById(R.id.repairweightc);
        wei.setText(st5);
        Log.d("rrr","5");

        String st6= getIntent().getExtras().getString("birth", "not found");
        n_birth = bundle.getString("birth");//get 中文名字
        EditText birth=(EditText) findViewById(R.id.reapiragec);
        birth.setText(st6);
        Log.d("rrr","6");


    }






            public void gotoperson(View v) { //回到個人資訊頁
                updatePersonalInfor();
                Bundle bundle = new Bundle();
                bundle.putString("googleid", n_googleid);
                Log.d("wwwwww", n_googleid);
                bundle.putString("memberid", n_memberid);
                Log.d("wwwwww", n_memberid);
                bundle.putString("name", n_name);
                Log.d("wwwwww", n_name);

                bundle.putString("gender_man",n_gender);
                bundle.putString("weight",n_weight);
                bundle.putString("height", n_height);
                bundle.putString("birth", n_birth);
                Intent it = new Intent(this, PersonalInformationctivity.class);
                it.putExtras(bundle);

                startActivity(it);

            }

    private void updatePersonalInfor() {
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());

        EditText higha=(EditText) findViewById(R.id.repairheightc);
        n_height=higha.getText().toString();

        EditText weia=(EditText) findViewById(R.id.repairweightc);
        n_weight=weia.getText().toString();

        EditText birtha=(EditText) findViewById(R.id.reapiragec);
        n_birth=birtha.getText().toString();

         final StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, repairdata,new  Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read repairdata.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("memberid", n_memberid);
                parameters.put("height", n_height);
                parameters.put("weight",n_weight);
                parameters.put("birth",n_birth);
                Log.d("dddddd", parameters.toString());
                Log.d("=dddddd","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
        updatePersonalInformationSQL();
        updatePersonalInformationStatic();
    }
    public void updatePersonalInformationSQL(){
        ContentValues contentValues = new ContentValues(3);
        contentValues.put("height",n_height);
        contentValues.put("weight",n_weight);
        contentValues.put("birth",n_birth);
        //sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "id =" + memberdata.getMember_id(), null);

    }
    public void updatePersonalInformationStatic(){
        RepairData.weight = n_weight;
        RepairData.height = n_height;
        RepairData.birth = n_birth;

    }


    public void goback(View v) {
                finish();


            }

};

