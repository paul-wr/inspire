package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.LoginActivity;
import com.example.mainaccount.inspire.model.RegisterActivity;


public class HomeActivity extends BaseActivity {
    Button regsiterBtn, loginBtn, settingsBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        regsiterBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        settingsBtn = (Button) findViewById(R.id.settingsBtn);



        regsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, NotificationSettingsActivity.class));
            }
        });

    }


}
