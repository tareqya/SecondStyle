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

public class CartProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Product> products;
    private Context context;
    private ProductListener productListener;

    public CartProductAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item,parent,false);
        return new CartProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = getItem(position);
        CartProductViewHolder cartProductViewHolder = (CartProductViewHolder) holder;

        cartProductViewHolder.cartProduct_TV_price.setText(product.getPrice() + " ₪");
        cartProductViewHolder.cartProduct_TV_size.setText(product.getSize());
        Glide.with(context).load(product.getImageUrl()).into(cartProductViewHolder.cartProduct_IV_image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public Product getItem(int pos){
        return this.products.get(pos);
    }

    public class CartProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView cartProduct_IV_image;
        public TextView cartProduct_TV_price;
        public TextView cartProduct_TV_size;
        public Button cartProduct_BTN_remove;

        public CartProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProduct_IV_image = itemView.findViewById(R.id.cartProduct_IV_image);
            cartProduct_TV_price = itemView.findViewById(R.id.cartProduct_TV_price);
            cartProduct_TV_size = itemView.findViewById(R.id.cartProduct_TV_size);
            cartProduct_BTN_remove = itemView.findViewById(R.id.cartProduct_BTN_remove);

            cartProduct_BTN_remove.setOnClickListener(new View.OnClickListener() {
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
