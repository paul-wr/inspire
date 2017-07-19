package com.example.mainaccount.inspire.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *  Classname: RegisterActivity.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @reference Benit Kibabu
 *  @author Paul Wrenn, x15020029
 */


public class RegisterActivity extends BaseActivity {
    private EditText  emailField, passwordField;
    private Button loginBtn, registerBtn;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");
        setHeadingText("Register");

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        registerBtn = (Button) findViewById(R.id.register_btn);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                    finish();
                }
            }
        };

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEditTextField(emailField) && validateEditTextField(passwordField)) {
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();
                    createNewUser(email, password);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SigninActivity.class));
                finish();
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

    void createNewUser(String email, String password){
        showProgressDialog("Signing Up....");
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Failed to register",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
