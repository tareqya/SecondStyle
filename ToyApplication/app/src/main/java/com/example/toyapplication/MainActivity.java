package com.example.toyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.toyapplication.Adaptor.CategoryAdaptor;
import com.example.toyapplication.Adaptor.popularAdaptor;
import com.example.toyapplication.Domain.CategoryDomain;
import com.example.toyapplication.Domain.PopularDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter,adapter2;
private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
    }
    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category=new ArrayList<>();
        category.add(new CategoryDomain("Dresses","cat_1"));
        category.add(new CategoryDomain("Tops","cat_2"));
        category.add(new CategoryDomain("Skirts","cat_3"));
        category.add(new CategoryDomain("Pants","cat_4"));
        category.add(new CategoryDomain("Jackets","cat_5"));

        adapter=new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }
    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        ArrayList<PopularDomain> popularList=new ArrayList<>();
        popularList.add(new PopularDomain("dress","dress_1","has been worn once",49.99));
        popularList.add(new PopularDomain("dress","dress_2","has been worn twice",79.99));
        popularList.add(new PopularDomain("dress","dress_3","has been worn twice",39.99));
        popularList.add(new PopularDomain("dress","dress_4","has been worn twice",99.99));
        popularList.add(new PopularDomain("dress","dress_5","has been worn twice",49.99));

        adapter2=new popularAdaptor(popularList);
        recyclerViewPopularList.setAdapter(adapter2);



    }
}