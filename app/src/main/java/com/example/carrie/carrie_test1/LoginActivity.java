package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kosalgeek.asynctask.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,AsyncResponse {

    private LinearLayout Prof_Section2;
    private SignInButton SignIn;
    public TextView Name, Email;
    public GoogleApiClient googleApiCliente;
    private static final int REQ_CODE = 9001;
    String gname, gemail, googleid;
    Uri gphoto;
    RequestQueue requestQueue;
    String insertUrl = "http://54.65.194.253/Member/login.php";
    public static final String DATABASE_NAME = "MedicineTest.db";
    SQLiteDatabase sqLiteDatabase;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Prof_Section2 = (LinearLayout) findViewById(R.id.prof_section2);

        SignIn = (SignInButton) findViewById(R.id.btn_login);
        Name = (TextView) findViewById(R.id.Name);
        Email = (TextView) findViewById(R.id.email);
        SignIn.setOnClickListener(this);
        Prof_Section2.setVisibility(View.VISIBLE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiCliente = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


    }


    @Override
    public void processFinish(String result) {

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                signIn();
                break;
//            case R.id.setbtn:
//                signOut();
//                break;
        }

    }


    public void gotoMain() { //連到首頁
        Log.d("rrr", "4");
        dialog = ProgressDialog.show(LoginActivity.this,
                "讀取中", "載入資料中...",true);
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    getpersonal();
                    Thread.sleep(3000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    dialog.dismiss();
                }
            }
        }).start();
        Intent it = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", gname);
        bundle.putString("email", gemail);
        bundle.putString("name",gname);
        bundle.putString("email",gemail);
        bundle.putString("googleid",googleid);
        memberdata.setName(gname);
        memberdata.setEmail(gemail);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
    }


    public void gotorow(View v) { //連到聊天機器人頁面
        Intent it = new Intent(this, Beacon.class);
    }

    public void gotoInformation(){ //連到個人資訊頁面
        Log.d("rrr", "3");

        Intent it = new Intent(this,informationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",gname);
        bundle.putString("email",gemail);
        bundle.putString("googleid",googleid);
        Log.d("mm",bundle.getString("email"));
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦

        startActivity(it);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void  signIn() {


        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiCliente);
        startActivityForResult(intent,REQ_CODE);


    }

//    private   void  signOut() {
//        Log.d("hh","5");
//
//        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
//            @Override
//            public void onResult(@NonNull Status status) {
//
//                    updateUI(false);
//
//            }
//        });
//        Log.d("hh","6");
//
//    }


    private void handleResult(GoogleSignInResult result){

        if (result.isSuccess()){//取google的值
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String google =account.getId();
            Uri photo = account.getPhotoUrl();
            gname=name;
            gemail=email;
            googleid=google;
            gphoto=photo;
            memberdata.setName(gname);
            memberdata.setEmail(gemail);

            Log.d("rrr123", "1");

            member();
            String username="rita";
            updateUI(true);

        }
        else {
            updateUI(false);
        }

    }


    public void member() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rrr", "1");
                Log.d("rrr", response);

                if(response.contains("success")){//檢查是否為新會員
                    gotoMain();

                }
                else if(response.contains("nodata")){
                    gotoInformation();

                }
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
//                parameters.put("username", gname);
//                parameters.put("password", gemail);
                parameters.put("google_id", googleid);
                Log.d("my", parameters.toString());
                return parameters;
            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);



    }



    private void updateUI(boolean isLogin){

        if (isLogin){
            Log.d("rrr", "2");


//            Prof_Section.setVisibility(View.VISIBLE);
//            SignIn.setVisibility(View.GONE);
        }
        else {
            Prof_Section2.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
            Log.d("hh","5");

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    public void gotoFirstAvtivity(View v){ //連到搜尋藥品資訊頁面
        Intent it = new Intent(this,druginfo.class);
        startActivity(it);
    }



    public void goback(View v){
        finish();
    }

    public void getpersonal() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Member/personal.php?google_id=" + googleid;
                OkHttpClient client = new OkHttpClient();
                Log.d("ppppp", insertUrl);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(insertUrl)
                        .build();
                Log.d("ppppp", "2222");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("ppppp", "1111");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("ppppp", array.toString());
                    JSONObject object = array.getJSONObject(0);
                    Log.d("ppppp", "16666");

                    if ((object.getString("id")).equals("nodata")) {
                        Log.d("okht2tp", "4442222");
                        //normalDialogEvent();
                    } else {

                    }


                    Log.d("searchtest", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        object = array.getJSONObject(i);
                        addData(object.getString("id"),object.getString("name"), object.getString("email")
                                , object.getString("gender_man"), object.getString("weight"), object.getString("height")
                                , object.getString("birth"), object.getString("google_id"),object.getString("photo"));


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {



            }
        };
        task.execute();

    }
    public void addData(String a ,String b,String c ,String d,String e ,String f,String g ,String h,String i){
        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
        ContentValues contentValues = new ContentValues(9);
        contentValues.put("id",a);
        contentValues.put("name",b);
        contentValues.put("email",c);
        contentValues.put("genderman",d);
        contentValues.put("weight",e);
        contentValues.put("height",f);
        contentValues.put("birth",g);
        contentValues.put("google_id",h);
        contentValues.put("photo",i);


        sqLiteDatabase.insert("Member",null,contentValues);


    }



}



