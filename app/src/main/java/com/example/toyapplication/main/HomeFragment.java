package com.example.toyapplication.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toyapplication.App;
import com.example.toyapplication.adaptor.CategoryAdapter;
import com.example.toyapplication.Database;
import com.example.toyapplication.adaptor.ProductAdapter;
import com.example.toyapplication.callback.CategoryListener;
import com.example.toyapplication.callback.ProductListener;
import com.example.toyapplication.callback.ProductsCallBack;
import com.example.toyapplication.domain.Category;
import com.example.toyapplication.R;
import com.example.toyapplication.domain.Product;
import com.example.toyapplication.information.User;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    public static final Category[] categories = {
            new Category("Dresses", R.drawable.cat_1).setSelected(true),
            new Category("Tops", R.drawable.cat_2),
            new Category("Skirts", R.drawable.cat_3),
            new Category("Pants", R.drawable.cat_4),
            new Category("Jackets", R.drawable.cat_5),
    };
    private Context context;
    private RecyclerView fHome_RV_categories;
    private RecyclerView fHome_RV_products;
    private Database database;

    public HomeFragment(Context context){
        this.context = context;
        database = new Database();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(root);
        initVars();
        return root;
    }

    private void findViews(View root) {
        fHome_RV_categories = root.findViewById(R.id.fHome_RV_categories);
        fHome_RV_products = root.findViewById(R.id.fHome_RV_products);
    }

    private void initVars() {

        database.setProductsCallBack(new ProductsCallBack() {
            @Override
            public void onProductsFetchComplete(ArrayList<Product> products) {
                ProductAdapter productAdapter = new ProductAdapter(context, products);
                productAdapter.setProductListener(new ProductListener() {
                    @Override
                    public void onClick(Product product) {
                        if(App.cart.addProduct(product)){
                            Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Product already exist in the cart", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                fHome_RV_products.setLayoutManager(new GridLayoutManager(context, 2));
                fHome_RV_products.setHasFixedSize(true);
                fHome_RV_products.setItemAnimator(new DefaultItemAnimator());
                fHome_RV_products.setAdapter(productAdapter);
            }
        });

        CategoryAdapter categoryAdapter = new CategoryAdapter(context, categories);
        categoryAdapter.setCategoryListener(new CategoryListener() {
            @Override
            public void onCategoryClick(Category category) {
                for(int i = 0; i < categories.length; i++){
                    if(categories[i].getName().equals(category.getName())){
                        categories[i].setSelected(true);
                    }else{
                        categories[i].setSelected(false);
                    }
                }
                categoryAdapter.setCategories(categories);
                fHome_RV_categories.getAdapter().notifyDataSetChanged();
                database.fetchClothesByCategoryName(category.getName());
            }
        });

        fHome_RV_categories.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        fHome_RV_categories.setHasFixedSize(true);
        fHome_RV_categories.setItemAnimator(new DefaultItemAnimator());
        fHome_RV_categories.setAdapter(categoryAdapter);

        database.fetchClothesByCategoryName(categories[0].getName());
    }

}
