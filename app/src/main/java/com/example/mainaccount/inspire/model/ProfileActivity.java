package com.example.mainaccount.inspire.model;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class ProfileActivity extends BaseActivity {
    private EditText nameField, emailField;
    private Button updateBtn, profileEdit;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        profileEdit = (Button) findViewById(R.id.profile_edit);

        profileEdit.setPaintFlags(profileEdit.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        updateBtn.setPaintFlags(updateBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        nameField.setEnabled(false);
        emailField.setEnabled(false);
        updateBtn.setVisibility(View.INVISIBLE);

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
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameField.getText().toString();
                if(!newName.isEmpty()){
                    if(newName.equals(user.getDisplayName())){
                        Toast.makeText(ProfileActivity.this, "No changes applied",
                                Toast.LENGTH_LONG).show();
                    }else {
                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(newName)
                                .build();
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
                                        }
                                    }
                                });
                    }
                }else{
                    nameField.setError("Field can't be empty");
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

    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();

    }
}
