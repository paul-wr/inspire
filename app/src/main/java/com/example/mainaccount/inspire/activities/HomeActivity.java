package com.example.mainaccount.inspire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.BaseActivity;

/**
 *  Classname: HomeActivity.java
 *  Version 1
 *  Date: 26 jun 2017
 *  @author Paul Wrenn, x15020029
 */
public class HomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeUser();


        Button servicesBtn = (Button) findViewById(R.id.services_btn);
        Button favsLaunchBtn = (Button) findViewById(R.id.favs_btn);



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

        favsLaunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
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
