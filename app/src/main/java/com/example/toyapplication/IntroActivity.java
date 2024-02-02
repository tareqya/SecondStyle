package com.example.toyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.toyapplication.main.ShopActivity;

public class IntroActivity extends AppCompatActivity {
private Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        startBtn=findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, Log_Activity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database database = new Database();
        if(database.getCurrentUser() != null){
            Intent intent = new Intent(IntroActivity.this, ShopActivity.class);
            startActivity(intent);
            finish();
        }
    }
}