package com.example.toyapplication.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toyapplication.App;
import com.example.toyapplication.Database;
import com.example.toyapplication.R;
import com.example.toyapplication.adaptor.CartProductAdapter;
import com.example.toyapplication.callback.OrderCallBack;
import com.example.toyapplication.callback.ProductListener;
import com.example.toyapplication.domain.Order;
import com.example.toyapplication.domain.Product;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class CartFragment extends Fragment {
    private Context context;
    private RecyclerView fCart_RV_products;
    private Button fCart_BTN_order;
    private TextView fCart_TV_totalPrice;
    private Database database;
    public CartFragment(Context context) {
        // Required empty public constructor
        this.context=context;
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fCart_BTN_order = view.findViewById(R.id.fCart_BTN_order);
        fCart_RV_products = view.findViewById(R.id.fCart_RV_products);
        fCart_TV_totalPrice = view.findViewById(R.id.fCart_TV_totalPrice);
    }

    private void setTotalPrice(){
       double price = App.cart.totalPrice();
        fCart_TV_totalPrice.setText("Total Price: "+ price + " ₪");
    }
    public void displayCartUI(){
        CartProductAdapter cartProductAdapter = new CartProductAdapter(context, App.cart.getProducts());
        cartProductAdapter.setProductListener(new ProductListener() {
            @Override
            public void onClick(Product product) {
                App.cart.removeProduct(product);
                setTotalPrice();
                fCart_RV_products.getAdapter().notifyDataSetChanged();
            }
        });

        fCart_RV_products.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fCart_RV_products.setHasFixedSize(true);
        fCart_RV_products.setItemAnimator(new DefaultItemAnimator());
        fCart_RV_products.setAdapter(cartProductAdapter);
        this.setTotalPrice();
    }

    private void initVars() {
        database.setOrderCallBack(new OrderCallBack() {
            @Override
            public void onOrderAddComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Order added successfully", Toast.LENGTH_SHORT).show();
                    App.cart.cleanCart();
                    setTotalPrice();
                    fCart_RV_products.getAdapter().notifyDataSetChanged();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show();

                }
            }
        });
        fCart_BTN_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order()
                        .setDate(new Date())
                        .setUid(database.getCurrentUser().getUid())
                        .setProducts(App.cart.getProducts());

                database.createOrder(order);
            }
        });
    }
}