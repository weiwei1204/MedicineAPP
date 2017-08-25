package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by jonathan on 2017/8/10.
 */

public class BsTab extends Fragment {
    EditText etsugarvalue;
    public static String sugarvalue;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bstab, container, false);
        etsugarvalue = (EditText) rootView.findViewById(R.id.sugar);
        btn = (Button) rootView.findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memberid = getActivity().getIntent().getExtras().getString("memberid");
                sugarvalue = etsugarvalue.getText().toString();
                AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... integers) {
                        RequestBody formBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("memberid", memberid)
                                .addFormDataPart("sugarvalue", sugarvalue)
                                .build();
                        Log.d("bbbbb", "9999");
                        OkHttpClient client = new OkHttpClient();
                        okhttp3.Request request = new okhttp3.Request.Builder()
                                .url("http://54.65.194.253/Health_Calendar/insertbloodsugar.php?memberid=" + memberid + "&sugarvalue=" + sugarvalue )
                                .post(formBody)
                                .build();
                        Log.d("bbbbb", "999");
                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            Log.d("mon_idte213st", "12212321231");
                            Log.d("mon_idte213st", "http://54.65.194.253/Health_Calendar/insertbloodsugar.php?memberid=" + memberid + "&sugarvalue=" + sugarvalue);
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
                Intent it = new Intent(v.getContext(),BsRecord.class);
                it.putExtra("memberid",memberid);
                it.putExtra("sugarvalue",sugarvalue);
                startActivity(it);


            }
        });
        return rootView;
            }



    }




