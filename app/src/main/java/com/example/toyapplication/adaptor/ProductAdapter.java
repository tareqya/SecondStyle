package com.example.toyapplication.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toyapplication.R;
import com.example.toyapplication.callback.ProductListener;
import com.example.toyapplication.domain.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Product> products;
    private Context context;
    private ProductListener productListener;

    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
    }

    public void setProductListener(ProductListener productListener){
        this.productListener = productListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = getItem(position);
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;

        Glide.with(context).load(product.getImageUrl()).into(productViewHolder.product_IMG_icon);
        productViewHolder.product_TV_price.setText(product.getPrice() + " ₪");
        productViewHolder.product_TV_size.setText(product.getSize());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public Product getItem(int pos){
        return this.products.get(pos);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_IMG_icon;
        public TextView product_TV_price;
        public TextView product_TV_size;
        public Button product_BTN_add;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_IMG_icon = itemView.findViewById(R.id.product_IMG_icon);
            product_TV_price = itemView.findViewById(R.id.product_TV_price);
            product_TV_size = itemView.findViewById(R.id.product_TV_size);
            product_BTN_add = itemView.findViewById(R.id.product_BTN_add);

            product_BTN_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(productListener != null){
                        int pos = getAdapterPosition();
                        Product product = getItem(pos);
                        productListener.onClick(product);
                    }
                }
            });
        }
    }
}
