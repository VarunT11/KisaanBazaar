package com.example.organicfarming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.awt.font.TextAttribute;
import java.security.PublicKey;
import java.util.ArrayList;

public class FarmerAdapter  extends RecyclerView.Adapter<FarmerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<FarmerItem> arrayList= new ArrayList<FarmerItem>();

    public FarmerAdapter(ArrayList<FarmerItem> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public FarmerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.farmer_item, parent, false);
        context=parent.getContext();
        FarmerAdapter.ViewHolder viewHolder = new FarmerAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerAdapter.ViewHolder holder, int position) {
        FarmerItem farmerItem= arrayList.get(position);

        holder.ratingBar.setRating(farmerItem.getRating());
        holder.name.setText(farmerItem.getName());
        holder.rate.setText("Rs "+ farmerItem.getRate() + "/ton");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,dist,rate;
        ImageView imageView;
        View time;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.seller_name);
            dist=itemView.findViewById(R.id.seller_proximity);
            imageView=itemView.findViewById(R.id.seller_image);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            time=itemView.findViewById(R.id.time);
            rate=itemView.findViewById(R.id.price);
        }
    }

}

