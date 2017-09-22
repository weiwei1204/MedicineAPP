package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.services.sns.AmazonSNS;
import com.google.android.gms.wearable.MessageApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.google.android.gms.wearable.DataMap.TAG;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.GetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesResult;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.NotFoundException;
import com.amazonaws.services.sns.model.SetEndpointAttributesRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by jonathan on 2017/9/11.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public FirebaseAuth mAuth;
    private static final String TAG = "MyFirebaseIDService";
    AWSCredentials awsCredentials = new AWSCredentials() {
        @Override
        public String getAWSAccessKeyId() {
            return "AKIAIJBWJ3GH6OBW7PZQ";
        }

        @Override
        public String getAWSSecretKey() {
            return "9soQ6XG2V7WCzYAbyYf3bZMuFoov4dudF7zFso";
        }
    };

    final AWSCredentialsProvider credentialsProvider = IdentityManager.getDefaultIdentityManager().getCredentialsProvider();
    AmazonSNS client =  new AmazonSNSClient(credentialsProvider);
    public static String arnStorage;
    public static String token;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        mAuth = FirebaseAuth.getInstance();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("8080", "Refreshed token: " + refreshedToken);
        Log.d("8080", "credentialsProvider: " + awsCredentials.getAWSAccessKeyId());

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
        Intent intent = new Intent(this, Main2.class);
        startService(intent);
        Log.d("9988","do this");

    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d("9988","do aaa");

        String endpointArn = retrieveEndpointArn();
        token = FirebaseInstanceId.getInstance().getToken();

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
            GetEndpointAttributesResult geaRes = client.getEndpointAttributes(geaReq);

            updateNeeded = !geaRes.getAttributes().get("Token").equals(token) || !geaRes.getAttributes().get("Enabled").equalsIgnoreCase("true");

        } catch (NotFoundException nfe) {
            // We had a stored ARN, but the platform endpoint associated with it
            // disappeared. Recreate it.
            createNeeded = true;
        }

        if (createNeeded) {
            createEndpoint();
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
            client.setEndpointAttributes(saeReq);
        }
    }
    private String createEndpoint() {
        String endpointArn = null;
        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            System.out.println("Creating platform endpoint with token " +token);
            String applicationArn = "arn:aws:sns:us-east-1:610465842429:app/GCM/PillHelper";
            String topicArn = "arn:aws:sns:us-east-1:610465842429:SendMessage";
            CreatePlatformEndpointRequest cpeReq = new CreatePlatformEndpointRequest().withPlatformApplicationArn(applicationArn).withToken(token);
            CreatePlatformEndpointResult cpeRes = client.createPlatformEndpoint(cpeReq);
            endpointArn = cpeRes.getEndpointArn();
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
