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

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by shana on 2017/8/8.
 */

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder>{

    private Context context;
    private List<MyData> my_data;
    private String m_calid;

    public CustomAdapter2(Context context, List<MyData> my_data , String mcalid) {
        this.context = context;
        this.my_data = my_data;
        this.m_calid = mcalid;
    }

    public void setmcalid(String mcalid){
        this.m_calid=mcalid;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position ) {

     //   holder.description.setText(my_data.get(position).getDescription());
        holder.indication.setText(my_data.get(position).getIndication());
        holder.englishName.setText(my_data.get(position).getEnglishName());
        holder.chineseName.setText(my_data.get(position).getChineseName());
      //  holder.licenseNumber.setText(my_data.get(position).getLicenseNumber());
       Glide.with(context).load(my_data.get(position).getImage_link()).into(holder.imageView);


        holder.drugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Click positioin"+ position,Toast.LENGTH_LONG).show();
                Log.d("customadapter2","1");
                Intent it =new Intent(context,FourthActivity.class);
                Log.d("customadapter2","2");
                Bundle bundle = new Bundle();
                Log.d("customadapter2","3");
                bundle.putInt("id", my_data.get(position).getId());
                Log.d("customadapter2","4");
                bundle.putString("image", my_data.get(position).getImage_link());
                bundle.putString("chineseName", my_data.get(position).getChineseName());
                bundle.putString("indication", my_data.get(position).getIndication());
                bundle.putString("englishName", my_data.get(position).getEnglishName());
                bundle.putString("licenseNumber", my_data.get(position).getLicenseNumber());
                bundle.putString("category", my_data.get(position).getCategory());
                bundle.putString("component", my_data.get(position).getComponent());
                bundle.putString("maker_Country", my_data.get(position).getMaker_Country());
                bundle.putString("applicant", my_data.get(position).getApplicant());
                bundle.putString("maker_Name", my_data.get(position).getMaker_Name());
                bundle.putString("m_calid",m_calid);
                Log.d("customadapter2","5");
                it.putExtras(bundle);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
                Log.d("customadapter2","6");

//                Log.d("qqqqq3",m_calid);

            }
        });

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
        public Button drugButton;
        public  TextView licenseNumber;
        public View repair;

        public ViewHolder(View itemView) {
            super(itemView);
      //      description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            indication= (TextView) itemView.findViewById(R.id.indication);
            englishName= (TextView) itemView.findViewById(R.id.englishName);
            chineseName= (TextView) itemView.findViewById(R.id.chineseName);
            drugButton=(Button) itemView.findViewById(R.id.drugButton);
            licenseNumber=(TextView) itemView.findViewById(R.id.licenseNumber);

        }
    }
}
