package com.example.carrie.carrie_test1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void onBindViewHolder(ViewHolder holder, int position) {


    //  holder.chineseName.setText(my_data.get(position).getchineseName());

        holder.description.setText(my_data.get(position).getName());
        //Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {

        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView chineseName;
        public ImageView imageView;
        public TextView indication;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            chineseName = (TextView) itemView.findViewById(R.id.chineseName);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            indication = (TextView) itemView.findViewById(R.id.indication);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.monitor_image);

    }
    }
}
