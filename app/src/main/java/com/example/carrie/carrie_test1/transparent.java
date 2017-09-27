package com.example.carrie.carrie_test1;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class transparent extends AppCompatActivity {

    private MediaPlayer media_song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String mcalname=null;
        Bundle bundle = getIntent().getExtras();
        mcalname = bundle.getString("extra");
        media_song=MediaPlayer.create(this,R.raw.piano);//raw裡的音樂
        media_song.setLooping(true);
        media_song.start();


        AlertDialog.Builder builder = new AlertDialog.Builder(transparent.this);
                        builder.setMessage(mcalname)
                                .setTitle("藥忘記帶囉")
                                .setCancelable(false)
                                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        media_song.stop();
                                        media_song.reset();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main3_drawer, menu);
//        return true;
//    }

}
