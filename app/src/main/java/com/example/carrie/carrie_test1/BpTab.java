package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by jonathan on 2017/8/10.
 */

public class BpTab extends Fragment {
    String insertbpvalue = "http://54.65.194.253/Health_Calendar/insertbloodpressure.php";
    EditText ethighmmhg;
    EditText etlowmmhg;
    EditText etbpm;
    public static String highmmhg = "";
    public static String lowmmhg = "";
    public static String bpm ="";
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bptab, container, false);

        String memberid = getActivity().getIntent().getExtras().getString("memberid");
        ethighmmhg = (EditText) rootView.findViewById(R.id.highmmhg);

        etlowmmhg = (EditText) rootView.findViewById(R.id.lowmmhg);

        etbpm = (EditText) rootView.findViewById(R.id.bpm);

        btn = (Button) rootView.findViewById(R.id.button1);
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
                final String memberid = getActivity().getIntent().getExtras().getString("memberid");


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
                            Log.d("mon_idte213st", "12212321231");
                            Log.d("mon_idte213st", "http://54.65.194.253/Health_Calendar/insertbloodpressure.php?memberid=" + memberid + "&hmmhg=" + highmmhg + "&lmmhg=" + lowmmhg + "&bpm=" + bpm);
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
                Toast.makeText(getActivity().getApplicationContext(),"成功送出",Toast.LENGTH_SHORT).show();

                    Intent it = new Intent(v.getContext(),BpRecord.class);
                    it.putExtra("memberid",memberid);
//                    it.putExtra("highmmhg",highmmhg);
//                    it.putExtra("lowmmhg",lowmmhg);
//                    it.putExtra("bpm",bpm);
                    startActivity(it);


            }
        });
        return rootView;

    }

    public void setInsertbpvalue() {
        Log.d("bbbbb","123456");
        final String memberid = getActivity().getIntent().getExtras().getString("memberid");


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

