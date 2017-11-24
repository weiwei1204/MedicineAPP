package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class PersonalInformationctivity extends AppCompatActivity {



    private Button repair1;
    public static String my_google_id = "";
    public static String email=" ";
    public static String name="";
    public static String n_member="";

    public static  String googleid="";
    public static String nname="";
    public static String gender="";
    public static String weight="";
    public static String height="";
    public static String birth="";
    String p1;

    //public TextView gmailPersonal;
    RepairData repairData;
    public  TextView namePerson;
    String rep;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_informationctivity);
        repair1 = (Button) findViewById(R.id.repair1);
        Bundle bundle = getIntent().getExtras();
        //setContentView(R.layout.activity_main);

        my_google_id = RepairData.google_id;
        nname = RepairData.name;
        gender = RepairData.gender_man;
        height=RepairData.height;
        weight=RepairData.weight;
        birth=RepairData.birth;
        if(gender.equals("1")){
            gender = "男";
        }else
        {
            gender = "女";
        }



//        Log.d("p", p_name);
//        Log.d("p", p_birth);


        String[] personal = {"姓名: "+ nname,"性別: "+gender,"身高: "+height, "體重: "+weight,"生日: "+birth};
        ListAdapter buckyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, personal);
        ListView buckyListview= (ListView)findViewById(R.id.listPersonal);
        buckyListview.setAdapter(buckyAdapter);


       // getgid();
        insteremial();
//        TextView email=(TextView) findViewById(R.id.gmailPersonal);
//        email.setText(repairData.getEmail());
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            Intent myIntent = new Intent();
//            Intent it = new Intent(this, MainActivity.class);
//            myIntent = new Intent(PersonalInformationctivity.this, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("gender_man",gender);
//            bundle.putString("googleid",my_google_id);
//            bundle.putString("weight",weight);
//            bundle.putString("gender_man",gender);
//            bundle.putString("height",height);
//            bundle.putString("birth",birth);
//            it.putExtras(bundle);
//            startActivity(myIntent);
//            this.finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String google_id= memberdata.getGoogle_id();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("googleid", google_id);

        startActivity(i);
        finish();
    }

    public void gotorepair(View v) { //連到修改個人資訊
        Intent it = new Intent(this, repair.class);
        startActivity(it);
    }

    public void getgid() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Member/googleid.php?google_id=" + my_google_id;
                OkHttpClient client = new OkHttpClient();
                Log.d("idddddddd", insertUrl);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(insertUrl)
                        .build();
                Log.d("idddddddd", "2222");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("idddddddd", "1111");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("idddddddd", array.toString());
                    JSONObject object = array.getJSONObject(0);
                    Log.d("idddddddd", "16666");

                    if ((object.getString("id")).equals("nodata")) {
                        Log.d("okht2tp", "4442222");
                        //normalDialogEvent();
                    } else {
                        name = object.getString("name");
                        gender = object.getString("gender_man");
                        email = object.getString("email");
                        Log.d("eeee", email);
                    }


                    Log.d("searchtest", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        object = array.getJSONObject(i);

                        repairData = new RepairData(object.getString("name"), object.getString("email")
                                , object.getString("gender_man"), object.getString("weight"), object.getString("height")
                                , object.getString("birth"), object.getString("google_id"));

                        Log.d("rrrr", repairData.getEmail());

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

                Bundle bundle1 = new Bundle();

                bundle1.putString("name", repairData.getName());
                TextView name=(TextView) findViewById(R.id.namePerson);
                name.setText(repairData.getName());

                bundle1.putString("email", repairData.getEmail());
                TextView email=(TextView) findViewById(R.id.gmailPersonal);
                email.setText(repairData.getEmail());

                Log.d("eeee","8");


                Log.d("customadapter2", "6");

            }
        };
        task.execute();

    }


            public void insteremial() {
                repair1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(PersonalInformationctivity.this, repair.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("googleid", my_google_id);
                        bundle.putString("name", name);
                        bundle.putString("weight", weight);
                        bundle.putString("email", email);
                        bundle.putString("height", height);
                        bundle.putString("gender_man", gender);
                        bundle.putString("birth", birth);
                        bundle.putString("memberid", memberdata.getMember_id());
                        //bundle.putString("googleid", my_google_id);
                        Log.d("RRRRR", my_google_id);
                        Log.d("mmmm", n_member);
                        Log.d("aaaaa", "12");
                        it.putExtras(bundle);

//                bundle1.putInt("entertype",1);

                        startActivity(it);
                    }

                });
            }

            public void goback(View v) {

                finish();
            }


}


