package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mainaccount.inspire.model.LoginActivity;
import com.example.mainaccount.inspire.model.SignUpActivity;

public class HomeActivity extends AppCompatActivity {
    Button regsiterBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        regsiterBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        regsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });


    }
}
