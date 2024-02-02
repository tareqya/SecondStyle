package com.example.toyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.toyapplication.information.FirebaseId;
import com.example.toyapplication.main.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Sign_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button accountBtn1;
    
    private CircularProgressIndicator signup_PB_loading;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        accountBtn1 = findViewById(R.id.accountBtn1);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
        mAuth = FirebaseAuth.getInstance();


        accountBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signin();




            }

            private void signin() {
                String firstname = signup_TF_firstName.getEditText().getText().toString();
                String lastname = signup_TF_lastName.getEditText().getText().toString();
                String email = signup_TF_email.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                String confirmpassword = signup_TF_confirmPassword.getEditText().getText().toString();

                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()|| password.isEmpty()|| confirmpassword.isEmpty()){
                    Toast.makeText(Sign_Activity.this, "please fill all fields ", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmpassword)){
                    Toast.makeText(Sign_Activity.this, "passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else{

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Sign_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User account created successfully
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Update user profile with first and last name
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(firstname + " " + lastname)
                                            .build();

                                    if (user != null) {
                                        user.updateProfile(profileUpdates);
                                    }

                                    Toast.makeText(Sign_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    // Navigate to the next activity or perform any other desired action
                                } else {
                                    // If registration fails, display a message to the user.
                                    Toast.makeText(Sign_Activity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
        }



    });

    }
}