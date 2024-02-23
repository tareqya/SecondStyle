package com.example.toyapplication.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toyapplication.Database;
import com.example.toyapplication.Log_Activity;
import com.example.toyapplication.R;
import com.example.toyapplication.callback.UserCallBack;
import com.example.toyapplication.information.User;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private Context context;
    private CircleImageView profile_CIV_image;
    private TextView profile_TV_name;
    private TextView profile_TV_email;
    private CardView profile_CV_editDetails;
    private CardView profile_CV_logout;
    private Database database;
    private User currentUser;

    public ProfileFragment(Context context) {
        // Required empty public constructor
        this.context = context;
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        profile_CIV_image = view.findViewById(R.id.profile_CIV_image);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
        profile_CV_logout = view.findViewById(R.id.profile_CV_logout);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);

    }

    private void initVars() {
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(Task<Void> task) {

            }

            @Override
            public void fetchUserDataComplete(User user) {
                currentUser = user;
                if(user != null){
                    profile_TV_name.setText(user.getFullName());
                    profile_TV_email.setText(user.getEmail());
                    if(user.getImageUrl() != null){
                        Glide.with(context).load(user.getImageUrl()).into(profile_CIV_image);
                    }
                }
            }
        });

        database.fetchUserData(database.getCurrentUser().getUid());

        profile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity) context, EditProfileActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
            }
        });

        profile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.logout();
                Intent intent = new Intent((Activity) context, Log_Activity.class);
                startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }


}