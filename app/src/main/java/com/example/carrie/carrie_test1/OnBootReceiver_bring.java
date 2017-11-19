package com.example.carrie.carrie_test1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rita on 2017/9/21.
 */

public class OnBootReceiver_bring extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ArrayList<String> bring = new ArrayList<String>();
        bring = intent.getExtras().getStringArrayList("extra");
        String string="";
        for (int i=0;i<bring.size();i+=2){
            string+=bring.get(i)+"：Rssi="+bring.get(i+1)+"\n";
        }
        Log.d("bbb",string);
        Intent startIntent = new Intent(context, Transparent_bring.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("extra",string);
        startIntent.putExtras(bundle);
        context.startActivity(startIntent);
    }
}
