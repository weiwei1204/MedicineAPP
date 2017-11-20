package com.example.carrie.carrie_test1;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Transparent_bring extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String mcalname=null;
        Bundle bundle = getIntent().getExtras();
        mcalname = bundle.getString("extra");


        AlertDialog.Builder builder = new AlertDialog.Builder(Transparent_bring.this);
                        builder.setMessage(mcalname)
                                .setTitle("掃描結束，無應帶而未帶之藥品")
                                .setCancelable(false)
                                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

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
