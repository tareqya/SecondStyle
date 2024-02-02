package com.example.toyapplication.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;

import com.example.toyapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ShopActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;
    private CartFragment cartFragment;
    private FrameLayout main_frame_home, main_frame_addproduct, main_frame_profile;
    private BottomNavigationView btnhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_main);
        main_frame_home = findViewById(R.id.main_frame_home);
        main_frame_addproduct = findViewById(R.id.main_frame_addproduct);
        main_frame_profile = findViewById(R.id.main_frame_profile);
        btnhome = findViewById(R.id.btnhome);
        start();
    }

    private void start() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_home, homeFragment).commit();
        cartFragment = new CartFragment(this, fragmentManager);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_addproduct, cartFragment).commit();
        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_profile, profileFragment).commit();


        main_frame_profile.setVisibility(View.INVISIBLE);
        main_frame_addproduct.setVisibility(View.INVISIBLE);

        btnhome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    main_frame_profile.setVisibility(View.INVISIBLE);
                    main_frame_addproduct.setVisibility(View.INVISIBLE);
                    main_frame_home.setVisibility(View.VISIBLE);
                } else if (item.getItemId() == R.id.profile) {
                    main_frame_profile.setVisibility(View.VISIBLE);
                    main_frame_addproduct.setVisibility(View.INVISIBLE);
                    main_frame_home.setVisibility(View.INVISIBLE);
                } else if (item.getItemId() == R.id.addproduct) {
                    main_frame_profile.setVisibility(View.INVISIBLE);
                    main_frame_addproduct.setVisibility(View.VISIBLE);
                    main_frame_home.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        });
    }
}
