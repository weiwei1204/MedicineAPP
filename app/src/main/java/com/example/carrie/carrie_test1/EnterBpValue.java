package com.example.carrie.carrie_test1;

import android.content.Context;
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

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.DeleteEndpointRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.NotFoundException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SetEndpointAttributesRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static String googleid;
    Button btn;
    AmazonSNSClient client ;
    public static String arnStorage;
    public static String token;
    public static String mtoken;
    public String send="今天的血壓過高要小心哦";
    public String send2="您的廢物病患今天血壓過高哦";
    public String url = "http://54.65.194.253/Monitor/getMonitorToken.php?member_id="+memberdata.getMember_id();
    ArrayList<String> TokenList = new ArrayList<String>();
    String mtokens;
    AmazonSNSClient sns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bptab);
        Context appContext = getApplicationContext();
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(appContext,"us-east-1:9d8262a8-fa3c-4449-91bd-fc4841000a24", Regions.US_EAST_1);
        sns = new AmazonSNSClient(cognitoCachingCredentialsProvider);
        token = FirebaseInstanceId.getInstance().getToken();
        getMonitorToken();
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
                final String googleid = getIntent().getExtras().getString("googleid");
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
                            if(Integer.parseInt(highmmhg)>=140){
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                        if(token!=null) {
                                            sendRegistrationToServer();
                                            finish();
                                        }
                                        //DynamoDB calls go here
                                    }
                                };
                                Thread mythread = new Thread(runnable);
                                mythread.start();

                            }
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
                            it.putExtra("my_google_id",googleid);
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
    //*********************************************************************************************
    //*********************************************************************************************
    // below doing SNS things
    private void sendRegistrationToServer() {
        Log.d("9988","do aaa");

        String endpointArn = retrieveEndpointArn();
//        String topicArn = retrieveTopicArn();
        token = FirebaseInstanceId.getInstance().getToken();
//        StaticCredentialsProvider creds = new StaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
//        AWSCredentialsProvider provider = new StaticCredentialsProvider(credentials);
//        client = new AmazonSNSClient(new BasicAWSCredentials(accessKey,secretKey));
        Log.d("7070","client: "+client);
        Log.d("7070","token: "+token);
        boolean updateNeeded = false;
        boolean createNeeded = (null == endpointArn);


        if (createNeeded) {
            // No platform endpoint ARN is stored; need to call createEndpoint.
            endpointArn = createEndpoint();
            createNeeded = false;
        }


        System.out.println("Retrieving platform endpoint data...");
        // Look up the platform endpoint and make sure the data in it is current, even if
        // it was just created.
        try {
            GetEndpointAttributesRequest geaReq = new GetEndpointAttributesRequest().withEndpointArn(endpointArn);
            GetEndpointAttributesResult geaRes = sns.getEndpointAttributes(geaReq);

//            GetTopicAttributesRequest topicAttributesRequest = new GetTopicAttributesRequest().withTopicArn(topicArn);
//            GetTopicAttributesResult topicAttributesResult = client.getTopicAttributes(topicAttributesRequest);

            updateNeeded = !geaRes.getAttributes().get("Token").equals(token) || !geaRes.getAttributes().get("Enabled").equalsIgnoreCase("true");

        } catch (NotFoundException nfe) {
            // We had a stored ARN, but the platform endpoint associated with it
            // disappeared. Recreate it.
            createNeeded = true;
        }

        if (createNeeded) {
            createEndpoint();
//            createTopic();
        }

        System.out.println("updateNeeded = " + updateNeeded);

        if (updateNeeded) {
            // The platform endpoint is out of sync with the current data;
            // update the token and enable it.
            System.out.println("Updating platform endpoint " + endpointArn);
            Map attribs = new HashMap();
            attribs.put("Token", token);
            attribs.put("Enabled", "true");
            SetEndpointAttributesRequest saeReq = new SetEndpointAttributesRequest().withEndpointArn(endpointArn).withAttributes(attribs);

//            SetTopicAttributesRequest setTopicAttributesRequest = new SetTopicAttributesRequest().withTopicArn(topicArn).withAttributeName("SendMessage");
            sns.setEndpointAttributes(saeReq);
//            client.setTopicAttributes(setTopicAttributesRequest);
        }
    }
    private String createEndpoint() {
        String endpointArn = null;
        try {
//            String token = FirebaseInstanceId.getInstance().getToken();
            System.out.println("Creating platform endpoint with token " +token);
            String applicationArn = "arn:aws:sns:us-east-1:610465842429:app/GCM/PillHelper";
//            String topicArn = "arn:aws:sns:us-east-1:610465842429:SendMessage";
            CreatePlatformEndpointRequest cpeReq = new CreatePlatformEndpointRequest().withPlatformApplicationArn(applicationArn).withToken(token);
            CreatePlatformEndpointResult cpeRes = sns.createPlatformEndpoint(cpeReq);
            endpointArn = cpeRes.getEndpointArn();
            PublishRequest publishRequest = new PublishRequest().withTargetArn(endpointArn).withMessage(send);
            sns.publish(publishRequest);
            DeleteEndpointRequest deleteEndpointRequest = new DeleteEndpointRequest().withEndpointArn(endpointArn);
            sns.deleteEndpoint(deleteEndpointRequest);
            //////////////////////////////////////////////////上面送自己下面送監控者

            Log.d("3333","TokenList's Size: "+TokenList.size());
            for(int i = 0; i<TokenList.size();i++) {
                mtokens = TokenList.get(i);
                Log.d("2424","mtokensssss"+mtokens);
                Log.d("2424", "mToken: " + mtoken);
                CreatePlatformEndpointRequest cpeReq2 = new CreatePlatformEndpointRequest().withPlatformApplicationArn(applicationArn).withToken(mtokens);
                CreatePlatformEndpointResult cpeRes2 = sns.createPlatformEndpoint(cpeReq2);
                String endpointArn2 = cpeRes2.getEndpointArn();
                PublishRequest publishRequest2 = new PublishRequest().withTargetArn(endpointArn2).withMessage(send2);
                sns.publish(publishRequest2);
                DeleteEndpointRequest deleteEndpointRequest2 = new DeleteEndpointRequest().withEndpointArn(endpointArn2);
                sns.deleteEndpoint(deleteEndpointRequest2);
            }

        } catch (InvalidParameterException ipe) {
            String message = ipe.getErrorMessage();
            System.out.println("Exception message: " + message);
            Pattern p = Pattern.compile(".*Endpoint (arn:aws:sns[^ ]+) already exists " + "with the same token.*");
            Matcher m = p.matcher(message);
            if (m.matches()) {
                // The platform endpoint already exists for this token, but with
                // additional custom data that
                // createEndpoint doesn't want to overwrite. Just use the
                // existing platform endpoint.
                endpointArn = m.group(1);
            } else {
                // Rethrow the exception, the input is actually bad.
                throw ipe;
            }
        }
        storeEndpointArn(endpointArn);
        return endpointArn;
    }
//

    /**
     * @return the ARN the app was registered under previously, or null if no
     *         platform endpoint ARN is stored.
     */
    private String retrieveEndpointArn() {
        // Retrieve the platform endpoint ARN from permanent storage,
        // or return null if null is stored.
        return arnStorage;
    }

    /**
     * Stores the platform endpoint ARN in permanent storage for lookup next time.
     * */
    private void storeEndpointArn(String endpointArn) {
        // Write the platform endpoint ARN to permanent storage.
        arnStorage = endpointArn;
    }

    public void getMonitorToken(){
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {
                TokenList = new ArrayList<>();

                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String member_id = object.getString("supervisor_id");
                        mtoken = object.getString("token");
                        TokenList.add(mtoken);

                        Log.d("2424","monitor's Token: "+mtoken);
                        Log.d("2424","TokenList's Size: "+TokenList.size());
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.d("2424","error: "+e.toString());
                }

            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("777",error.toString());
                    }
                }){
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("member_id",memberdata.getMember_id());
                Log.d("nn1122",parameters.toString());
                return parameters;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}
