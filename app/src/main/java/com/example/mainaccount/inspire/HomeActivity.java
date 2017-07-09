package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mainaccount.inspire.model.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button servicesBtn = (Button) findViewById(R.id.services_btn);


        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NotificationSettingsActivity.class));
            }
        });

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ServicesActivity.class);
                startActivity(intent);
            }
        });

    }


    // onRestart() method call refreshes user login data in the menu
    @Override
    public void onRestart() {
        super.onRestart();
        if(backPressed == true){
            finish();
            startActivity(getIntent());
            backPressed = false;
        }

    }
}
