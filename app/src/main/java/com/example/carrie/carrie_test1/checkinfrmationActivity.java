package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class checkinfrmationActivity extends Activity {

    private TextView Namec,Emailc,heightc,weightc,agec,effectc;
    private RadioButton radiomanc,radiowomanc;
    private String googleid;
    private Button btnreturn,btnok;
    String email,name,height,weight,age,effect,gender;
    RequestQueue requestQueue;
    String insertUrl = "http://54.65.194.253/Member/insert.php";
    String inserMonitortUrl = "http://54.65.194.253/Monitor/addMonitor.php";

    public static final String DATABASE_NAME = "MedicineTest.db";
    public static final String TABLE_NAME = "Member";
    public static final String col_id = "id";
    public static final String col_name = "name";
    public static final String col_email = "email";
    public static final String col_genderman = "genderman";
    public static final String col_weight = "weight";
    public static final String col_height = "height";
    public static final String col_birth = "birth";
    public static final String col_google_id = "google_id";
    public static final String col_photo = "photo";
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinfrmation);

        Namec = (TextView)findViewById(R.id.Namec);
        Emailc = (TextView)findViewById(R.id.emailc);
        heightc = (TextView)findViewById(R.id.heightc);
        weightc = (TextView)findViewById(R.id.weightc);
        agec = (TextView)findViewById(R.id.agec);
        effectc = (TextView)findViewById(R.id.effectc);
        radiomanc = (RadioButton)findViewById(R.id.radiomanc);
        radiowomanc = (RadioButton)findViewById(R.id.radiowomanc);
        btnreturn = (Button)findViewById(R.id.btnreturn);
        btnok = (Button)findViewById(R.id.btnok);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        name = bundle.getString("name");
        height = bundle.getString("height");
        weight = bundle.getString("weight");
        age = bundle.getString("age");
        effect = bundle.getString("effect");
        gender = bundle.getString("gender");
        googleid=bundle.getString("googleid");

        Namec.setText(name);
        Emailc.setText(email);
        heightc.setText(height);
        weightc.setText(weight);
        agec.setText(age);
        effectc.setText(effect);
        if (gender.equalsIgnoreCase("1")){
            radiomanc.setChecked(true);
            Log.d("zz",gender);
        }else if (gender.equalsIgnoreCase("2")){
            radiowomanc.setChecked(true);
            Log.d("zz",gender);

        }


    }


    public void insertmember() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("name", name);
                parameters.put("email", email);
                parameters.put("gender_man",gender);
                parameters.put("weight",weight);
                parameters.put("height",height);
                parameters.put("birth",age);
                parameters.put("google_id", googleid);
                Log.d("my111", parameters.toString());
                Log.d("my","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        String SQL_String = "CREATE  TABLE  IF NOT EXISTS " + TABLE_NAME + "(" + col_id + " VARCHAR(32)," + col_name + " VARCHAR(32)," + col_email + " VARCHAR(32)," + col_genderman +" VARCHAR(32),"+ col_weight +" VARCHAR(32),"+ col_height +" VARCHAR(32),"+ col_birth +" VARCHAR(32),"+ col_google_id +" VARCHAR(32),"+  col_photo +" VARCHAR(32)"+")";
        sqLiteDatabase.execSQL(SQL_String);
        Cursor c  = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (c.getCount() == 0) {
            addData("0",name,email,gender,weight,height,age,googleid,"null");
            c  = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        }
        sqLiteDatabase.close();

    }
    public void insertMonitor() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, inserMonitortUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getApplicationContext(), "Error read addMonitor.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("Mname", name);
                parameters.put("Memail", email);
                parameters.put("Mgender_man",gender);
                parameters.put("Mweight",weight);
                parameters.put("Mheight",height);
                parameters.put("Mbirth",age);
                parameters.put("Mgoogle_id", googleid);
                Log.d("monitoradd", parameters.toString());
                Log.d("monitoraddCheck","checck!!!");
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void gotoinActivity(View v){ //連到個人資訊頁面
        Intent it = new Intent(this,informationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",Namec.getText().toString());
        bundle.putString("email", Emailc.getText().toString());
//        bundle.putString("height", heightc.getText().toString());
//        bundle.putString("weight", weightc.getText().toString());
//        bundle.putString("age", agec.getText().toString());
//        bundle.putString("effect", effectc.getText().toString());
//        bundle.putString("gender", radioresult);
        bundle.putString("googleid", googleid);
//        Log.d("mm1",radioresult);
//        Log.d("mm",bundle.getString("age"));
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void gotoMainActivity(View v){ //連到首頁
        insertmember();
        insertMonitor();
        Intent it = new Intent(this,FirstSaveBsBpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("googleid", googleid);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }
    public void addData(String a ,String b,String c ,String d,String e ,String f,String g ,String h,String i){
        ContentValues contentValues = new ContentValues(9);
        contentValues.put("id","0");
        contentValues.put("name",b);
        contentValues.put("email",c);
        contentValues.put("genderman",d);
        contentValues.put("weight",e);
        contentValues.put("height",f);
        contentValues.put("birth",g);
        contentValues.put("google_id",h);
        contentValues.put("photo",i);


        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);


    }

}
