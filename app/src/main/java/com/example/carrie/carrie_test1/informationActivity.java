package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class informationActivity extends AppCompatActivity{

    private TextView Name,Email;
    private EditText height,weight,age,effect;
    private RadioGroup rggender;
    private RadioButton radioman,ragiowoman;
    private Button check;
    private String radioresult;
    private String googleid;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Name = (TextView)findViewById(R.id.Name);
        Email = (TextView)findViewById(R.id.email);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        effect = (EditText) findViewById(R.id.effect);
        check = (Button)findViewById(R.id.buttonck);
        rggender = (RadioGroup)findViewById(R.id.rggender);
        radioman = (RadioButton)findViewById(R.id.radioman);
        ragiowoman = (RadioButton)findViewById(R.id.radiowoman);



        Bundle bundle = getIntent().getExtras();
        String gemail = bundle.getString("email");
        String gname = bundle.getString("name");
        googleid = bundle.getString("googleid");
        Name.setText(gname);
        Email.setText(gemail);

        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }


//    public void showmember(){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, showtUrl, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {JSONArray students=response.getJSONArray("tbl_client");
//                } catch (Exception e) {e.printStackTrace();}
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error){
//            }
//        });}


    public void show(){
        switch (rggender.getCheckedRadioButtonId()){
            case  R.id.radioman:
                radioresult="1";
                break;
            case R.id.radiowoman:
                radioresult="2";
                break;

        }
    }



    public void gotocheckinfrmationActivity(View v){ //連到個人資訊頁面頁面

        try{
            show();
            Intent it = new Intent(this,checkinfrmationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",Name.getText().toString());
            bundle.putString("email", Email.getText().toString());
            bundle.putString("height", height.getText().toString());
            bundle.putString("weight", weight.getText().toString());
            bundle.putString("age", age.getText().toString());
            bundle.putString("effect", effect.getText().toString());
            bundle.putString("gender", radioresult);
            bundle.putString("googleid", googleid);
            Log.d("mm1",radioresult);
            it.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
            startActivity(it);
        }catch(Exception obj){
            if(rggender.getCheckedRadioButtonId()==-1){
                Toast.makeText(informationActivity.this, "Please enter gender", Toast.LENGTH_SHORT).show();
            }else if(height.getText().toString().matches("") ) {
                Toast.makeText(informationActivity.this, "Please enter height", Toast.LENGTH_SHORT).show();
            }else if(weight.getText().toString().matches("") ) {
                Toast.makeText(informationActivity.this, "Please enter weight", Toast.LENGTH_SHORT).show();
            }else if(age.getText().toString().matches("") ) {
                Toast.makeText(informationActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
            }else if(effect.getText().toString().matches("") ) {
                Toast.makeText(informationActivity.this, "Please enter effect", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
