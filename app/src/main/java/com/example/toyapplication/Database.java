package com.example.toyapplication;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toyapplication.callback.AuthCallBack;
import com.example.toyapplication.callback.OrderCallBack;
import com.example.toyapplication.callback.ProductsCallBack;
import com.example.toyapplication.callback.UserCallBack;
import com.example.toyapplication.domain.Order;
import com.example.toyapplication.domain.Product;
import com.example.toyapplication.information.User;
import com.example.toyapplication.main.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Database {
    public static final String USERS_TABLE = "Users";
    public static final String Clothes_TABLE = "Clothes";
    public static final String ORDERS_TABLE = "Orders";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private AuthCallBack authCallBack;
    private UserCallBack userCallBack;
    private ProductsCallBack productsCallBack;
    private OrderCallBack orderCallBack;

    public Database(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }
    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }
    public void setProductsCallBack(ProductsCallBack productsCallBack){this.productsCallBack = productsCallBack;}
    public void setOrderCallBack(OrderCallBack orderCallBack){
        this.orderCallBack = orderCallBack;
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

    public void fetchClothesByCategoryName(String categoryName) {
        this.db.collection(Clothes_TABLE).whereEqualTo("category", categoryName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Product> products = new ArrayList<>();
                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()) {
                        Product product = snapshot.toObject(Product.class);
                        product.setId(snapshot.getId());
                        if(product.getImagePath() != null){
                            String imageUrl = downloadImageUrl(product.getImagePath());
                            product.setImageUrl(imageUrl);
                        }
                        products.add(product);
                    }
                }


                productsCallBack.onProductsFetchComplete(products);
            }
        });
    }
    public String downloadImageUrl(String imagePath){
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());
        return downloadImageTask.getResult().toString();
    }

    public boolean uploadImage(Uri imageUri, String imagePath){
        try{
            UploadTask uploadTask = storage.getReference(imagePath).putFile(imageUri);
            while (!uploadTask.isComplete() && !uploadTask.isCanceled());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
            return false;
        }

    }

    public void createOrder(Order order){
        this.db.collection(ORDERS_TABLE).document().set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                orderCallBack.onOrderAddComplete(task);
            }
        });
    }

    public void fetchUserData(String uid) {
        this.db.collection(USERS_TABLE).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                User user = value.toObject(User.class);
                if(user.getImagePath() != null){
                    String imageUrl = downloadImageUrl(user.getImagePath());
                    user.setImageUrl(imageUrl);
                }
                user.setId(value.getId());
                userCallBack.fetchUserDataComplete(user);
            }
        });
    }
}
