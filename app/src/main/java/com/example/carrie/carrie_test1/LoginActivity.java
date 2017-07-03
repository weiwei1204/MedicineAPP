package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener,AsyncResponse {

    private LinearLayout Prof_Section2;
    private SignInButton SignIn;
    private TextView Name,Email;
    public GoogleApiClient googleApiCliente;
    private static final int REQ_CODE = 9001;
    EditText ET_name,ET_username,ET_email;
    String name1,username1,email1;
    Button insert;
    RequestQueue requestQueue;




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

//        HashMap postData=new HashMap();
//
//        postData.put("mobile","android");
//        postData.put("Username","weiwei");
//        postData.put("Password","12345");
//
//        PostResponseAsyncTask task = new PostResponseAsyncTask(this,postData);
//        task.execute("http://127.0.0.1/client2/member.php");
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
            String username="rita";
            Name.setText(name);
            Email.setText(email);
            updateUI(true);

        }
        else {
            updateUI(false);
        }

    }

    private void updateUI(boolean isLogin){

        if (isLogin){

                Intent it = new Intent(this,MainActivity.class);
                startActivity(it);

//            Prof_Section.setVisibility(View.VISIBLE);
//            SignIn.setVisibility(View.GONE);
        }
        else {
            Prof_Section2.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
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



