package com.example.mainaccount.inspire.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *  Classname: ProfileActivity.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @reference Benit Kibabu
 *  @author Paul Wrenn, x15020029
 */

public class SigninActivity extends BaseActivity {
    public static boolean loginV, logoutV;

    EditText emailField, passwordField;
    Button loginBtn, registerBtn, btnResetPassword;
    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        setTitle("Sign in");
        setHeadingText("Sign in");

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        btnResetPassword = (Button) findViewById(R.id.password_reset);
        progressBar = (ProgressBar) findViewById(R.id.resetProgressBar);

        progressBar.setVisibility(View.INVISIBLE);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // redirect user to screen they were orignally on
                    startActivity(userIntent);
                    finish();
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

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
                String email = emailField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SigninActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SigninActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
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
