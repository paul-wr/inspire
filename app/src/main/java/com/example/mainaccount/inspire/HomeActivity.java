package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mainaccount.inspire.model.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NotificationSettingsActivity.class));
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
