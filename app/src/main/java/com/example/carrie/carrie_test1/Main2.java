package com.example.carrie.carrie_test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.analytics.monetization.AmazonMonetizationEventBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.DeleteEndpointRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.NotFoundException;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main2 extends Activity {


    private static int SPLASH_TIME_OUT=500;
   // ImageView imageView;
    public static PinpointManager pinpointManager;
    public static final String LOG_TAG = Main2.class.getSimpleName();
    DynamoDBMapper dynamoDBMapper;
    public String send="hello";
    private TextView mTextMessage;
    String userId = "";
    private FirebaseAuth mAuth;
    AmazonSNSClient client ;
    public static String arnStorage;
    public static String token;
    public static String arnTopic;
    AmazonSNSClient sns;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }


    @Override
    protected void onPause() {
        super.onPause();

        // unregister notification receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register notification receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver,
                new IntentFilter(PushListenerService.ACTION_PUSH_NOTIFICATION));
    }
    private final BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Received notification from local broadcast. Display it in a dialog.");

            Bundle data = intent.getBundleExtra(PushListenerService.INTENT_SNS_NOTIFICATION_DATA);
            String message = PushListenerService.getMessage(data);

            new AlertDialog.Builder(Main2.this)
                    .setTitle("Push notification")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("9090", "token: " + token);



//        Intent intent = new Intent(this, MyFirebaseMessagingService.class);
//        this.startService(intent);





        //     imageView = (ImageView)findViewById(R.id.imageView);
        //   Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.main2_animation);
        //imageView.setAnimation(animation);
        Context appContext = getApplicationContext();
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(appContext,"us-east-1:9d8262a8-fa3c-4449-91bd-fc4841000a24",Regions.US_EAST_1);
        sns = new AmazonSNSClient(cognitoCachingCredentialsProvider);

        PinpointConfiguration config = new PinpointConfiguration(appContext, "391944f4b405494a8445c7c01d1455cf", Regions.US_EAST_1, cognitoCachingCredentialsProvider);

        this.pinpointManager = new PinpointManager(config);

        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        if (IdentityManager.getDefaultIdentityManager() == null) {
            IdentityManager identityManager = new IdentityManager(this, awsConfig);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }
        final AWSCredentialsProvider credentialsProvider = IdentityManager.getDefaultIdentityManager().getCredentialsProvider();
        if (pinpointManager == null) {
            PinpointConfiguration pinpointConfig = new PinpointConfiguration(getApplicationContext(), credentialsProvider, awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);
            final Activity self = this;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String deviceToken = InstanceID.getInstance(self).getToken("741829064706", GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                        Log.e("NotError", deviceToken);
                        pinpointManager.getNotificationClient().registerGCMDeviceToken(deviceToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        IdentityManager identityManager = new IdentityManager(appContext, awsConfig);
        IdentityManager.setDefaultIdentityManager(identityManager);
        identityManager.doStartupAuth(this, new StartupAuthResultHandler() {
            @Override
            public void onComplete(StartupAuthResult startupAuthResult) {
                // User identity is ready as unauthenticated user or previously signed-in user.
            }
        });

       


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Main2.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
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
//            topicArn = createTopic();
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
//    private String retrieveTopicArn(){
//        return arnTopic;
//    }
//    private void storeTopicArn(String topicArn){
//        arnTopic = topicArn;
//    }






}