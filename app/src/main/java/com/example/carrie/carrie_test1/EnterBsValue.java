package com.example.carrie.carrie_test1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class EnterBsValue extends AppCompatActivity {
    EditText etsugarvalue;
    public static String sugarvalue;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bstab);

        etsugarvalue = (EditText) findViewById(R.id.sugar);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memberid = getIntent().getExtras().getString("memberid");
                sugarvalue = etsugarvalue.getText().toString();
                if(sugarvalue.matches("")){
                    Toast.makeText(getApplicationContext(), "有地方忘了填哦", Toast.LENGTH_SHORT).show();
                }else {
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
                                    .url("http://54.65.194.253/Health_Calendar/insertbloodsugar.php?memberid=" + memberid + "&sugarvalue=" + sugarvalue)
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
                            Toast.makeText(getApplicationContext(), "成功送出", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(EnterBsValue.this, EnterBsBpActivity.class);
                            it.putExtra("memberid", memberid);
                            it.putExtra("sugarvalue", sugarvalue);
                            startActivity(it);
                            etsugarvalue.setText("");
                        }
                    };
                    task.execute();
                }



            }
        });
    }


}
