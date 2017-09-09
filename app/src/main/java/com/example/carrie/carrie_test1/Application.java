package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/9/9.
 */

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.auth.core.IdentityManager;
import android.util.Log;
import com.amazonaws.regions.Regions;

import android.support.multidex.MultiDexApplication;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
/**
 * Application class responsible for initializing singletons and other common components.
 */
public class Application extends MultiDexApplication {
    private static final String LOG_TAG = Application.class.getSimpleName();
    public static PinpointManager pinpointManager;


    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        initializeApplication();
        Log.d(LOG_TAG, "Application.onCreate - Application initialized OK");

    }

    private void initializeApplication() {

        AWSConfiguration awsConfiguration = new AWSConfiguration(getApplicationContext());

        // If IdentityManager is not created, create it
        if (IdentityManager.getDefaultIdentityManager() == null) {
            IdentityManager identityManager =
                    new IdentityManager(getApplicationContext(), awsConfiguration);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }
        final AWSCredentialsProvider credentialsProvider =
                IdentityManager.getDefaultIdentityManager().getCredentialsProvider();

        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                getApplicationContext(),
                credentialsProvider,
                awsConfiguration);

        Application.pinpointManager = new PinpointManager(pinpointConfig);

        pinpointManager.getSessionClient().startSession();


    }
}
