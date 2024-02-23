package com.example.toyapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.toyapplication.callback.AuthCallBack;
import com.example.toyapplication.callback.UserCallBack;
import com.example.toyapplication.information.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class Sign_Activity extends AppCompatActivity {

    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button accountBtn1;
    
    private CircularProgressIndicator signup_PB_loading;
    private Database database;

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

        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserDataSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Sign_Activity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(Sign_Activity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void fetchUserDataComplete(User user) {

            }
        });

        database.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onCreateAccountComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String firstname = signup_TF_firstName.getEditText().getText().toString();
                    String lastname = signup_TF_lastName.getEditText().getText().toString();
                    String email = signup_TF_email.getEditText().getText().toString();

                    User user = new User()
                            .setEmail(email)
                            .setFirstName(firstname)
                            .setLastName(lastname);
                    String uid = database.getCurrentUser().getUid();
                    user.setId(uid);
                    database.saveUserData(user);
                } else {
                    Toast.makeText(Sign_Activity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        accountBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }

            private void signup() {
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
                    database.createAccount(email, password);
                }
            }
        });

    }
}