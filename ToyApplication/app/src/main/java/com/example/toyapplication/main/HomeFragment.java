package com.example.toyapplication.main;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toyapplication.Adaptor.CategoryAdaptor;
import com.example.toyapplication.Adaptor.popularAdaptor;
import com.example.toyapplication.Domain.CategoryDomain;
import com.example.toyapplication.Domain.PopularDomain;
import com.example.toyapplication.R;
import com.example.toyapplication.information.User;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context context;
    private User user;
    private TextView TV_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TV_name = root.findViewById(R.id.TV_name);
        recyclerViewCategory(root);
        recyclerViewPopular(root);
        return root;
    }

    public void setUser(User user) {
        this.user = user;
        TV_name.setText("Hello " + user.getFirstName() + ", ");
    }

    private void recyclerViewCategory(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewCategoryList = root.findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Dresses", "cat_1"));
        category.add(new CategoryDomain("Tops", "cat_2"));
        category.add(new CategoryDomain("Skirts", "cat_3"));
        category.add(new CategoryDomain("Pants", "cat_4"));
        category.add(new CategoryDomain("Jackets", "cat_5"));

        RecyclerView.Adapter adapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopular(View root) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerViewPopularList = root.findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        ArrayList<PopularDomain> popularList = new ArrayList<>();
        popularList.add(new PopularDomain("dress", "dress_1", "has been worn once", 49.99));
        popularList.add(new PopularDomain("dress", "dress_2", "has been worn twice", 79.99));
        popularList.add(new PopularDomain("dress", "dress_3", "has been worn twice", 39.99));
        popularList.add(new PopularDomain("dress", "dress_4", "has been worn twice", 99.99));
        popularList.add(new PopularDomain("dress", "dress_5", "has been worn twice", 49.99));

        RecyclerView.Adapter adapter = new popularAdaptor(popularList);
        recyclerViewPopularList.setAdapter(adapter);
    }
}
