package com.example.carrie.carrie_test1;

import android.content.Intent;
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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.kosalgeek.asynctask.AsyncResponse;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,AsyncResponse {

    private LinearLayout Prof_Section2;
    private SignInButton SignIn;
    public TextView Name,Email;
    public GoogleApiClient googleApiCliente;
    private static final int REQ_CODE = 9001;
    String gname,gemail,googleid;
    RequestQueue requestQueue;
    String insertUrl = "http://140.136.47.56/client2/insert.php/";
    String inputStr;




@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Prof_Section2= (LinearLayout)findViewById(R.id.prof_section2);

        SignIn = (SignInButton)findViewById(R.id.btn_login);
        Name = (TextView)findViewById(R.id.Name);
        Email = (TextView)findViewById(R.id.email);
        SignIn.setOnClickListener(this);
        Prof_Section2.setVisibility(View.VISIBLE);

    GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiCliente = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }

    @Override
    public void processFinish(String result){

        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                signIn();
                break;
//            case R.id.setbtn:
//                signOut();
//                break;
        }

    }


    public void gotoMainActivity(View v){ //連到聊天機器人頁面
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void  signIn() {


        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiCliente);
        startActivityForResult(intent,REQ_CODE);


    }

    private  void  signOut() {


        Auth.GoogleSignInApi.signOut(googleApiCliente).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }


    private void handleResult(GoogleSignInResult result){

        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String google =account.getId();
            gname=name;
            gemail=email;
            googleid=google;

            member();
            String username="rita";
            updateUI(true);

        }
        else {
            updateUI(false);
        }

    }


    public void member(){

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("asdfg","1");
//                        Log.i("asdfg",response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("username", gname);
                        parameters.put("password", gemail);
                        parameters.put("google_id", googleid);
                        Log.d("my",parameters.toString());
                        check();
//                        try {
//                            Log.d("qqqqq","333");
//
//                            URL url = new URL(insertUrl);
//                            HttpURLConnection httpHandler = (HttpURLConnection) url.openConnection();
//                            httpHandler.setRequestMethod("GET");
//                            httpHandler.setDoInput(true);
//                            InputStream is = httpHandler.getInputStream();
//                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                            inputStr = br.readLine();
//                            Log.d("qqqqq","555");
//
//                            Log.d("qqqqq",inputStr);
//                        } catch(Exception e){
//                            Log.d("ErrorMessage", e.getLocalizedMessage());
//                        }
                        return parameters;


                    }
                };
                requestQueue.add(request);



    }


    public void check() {
        Log.d("aaa","1");
        try {
            URL url = new URL(insertUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d("aaa","2");

//            connection.connect();
//            Log.d("aaa","3");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            Log.d("aaa","3");
            InputStream is = connection.getInputStream();
            Log.d("aaa","4");

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            Log.d("aaa","5");

            while ((inputStr = streamReader.readLine()) != null) {
                Log.d("aaa","6");

                Log.d("aaa",inputStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(boolean isLogin){

        if (isLogin){

                Intent it = new Intent(this,informationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",gname);
            bundle.putString("email",gemail);
            Log.d("mm",bundle.getString("email"));
            it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                startActivity(it);

//            Prof_Section.setVisibility(View.VISIBLE);
//            SignIn.setVisibility(View.GONE);
        }
        else {
            Prof_Section2.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }





    public void goback(View v){
        finish();
    }


}



