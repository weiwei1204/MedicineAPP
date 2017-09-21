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

public class OnBootReceiver  extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        ArrayList<String> lost = new ArrayList<String>();
        lost = intent.getExtras().getStringArrayList("extra");
        String mcalname="";
        for (int i=0;i<lost.size();i++){
            mcalname+=lost.get(i).toString()+"\n";
        }
        Log.d("bbb",mcalname);
        Intent startIntent = new Intent(context, transparent.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("extra", mcalname);
        startIntent.putExtras(bundle);
        context.startActivity(startIntent);
    }
}
