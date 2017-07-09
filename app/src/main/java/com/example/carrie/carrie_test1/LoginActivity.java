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
    String insertUrl = "http://192.168.100.9/client2/insert.php/";




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


    public void gotoMain(){ //連到首頁
        Log.d("rrr", "4");

        Intent it = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name",gname);
        bundle.putString("email",gemail);
        it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(it);
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
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", gname);
                parameters.put("password", gemail);
                parameters.put("google_id", googleid);
                Log.d("my", parameters.toString());
                return parameters;


            }
        };
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



