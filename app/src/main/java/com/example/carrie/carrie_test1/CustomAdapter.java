package com.example.carrie.carrie_test1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private Context context;
    private List<MyMonitorData> my_data;

    public CustomAdapter(Context context, List<MyMonitorData> my_data) {
        this.context = context;
        this.my_data = my_data;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmonitor,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.monitorName.setText(my_data.get(position).getName());
        holder.monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Click positioin"+ position,Toast.LENGTH_SHORT).show();
                Log.d("customadapter","1");
                Intent it =new Intent(context,SwipePlot.class);
                Log.d("customadapter","2");
                Bundle bundle = new Bundle();
                Log.d("customadapter","3");
                bundle.putInt("monitor_who", my_data.get(position).getId());
                bundle.putString("memberid", MonitorActivity.google_id);
                Log.d("customadapter","4");
                Log.d("customadapter", String.valueOf(my_data.get(position).getId()));
                it.putExtras(bundle);
                Log.d("customadapter","5");
                context.startActivity(it);
                Log.d("customadapter","6");
            }
        });
        //Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {

        return my_data.size();
    }



    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView monitorName;
        public Button monitorButton;

        public ViewHolder(View itemView) {
            super(itemView);
            //chineseName = (TextView) itemView.findViewById(R.id.chineseName);
            //imageView = (ImageView) itemView.findViewById(R.id.image);
            //indication = (TextView) itemView.findViewById(R.id.indication);
            monitorName = (TextView) itemView.findViewById(R.id.monitorName);
            imageView = (ImageView) itemView.findViewById(R.id.monitor_image);
            monitorButton = (Button) itemView.findViewById(R.id.monitorButton);


    }
    }

}
