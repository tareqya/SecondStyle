package com.example.toyapplication.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toyapplication.Domain.Category;
import com.example.toyapplication.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Category[] categories;
    private Context context;
    public CategoryAdapter(Context context, Category[] categories){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        Category category = getItem(position);
        categoryViewHolder.category_IMG_icon.setImageResource(category.getImage());
        categoryViewHolder.category_TV_title.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public Category getItem(int pos){
        return categories[pos];
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView category_IMG_icon;
        public TextView category_TV_title;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_IMG_icon = itemView.findViewById(R.id.category_IMG_icon);
            category_TV_title = itemView.findViewById(R.id.category_TV_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
