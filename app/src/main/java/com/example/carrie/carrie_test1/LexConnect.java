package com.example.carrie.carrie_test1;


import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.Manifest;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.lex.interactionkit.InteractionClient;
import com.amazonaws.mobileconnectors.lex.interactionkit.Response;
import com.amazonaws.mobileconnectors.lex.interactionkit.config.InteractionConfig;
import com.amazonaws.mobileconnectors.lex.interactionkit.listeners.AudioPlaybackListener;
import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.StringUtils;
import android.support.v4.app.ActivityCompat;
import android.widget.VideoView;

import static android.Manifest.permission.*;
import java.util.Locale;
import java.util.Map;

public class LexConnect extends AppCompatActivity{
    public static final String TAG = "Lex doing";
    private static final int REQUEST_RECORD_AUDIO = 0;
    private Context appContext;
    private InteractiveVoiceView voiceView;
    private TextView transcriptTextView;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lex_connect);
        transcriptTextView = (TextView) findViewById(R.id.transcriptTextView);
        responseTextView = (TextView) findViewById(R.id.responseTextView);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 無權限，向使用者請求
            ActivityCompat.requestPermissions(this, new String[] {RECORD_AUDIO, READ_EXTERNAL_STORAGE}, REQUEST_RECORD_AUDIO);
        }
        init();
        StringUtils.isBlank("notempty");
    }

    @Override
    public void onBackPressed() {
        exit();
    }
    private void exit() {
        finish();
    }

    public void init(){
        appContext = getApplicationContext();
        voiceView = (InteractiveVoiceView) findViewById(R.id.voiceInterface);
        CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(
                appContext.getResources().getString(R.string.identity_id_test),
                Regions.fromName(appContext.getResources().getString(R.string.aws_region)));
        voiceView.getViewAdapter().setCredentialProvider(credentialsProvider);
        voiceView.getViewAdapter().setInteractionConfig(new InteractionConfig(appContext.getString(R.string.bot_name),appContext.getString(R.string.bot_alias)));
        voiceView.getViewAdapter().setAwsRegion(appContext.getString(R.string.aws_region));
        voiceView.setInteractiveVoiceListener(new InteractiveVoiceView.InteractiveVoiceListener() {
            @Override
            public void dialogReadyForFulfillment(Map slots, String intent) {
                Log.d("1212", String.format(
                        Locale.US,
                        "Dialog ready for fulfillment:\n\tIntent: %s\n\tSlots: %s",
                        intent,
                        slots.toString()));
            }

            @Override
            public void onResponse(Response response) {
                Log.d("1212", "Bot response: " + response.getTextResponse());
                Log.d("1212", "Transcript: " + response.getInputTranscript());
                responseTextView.setText(response.getTextResponse());
                transcriptTextView.setText(response.getInputTranscript());
                if(response.getTextResponse().equals("Yes, MineDicine is an incredible App ! Let me show you the logo !")) {
                    final VideoView videoView = (VideoView) LexConnect.this.findViewById(R.id.videoView);
                    MediaController mc = new MediaController(LexConnect.this);
                        videoView.setVisibility(View.VISIBLE);
                        videoView.setMediaController(mc);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
                        videoView.requestFocus();
                        videoView.start();
                        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                videoView.setVisibility(View.INVISIBLE);
                                ImageView showicon = (ImageView) findViewById(R.id.showicon);
                                showicon.setVisibility(View.VISIBLE);
                            }
                        });
                }
            }

            @Override
            public void onError(String responseText, Exception e) {
                Log.e("1212", "Error: " + responseText, e);
            }
        });

//        CognitoCredentialsProvider credentialsProvider = IdentityManager.getDefaultIdentityManager().getUnderlyingProvider();
//        voiceView.getViewAdapter().setCredentialProvider(credentialsProvider);
//        voiceView.getViewAdapter().setInteractionConfig(new InteractionConfig("YuanBot","$version")); //replace parameters with your botname, bot-alias
//        voiceView.getViewAdapter().setAwsRegion(getApplicationContext().getString(R.string.aws_region));
    }



}
