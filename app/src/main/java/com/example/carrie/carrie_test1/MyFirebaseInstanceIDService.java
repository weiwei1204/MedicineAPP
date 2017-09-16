package com.example.carrie.carrie_test1;

import android.util.Log;

import com.google.android.gms.wearable.MessageApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by jonathan on 2017/9/11.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public FirebaseAuth mAuth;
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        mAuth = FirebaseAuth.getInstance();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
    }


}
