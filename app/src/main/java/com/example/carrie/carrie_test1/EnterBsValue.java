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

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class EnterBsValue extends AppCompatActivity {
    EditText etsugarvalue;
    public static String sugarvalue;
    Button btn;
    public static String googleid;
    AmazonSNSClient client ;
    public static String arnStorage;
    public static String token;
    public static String arnTopic;
    public String send="今天的血糖過高要小心哦";
    private static String accessKey = "";
    private static String secretKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = FirebaseInstanceId.getInstance().getToken();
        setContentView(R.layout.bstab);
        etsugarvalue = (EditText) findViewById(R.id.sugar);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memberid = getIntent().getExtras().getString("memberid");
                Log.d("4441","memberid: "+memberid);
                final String googleid = getIntent().getExtras().getString("googleid");
                Log.d("4441","googleid: "+googleid);
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
                            if(Integer.parseInt(sugarvalue)>=140){
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
                            it.putExtra("my_google_id",googleid);
                            startActivity(it);
                            etsugarvalue.setText("");
                        }
                    };
                    task.execute();
                }



            }
        });

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
        AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretKey);
        AWSCredentialsProvider provider = new StaticCredentialsProvider(credentials);
        client = new AmazonSNSClient(new BasicAWSCredentials(accessKey,secretKey));
        Log.d("7070","client: "+client);
        Log.d("7070","token: "+token);
        boolean updateNeeded = false;
        boolean createNeeded = (null == endpointArn);


        if (createNeeded) {
            // No platform endpoint ARN is stored; need to call createEndpoint.
            endpointArn = createEndpoint();
//            topicArn = createTopic();
            createNeeded = false;
        }


        System.out.println("Retrieving platform endpoint data...");
        // Look up the platform endpoint and make sure the data in it is current, even if
        // it was just created.
        try {
            GetEndpointAttributesRequest geaReq = new GetEndpointAttributesRequest().withEndpointArn(endpointArn);
            GetEndpointAttributesResult geaRes = client.getEndpointAttributes(geaReq);

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
            client.setEndpointAttributes(saeReq);
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
            CreatePlatformEndpointResult cpeRes = client.createPlatformEndpoint(cpeReq);
            endpointArn = cpeRes.getEndpointArn();
            PublishRequest publishRequest = new PublishRequest().withTargetArn(endpointArn).withMessage(send);
            client.publish(publishRequest);
            DeleteEndpointRequest deleteEndpointRequest = new DeleteEndpointRequest().withEndpointArn(endpointArn);
            client.deleteEndpoint(deleteEndpointRequest);

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
//    private String createTopic() {
//        String topicArn = "arn:aws:sns:us-east-1:610465842429:SendMessage";
////        try {
//////            String token = FirebaseInstanceId.getInstance().getToken();
////            System.out.println("Creating platform endpoint with token " +token);
////
////            CreateTopicRequest topicRequest = new CreateTopicRequest().withName("SendMessage");
////            CreateTopicResult topicResult = client.createTopic(topicRequest);
////            topicArn = topicResult.getTopicArn();
////        } catch (InvalidParameterException ipe) {
////            String message = ipe.getErrorMessage();
////            System.out.println("Exception message: " + message);
////            Pattern p = Pattern.compile(".*Topic (arn:aws:sns[^ ]+) already exists " + "with the same token.*");
////            Matcher m = p.matcher(message);
////            if (m.matches()) {
////                // The platform endpoint already exists for this token, but with
////                // additional custom data that
////                // createEndpoint doesn't want to overwrite. Just use the
////                // existing platform endpoint.
////                topicArn = m.group(1);
////            } else {
////                // Rethrow the exception, the input is actually bad.
////                throw ipe;
////            }
////        }
//        storeTopicArn(topicArn);
//        return topicArn;
//
//    }

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


}
