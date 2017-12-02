package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class informationActivity extends AppCompatActivity{



    final int listvalue[]= new int[3];
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
        ScrollView relativeLayout = (ScrollView) findViewById(R.id.relativelayout_info);

        Name = (TextView)findViewById(R.id.Name);
        Email = (TextView)findViewById(R.id.email);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
//        effect = (EditText) findViewById(R.id.effect);
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


        ListView listview;
        List<String> list;

        listview = (ListView) findViewById(R.id.list_chronic);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                        {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                                            {
                                                CheckedTextView chkItem = (CheckedTextView) v.findViewById(R.id.check1);
                                                if(chkItem.isChecked()==true){
                                                    chkItem.setChecked(false);
                                                }else {
                                                    chkItem.setChecked(true);
                                                    //Toast.makeText(MainActivity.this, "您點選了第 "+(position+1)+" 項", Toast.LENGTH_SHORT).show();
                                                }
                                                if (chkItem.isChecked()){
                                                    listvalue[position]=1;
                                                }else {
                                                    listvalue[position]=0;
                                                }

                                            }
                                        }
        );


        list = new ArrayList<String>();
        for(int x=1;x<4;x++)
        {
            list.add("高血壓");
            list.add("高血糖");
            list.add("高血脂");

        }

        List1Adapter adapterItem = new List1Adapter(this,list);
        listview.setAdapter(adapterItem);
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
            it.putExtra("numbers", listvalue);
            bundle.putString("name",Name.getText().toString());
            bundle.putString("email", Email.getText().toString());
            bundle.putString("height", height.getText().toString());
            bundle.putString("weight", weight.getText().toString());
            bundle.putString("age", age.getText().toString());
            //bundle.putString("effect", effect.getText().toString());
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
            }
//            }else if(effect.getText().toString().matches("") ) {
//                Toast.makeText(informationActivity.this, "Please enter effect", Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
