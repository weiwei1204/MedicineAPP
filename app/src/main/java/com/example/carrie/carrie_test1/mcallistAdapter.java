package com.example.carrie.carrie_test1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rita on 2017/8/16.
 */

public class mcallistAdapter extends BaseAdapter {

    private Context mcontext;
    private List<m_calendar> mcallist;

    public mcallistAdapter(Context mcontext, List<m_calendar> mcallist) {
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
        TextView mcaldate=(TextView)v.findViewById(R.id.mcaldate);
        TextView mcalcomplete=(TextView)v.findViewById(R.id.mcalcomplete);

        mcalname.setText(mcallist.get(position).getMcalname());
        mcaldate.setText(mcallist.get(position).getMcaldate());
        mcalcomplete.setText(mcallist.get(position).getMcalpercent());

        v.setTag(mcallist.get(position).getId());


        return v;
    }
}
