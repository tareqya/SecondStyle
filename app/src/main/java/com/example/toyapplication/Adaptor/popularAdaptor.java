package com.example.toyapplication.Adaptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toyapplication.Domain.PopularDomain;
import com.example.toyapplication.R;

import java.util.ArrayList;

public class popularAdaptor extends RecyclerView.Adapter<popularAdaptor.ViewHolder> {
    ArrayList<PopularDomain> categoryPopular;

    public popularAdaptor(ArrayList<PopularDomain> categoryPopular) {
        this.categoryPopular = categoryPopular;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull popularAdaptor.ViewHolder holder, int position) {
        holder.title.setText(categoryPopular.get(position).getTitle());
        holder.fee.setText(String.valueOf(categoryPopular.get(position).getFee()));


        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(categoryPopular.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return categoryPopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,fee;
        ImageView pic;
        TextView addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            title=itemView.findViewById(R.id.title);
//            fee=itemView.findViewById(R.id.fee);
//            pic=itemView.findViewById(R.id.pic);
//            addBtn=itemView.findViewById((R.id.addBtn));

        }
    }
}
