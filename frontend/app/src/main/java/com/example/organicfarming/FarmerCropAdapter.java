package com.example.organicfarming;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FarmerCropAdapter extends RecyclerView.Adapter<FarmerCropAdapter.ViewHolder> {

    ArrayList<FarmerCrop> arrayList;
    Context context;

    public FarmerCropAdapter(Context context, ArrayList<FarmerCrop> arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public FarmerCropAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.farmer_home_crop_item, parent, false);
        FarmerCropAdapter.ViewHolder viewHolder = new FarmerCropAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerCropAdapter.ViewHolder holder, int position) {
        holder.tvCropItemName.setText(arrayList.get(position).getName());
        holder.tvCropItemViews.setText(Integer.toString(arrayList.get(position).getViews()));
        Picasso.get().load(arrayList.get(position).getImage()).into(holder.imgCropItem);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvCropItemName, tvCropItemViews;
        ImageView imgCropItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCropItemName=itemView.findViewById(R.id.tvCropItemName);
            tvCropItemViews=itemView.findViewById(R.id.tvCropItemViews);
            imgCropItem=itemView.findViewById(R.id.imgCropItem);
        }
    }
}
