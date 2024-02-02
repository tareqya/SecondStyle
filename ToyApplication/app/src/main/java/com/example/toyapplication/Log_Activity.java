package com.example.toyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.toyapplication.main.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log_Activity extends AppCompatActivity {
    private Button loginBtn;
    private Button signBtn;
    ;
    private TextInputLayout login_TF_email,login_TF_password;
    private FirebaseAuth  fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        fAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginBtn1);
        signBtn = findViewById(R.id.signBtn1);
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password= findViewById(R.id.login_TF_password);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_Activity.this, Sign_Activity.class);
                startActivity(intent);
            }
        });


    }
    public void login() {
            fAuth.signInWithEmailAndPassword(login_TF_email.getEditText().getText().toString(),login_TF_password.getEditText().getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=fAuth.getCurrentUser();
                        Toast.makeText(Log_Activity.this,"Welcome",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Log_Activity.this,ShopActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(Log_Activity.this,"failed to log in",Toast.LENGTH_SHORT).show();
                    }
                }
            });



    }

}









