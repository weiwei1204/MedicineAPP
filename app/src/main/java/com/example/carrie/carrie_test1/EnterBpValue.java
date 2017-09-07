package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EnterBpValue extends AppCompatActivity {
    String insertbpvalue = "http://54.65.194.253/Health_Calendar/insertbloodpressure.php";
    EditText ethighmmhg;
    EditText etlowmmhg;
    EditText etbpm;
    public static String highmmhg = "";
    public static String lowmmhg = "";
    public static String bpm ="";
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bptab);
        String memberid = getIntent().getExtras().getString("memberid");
        ethighmmhg = (EditText) findViewById(R.id.highmmhg);

        etlowmmhg = (EditText) findViewById(R.id.lowmmhg);

        etbpm = (EditText) findViewById(R.id.bpm);

        btn = (Button) findViewById(R.id.button1);
        Log.d("bbbbb", "12345");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highmmhg = ethighmmhg.getText().toString();
                lowmmhg = etlowmmhg.getText().toString();
                bpm = etbpm.getText().toString();
                setInsertbpvalue();
                Log.v("EditText", ethighmmhg.getText().toString());
                Log.v("EditText", etlowmmhg.getText().toString());
                Log.v("EditText", etbpm.getText().toString());
                final String memberid = getIntent().getExtras().getString("memberid");
                if(highmmhg.matches("") || lowmmhg.matches("") || bpm.matches("")) {
                    Toast.makeText(getApplicationContext(), "有地方忘了填哦", Toast.LENGTH_SHORT).show();
                }else{

                    AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                        @Override
                        protected Void doInBackground(Integer... integers) {
                            RequestBody formBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("memberid", memberid)
                                    .addFormDataPart("highmmhg", highmmhg)
                                    .addFormDataPart("lowmmhg", lowmmhg)
                                    .addFormDataPart("bpm", bpm)
                                    .build();
                            Log.d("bbbbb", "9999");
                            OkHttpClient client = new OkHttpClient();
                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    .url("http://54.65.194.253/Health_Calendar/insertbloodpressure.php?memberid=" + memberid + "&hmmhg=" + highmmhg + "&lmmhg=" + lowmmhg + "&bpm=" + bpm)
                                    .post(formBody)
                                    .build();
                            Log.d("bbbbb", "999");
                            try {
                                okhttp3.Response response = client.newCall(request).execute();
//                            Log.d("mon_idte213st", "12212321231");
//                            Log.d("mon_idte213st", "http://54.65.194.253/Health_Calendar/insertbloodpressure.php?memberid=" + memberid + "&hmmhg=" + highmmhg + "&lmmhg=" + lowmmhg + "&bpm=" + bpm);
//                            Log.d("mon_idte213st", "122");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            setInsertbpvalue();
                            Toast.makeText(getApplicationContext(), "成功送出", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(EnterBpValue.this, EnterBsBpActivity.class);
                            it.putExtra("memberid", memberid);
                            it.putExtra("highmmhg", highmmhg);
                            it.putExtra("lowmmhg", lowmmhg);
                            it.putExtra("bpm", bpm);
                            startActivity(it);
                            ethighmmhg.setText("");
                            etlowmmhg.setText("");
                            etbpm.setText("");


                        }
                    };
                    task.execute();



                }
            }
        });

    }

    public void setInsertbpvalue() {
        Log.d("bbbbb","123456");
        final String memberid = getIntent().getExtras().getString("memberid");


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("memberid", memberid)
                        .addFormDataPart("highmmhg", highmmhg)
                        .addFormDataPart("lowmmhg",lowmmhg)
                        .addFormDataPart("bpm",bpm)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://54.65.194.253/Health_Calendar/insertbloodpressure.php")
                        .post(formBody)
                        .build();
                Log.d("bbbbb","999");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("mon_idte213st", "12212321231");
                    Log.d("mon_idte213st", "122");

                } catch (IOException e) {
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

}
