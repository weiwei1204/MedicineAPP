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

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.StartupAuthResult;
import com.amazonaws.mobile.auth.core.StartupAuthResultHandler;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import static com.amazonaws.auth.policy.actions.DynamoDBv2Actions.Query;
import static com.google.android.gms.wearable.DataMap.TAG;

import com.amazonaws.mobileconnectors.pinpoint.*;
import com.amazonaws.regions.Regions;
import com.google.firebase.iid.FirebaseInstanceId;


public class Main2 extends Activity {


    private static int SPLASH_TIME_OUT=500;
   // ImageView imageView;
    public static PinpointManager pinpointManager;
    public static final String LOG_TAG = Main2.class.getSimpleName();
    DynamoDBMapper dynamoDBMapper;

    private TextView mTextMessage;
    String userId = "";


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



        //     imageView = (ImageView)findViewById(R.id.imageView);
        //   Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.main2_animation);
        //imageView.setAnimation(animation);
        Context appContext = getApplicationContext();
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(appContext,"IDENTITY_POOL_ID",Regions.US_EAST_1);

        PinpointConfiguration config = new PinpointConfiguration(appContext, "APP_ID", Regions.US_EAST_1, cognitoCachingCredentialsProvider);

        this.pinpointManager = new PinpointManager(config);

        AWSConfiguration awsConfig = new AWSConfiguration(appContext);
        if (IdentityManager.getDefaultIdentityManager() == null) {
            IdentityManager identityManager = new IdentityManager(this, awsConfig);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }
        final AWSCredentialsProvider credentialsProvider =
                IdentityManager.getDefaultIdentityManager().getCredentialsProvider();

        if (pinpointManager == null) {
            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    getApplicationContext(),
                    credentialsProvider,
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);
            final Activity self = this;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String deviceToken =
                                InstanceID.getInstance(self).getToken(
                                        "123456789Your_GCM_Sender_Id",
                                        GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                        Log.e("NotError", deviceToken);
                        pinpointManager.getNotificationClient()
                                .registerGCMDeviceToken(deviceToken);
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
                //DynamoDB calls go here
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();

    }





}