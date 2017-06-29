package com.example.mainaccount.inspire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mainaccount.inspire.model.LoginActivity;
import com.example.mainaccount.inspire.model.ProfileActivity;
import com.example.mainaccount.inspire.model.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button regsiterBtn, loginBtn;
    public static boolean isSignedIn, isSignedOut;
    public static Menu myMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        regsiterBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);



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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_activity_menu, menu);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }else{
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.profile){
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            return true;
        }
        else if(id == R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
            // if signed out display signin item and hide sign out item
            myMenu.getItem(0).setVisible(true);
            myMenu.getItem(1).setVisible(false);
            Toast.makeText(HomeActivity.this, "Successfully signed out!", Toast.LENGTH_SHORT).show();

            return true;
        }
        else if(id == R.id.sign_in){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
