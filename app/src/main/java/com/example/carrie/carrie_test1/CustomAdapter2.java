package com.example.carrie.carrie_test1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by shana on 2017/8/8.
 */

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder>{

    private Context context;
    private List<MyData> my_data;

    public CustomAdapter2(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

     //   holder.description.setText(my_data.get(position).getDescription());
        holder.indication.setText(my_data.get(position).getIndication());
        holder.englishName.setText(my_data.get(position).getEnglishName());
        holder.chineseName.setText(my_data.get(position).getChineseName());
       Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

      //  public TextView description;
        public ImageView imageView;
        public TextView indication;
        public TextView englishName;
        public TextView chineseName;


        public ViewHolder(View itemView) {
            super(itemView);
      //      description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            indication= (TextView) itemView.findViewById(R.id.indication);
            englishName= (TextView) itemView.findViewById(R.id.englishName);
            chineseName= (TextView) itemView.findViewById((R.id.chineseName));

        }
    }
}
