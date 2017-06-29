package com.example.mainaccount.inspire.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mainaccount.inspire.MainActivity.myMenu;


public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

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
            startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
            return true;
        }
        else if(id == R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
            // if signed out display signin item and hide sign out item
            myMenu.getItem(0).setVisible(true);
            myMenu.getItem(1).setVisible(false);
            Toast.makeText(BaseActivity.this, "Successfully signed out!", Toast.LENGTH_SHORT).show();

            return true;
        }
        else if(id == R.id.sign_in){
            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
