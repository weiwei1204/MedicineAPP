package com.example.carrie.carrie_test1;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by jonathan on 2017/8/7.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {
    private Context context;
    private List<Drug> my_data;

    public DrugAdapter(Context context, List<Drug> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drugcard,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.description2.setText(my_data.get(position).getChineseName());
        Glide.with(context).load(my_data.get(position).getImage()).into(holder.imageView2);
        holder.indication2.setText(my_data.get(position).getIndication());

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView description2;
        public ImageView imageView2;
        public TextView indication2;

        public ViewHolder(View itemView) {
            super(itemView);
            description2 = (TextView) itemView.findViewById(R.id.description2);
            imageView2 = (ImageView) itemView.findViewById(R.id.image2);
            indication2 = (TextView) itemView.findViewById(R.id.indication2);
        }
    }
}
