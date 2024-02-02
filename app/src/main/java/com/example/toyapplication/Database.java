package com.example.toyapplication;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.toyapplication.callback.AuthCallBack;
import com.example.toyapplication.callback.UserCallBack;
import com.example.toyapplication.information.User;
import com.example.toyapplication.main.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    public static final String USERS_TABLE = "Users";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private AuthCallBack authCallBack;
    private UserCallBack userCallBack;

    public Database(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }
    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }
    public FirebaseUser getCurrentUser(){
        return this.mAuth.getCurrentUser();
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authCallBack.onLoginComplete(task);
            }
        });
    }

    public void logout(){
        mAuth.signOut();
    }
    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authCallBack.onCreateAccountComplete(task);
                    }
                });
    }

    public void saveUserData(User user){
        this.db.collection(USERS_TABLE).document(user.getId()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userCallBack.onUserDataSaveComplete(task);
                    }
                });
    }
}
