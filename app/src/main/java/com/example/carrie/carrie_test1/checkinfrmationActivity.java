package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class checkinfrmationActivity extends Activity {

    private TextView Namec,Emailc,heightc,weightc,agec,effectc;
    private RadioButton radiomanc,radiowomanc;
    private String googleid;
    private Button btnreturn,btnok;

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
        String email = bundle.getString("email");
        String name = bundle.getString("name");
        String height = bundle.getString("height");
        String weight = bundle.getString("weight");
        String age = bundle.getString("age");
        String effect = bundle.getString("effect");
        String gender = bundle.getString("gender");
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


    public void gotoinActivity(View v){ //連到首頁
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
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
    }

    public void goback(View v){
        finish();
    }
}
