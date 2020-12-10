package com.example.organicfarming;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CropListAdapter extends RecyclerView.Adapter<CropListAdapter.ViewHolder>{

    ArrayList<CropShowItem> arrayList = new ArrayList<>();
    Context context;

    public CropListAdapter(ArrayList<CropShowItem> arrayList){
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public CropListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.crop_item, parent, false);
        context=parent.getContext();
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CropListAdapter.ViewHolder holder, int position) {

        CropShowItem cropShowItem=arrayList.get(position);

        holder.cropName.setText(cropShowItem.getCropName());
        holder.cropDistance.setText(cropShowItem.cropDistance+ " KM");
        holder.cropAvailableSellers.setText("Available Sellers : " + cropShowItem.getCropSellers());
        Picasso.get().load(cropShowItem.getCropUrl()).error(R.drawable.onion).into(holder.cropImage);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CropShowActivity.class);
                intent.putExtra("crop",cropShowItem.getCropName());
                intent.putExtra("url",cropShowItem.getCropUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView cropImage;
        public TextView cropName;
        public TextView cropDistance;
        public TextView cropAvailableSellers;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cropImage=itemView.findViewById(R.id.crop_image);
            this.cropName=itemView.findViewById(R.id.crop_name);
            this.cropDistance=itemView.findViewById(R.id.crop_proximity);
            this.cropAvailableSellers=itemView.findViewById(R.id.crop_sellers);
            this.constraintLayout=itemView.findViewById(R.id.layout);


        }
    }

}
