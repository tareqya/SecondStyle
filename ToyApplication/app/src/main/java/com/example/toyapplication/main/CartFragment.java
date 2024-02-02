package com.example.toyapplication.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toyapplication.R;

public class CartFragment extends Fragment {
    private Activity context;
    private FragmentManager fragmentManager;


    public CartFragment(Activity context, FragmentManager fragmentManager) {
        // Required empty public constructor
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    // Setter method to pass required values
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}