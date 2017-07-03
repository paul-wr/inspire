package com.example.mainaccount.inspire.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainaccount.inspire.NotificationSettingsActivity;
import com.example.mainaccount.inspire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.mainaccount.inspire.NotificationSettingsActivity.isRedirected;


public class SigninActivity extends BaseActivity {
    public static boolean loginV, logoutV;

    EditText emailField, passwordField;
    Button loginBtn, registerBtn;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setTitle("Sign in");

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    if(isRedirected){
                        startActivity(new Intent(SigninActivity.this, NotificationSettingsActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SigninActivity.this, ProfileActivity.class));
                        finish();
                    }
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTextField(emailField) && validateEditTextField(passwordField)) {
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();

                    signIn(email, password);
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }

    private void signIn(String email, String password){
        showProgressDialog("Signing in...");
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if(!task.isSuccessful()){
                            Toast.makeText(SigninActivity.this, "Failed signin!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // if login is successful display logout item and hide login item
                        myMenu.getItem(0).setVisible(false);
                        myMenu.getItem(1).setVisible(true);
                        loginV = false;
                        logoutV = true;
                    }
                });
    }

    public boolean validateEditTextField(EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Field can't be empty!");
            return false;
        }else
            return true;
    }

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();

    }

    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
