package com.example.mainaccount.inspire.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.activities.FavoritesActivity;
import com.example.mainaccount.inspire.activities.HomeActivity;
import com.example.mainaccount.inspire.activities.NotificationSettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mainaccount.inspire.activities.NotificationSettingsActivity.isRedirected;

/**
 *  Classname: BaseActivity.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @reference Benit Kibabu
 *  @author Paul Wrenn, x15020029
 */


public class BaseActivity extends AppCompatActivity {
    public static Menu myMenu; // myMenu allows updating of Menu items visibility across classes
    public static boolean backPressed;
    TextView userTV;
    TextView emailTV;
    TextView headerText;
    public static Intent userIntent;
    private static boolean isSignedOut;
    private BottomNavigationView bottomNavigationView;


    private ProgressDialog progressDialog;

    // method to create bottomNavigationView
    public void createBottomDrawerMenu(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home_item) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else if (item.getItemId() == R.id.settings_item) {
                    startActivity(new Intent(getApplicationContext(), NotificationSettingsActivity.class));
                    finish();
                } else if (item.getItemId() == R.id.favorites_item) {
                    startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                    finish();
                }
                return true;
            }
        });
    }

    public void showProgressDialog(String message){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
        }

        progressDialog.show();
    }

    public void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // call the create method for bottomDrawer nav
        createBottomDrawerMenu();
        // back to home arrow in actionbar
        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);
        // aBar.setHomeAsUpIndicator(R.drawable.logo_pink);


        // set data member to dynamically set user status in menu
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

        switch(item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                return true;
            case R.id.sign_out:
                // sign out user using getInstance() method
                FirebaseAuth.getInstance().signOut();
                // if signed out display signin item and hide sign out item in actionBar menu
                myMenu.getItem(0).setVisible(true);
                myMenu.getItem(1).setVisible(false);
                // alert user
                Toast.makeText(this, "Successfully signed out!", Toast.LENGTH_SHORT).show();
                // reset email display in TextView if user is signed out
                isSignedOut = true;
                return true;
            case R.id.sign_in:
                startActivity(new Intent(BaseActivity.this, SigninActivity.class));
                finish();
                // set userIntent and isRedirected boolean for redirecting from signin activity
                userIntent = getIntent();
                isRedirected = true;
                return true;
            case R.id.refresh:
                finish();
                startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void welcomeUser(){
        // declare TextViews on Home screen for display of user data
        userTV = (TextView) findViewById(R.id.user_tv);
        emailTV = (TextView) findViewById(R.id.user_email_tv);
        // welcome registered users by name and display email address
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            userTV.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            emailTV.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        }
    }

    // method declaration for re-setting Home screen email text after sign out
    private void setWelcomeText(){
        if(isSignedOut) {
            emailTV.setText("");
        }
    }

    // method declaration for dynamically setting heading text per activity screen
    public void setHeadingText(String text){
        headerText = (TextView) findViewById(R.id.heading_text);
        headerText.setText(text);
    }

}
