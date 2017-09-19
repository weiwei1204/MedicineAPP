package com.example.carrie.carrie_test1;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rita on 2017/8/16.
 */

public class mcallistAdapter extends BaseAdapter {

    private Context mcontext;
    private List<m_calendar> mcallist;

    public mcallistAdapter(Context mcontext, List<m_calendar> mcallist ) {
        this.mcontext = mcontext;
        this.mcallist = mcallist;
    }

    @Override
    public int getCount() {
        return mcallist.size();
    }

    @Override
    public Object getItem(int position) {
        return mcallist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mcontext,R.layout.item_mcalendarlist,null);
        TextView mcalname=(TextView)v.findViewById(R.id.mcalname);
        TextView mcaldelay=(TextView)v.findViewById(R.id.mcaldelay);
        TextView mcalcomplete=(TextView)v.findViewById(R.id.mcalcomplete);
        LinearLayout mcalstatus = (LinearLayout)v.findViewById(R.id.mcalstatus);
        mcalname.setText(mcallist.get(position).getMcalname());
        mcalcomplete.setText(mcallist.get(position).getMcalpercent());
        if (mcallist.get(position).getStatus()==1){     //如果排程完成
            mcalstatus.setBackgroundColor(Color.rgb(173, 173, 173));
        }
        if (Integer.valueOf(mcallist.get(position).getMcaldelay())>5){  //      延遲超過五次顯示紅字
            mcaldelay.setText(Html.fromHtml("<font color='red'>"+mcallist.get(position).getMcaldelay()+"</font>"));
            mcalstatus.setBackgroundColor(Color.rgb(184, 0, 0));

        }else {
            mcaldelay.setText(mcallist.get(position).getMcaldelay());

        }

        v.setTag(mcallist.get(position).getId());


        return v;
    }
}
