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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 *  Classname: ProfileActivity.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @reference Benit Kibabu
 *  @author Paul Wrenn, x15020029
 */



public class ProfileActivity extends BaseActivity {
    private EditText nameField, emailField;
    private Button updateBtn, profileEdit, deleteUserBtn, btnResetPassword;
    ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    static String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setHeadingText("User Profile");

        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);

        updateBtn = (Button) findViewById(R.id.updateButton);
        profileEdit = (Button) findViewById(R.id.profile_edit);
        deleteUserBtn = (Button) findViewById(R.id.delete_profile);
        btnResetPassword = (Button) findViewById(R.id.password_reset);
        progressBar = (ProgressBar) findViewById(R.id.resetProgressBar);


        nameField.setEnabled(false);
        emailField.setEnabled(false);
        updateBtn.setVisibility(View.INVISIBLE);
        deleteUserBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        btnResetPassword.setVisibility(View.INVISIBLE);


        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(ProfileActivity.this, SigninActivity.class));
                    finish();
                }
            }
        };

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {

            String name = user.getDisplayName();
            String email = user.getEmail();

            setTitle(name);

            emailField.setText(email);
            nameField.setText(name);
        }else{
            startActivity(new Intent(ProfileActivity.this, SigninActivity.class));
            finish();
        }

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameField.setEnabled(true);
                updateBtn.setVisibility(View.VISIBLE);
                deleteUserBtn.setVisibility(View.VISIBLE);
                btnResetPassword.setVisibility(View.VISIBLE);

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName = nameField.getText().toString();
                if(!newName.isEmpty()){
                    if(newName.equals(user.getDisplayName())){
                        Toast.makeText(ProfileActivity.this, "No changes applied",
                                Toast.LENGTH_LONG).show();
                    }else {
                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(newName)
                                .build();
                        progressBar.setVisibility(View.VISIBLE);
                        user.updateProfile(changeRequest)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this,
                                                    "Profile name updated",
                                                    Toast.LENGTH_LONG).show();
                                            nameField.setEnabled(false);
                                            updateBtn.setVisibility(View.INVISIBLE);
                                        } else {
                                            Toast.makeText(ProfileActivity.this,
                                                    "Profile name updated",
                                                    Toast.LENGTH_LONG).show();
                                            // refresh user name data with screen relaunch
                                            finish();
                                            startActivity(getIntent());
                                        }
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                        // reset profile name title after edit
                        setTitle(newName);
                    }
                }else{
                    nameField.setError("Field can't be empty");
                }
            }
        });

        // delete user account
        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Your account has been deleted!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
                String email = emailField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your email address ", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
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

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();

    }
}
