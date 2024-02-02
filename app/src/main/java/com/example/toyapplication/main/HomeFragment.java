package com.example.toyapplication.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toyapplication.Adaptor.CategoryAdapter;
import com.example.toyapplication.Database;
import com.example.toyapplication.Domain.Category;
import com.example.toyapplication.R;
import com.example.toyapplication.information.User;

public class HomeFragment extends Fragment {
    public static final Category[] categories = {
            new Category("Dresses", R.drawable.cat_1),
            new Category("Tops", R.drawable.cat_2),
            new Category("Skirts", R.drawable.cat_3),
            new Category("Pants", R.drawable.cat_4),
            new Category("Jackets", R.drawable.cat_5),
    };
    private Context context;
    private User user;
    private TextView TV_name;
    private RecyclerView fHome_RV_categories;
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
        TV_name = root.findViewById(R.id.TV_name);
        fHome_RV_categories = root.findViewById(R.id.fHome_RV_categories);
    }

    private void initVars() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(context, categories );
        fHome_RV_categories.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        fHome_RV_categories.setHasFixedSize(true);
        fHome_RV_categories.setItemAnimator(new DefaultItemAnimator());
        fHome_RV_categories.setAdapter(categoryAdapter);

    }

    public void setUser(User user) {
        this.user = user;
        TV_name.setText("Hello " + user.getFirstName() + ", ");
    }

}
