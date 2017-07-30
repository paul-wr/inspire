package com.example.mainaccount.inspire.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.Gem;
import com.example.mainaccount.inspire.model.SigninActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class GemBrowseActivity extends BaseActivity {
    String gemSuggestion;
    String userEmail;
    TextView gemTV;
    TextView gemSuggestTV;
    TextView authorTV;
    TextView gemGuideTV;
    EditText gemSuggestET;
    Button suggestGemBtn;
    Button sendBtn;
    Gem gem;
    int musicCount, healthCount, educationCount;
    ArrayList<Gem> musicList, eduList, healthList;
    private FirebaseDatabase database;
    DatabaseReference databaseReference;
    int infoCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gem_browse);

        gemSuggestET = (EditText) findViewById(R.id.gemSuggestionET);
        gemSuggestTV = (TextView) findViewById(R.id.gemSuggestionTV);
        gemTV = (TextView) findViewById(R.id.gemDisplayTV);
        authorTV = (TextView) findViewById(R.id.authorDisplayTV);
        gemGuideTV = (TextView) findViewById(R.id.gem_guidelines_tv);

        gemSuggestET.setVisibility(View.INVISIBLE);
        gemSuggestTV.setVisibility(View.INVISIBLE);
        gemGuideTV.setVisibility(View.INVISIBLE);

        gem = new Gem();
        gem.createList();


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Gem suggestions");
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        ImageButton browseMusicBtn = (ImageButton) findViewById(R.id.browseMusicBtn);
        ImageButton browseEducationBtn = (ImageButton) findViewById(R.id.browseEducationBtn);
        ImageButton browseHealthBtn = (ImageButton) findViewById(R.id.browseHealthBtn);
        ImageButton infoBtn = (ImageButton) findViewById(R.id.info_btn);
        suggestGemBtn = (Button) findViewById(R.id.suggestGemBtn);
        sendBtn = (Button) findViewById(R.id.gemSend);

        sendBtn.setVisibility(View.INVISIBLE);

        infoCount = 1;
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoCount %2 == 1){
                    gemGuideTV.setVisibility(View.VISIBLE);
                }else{
                    gemGuideTV.setVisibility(View.INVISIBLE);
                }
                infoCount++;
            }
        });

        browseMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicList = gem.getGemByCategpry("music");
                String gemString = "\""+musicList.get(musicCount).getGem()+"\"";
                String authorString = musicList.get(musicCount).getAuthor();
                gemTV.setText(gemString);
                authorTV.setText(" ~ "+authorString);
                musicCount++;
                if(musicCount == musicList.size()){
                    musicCount = 0;
                }
            }
        });

        browseEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eduList = gem.getGemByCategpry("education");
                String gemString = "\""+eduList.get(educationCount).getGem()+"\"";
                String authorString = eduList.get(educationCount).getAuthor();
                gemTV.setText(gemString);
                authorTV.setText(" ~ "+authorString);
                educationCount++;
                if(educationCount == eduList.size()){
                    educationCount = 0;
                }

            }
        });

        browseHealthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                healthList = gem.getGemByCategpry("health");
                String gemString = "\""+healthList.get(healthCount).getGem()+"\"";
                String authorString = healthList.get(healthCount).getAuthor();
                gemTV.setText(gemString);
                authorTV.setText(" ~ "+authorString);
                healthCount++;
                if(healthCount == healthList.size()){
                    healthCount = 0;
                }
            }
        });

        suggestGemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gemSuggestET.setVisibility(View.VISIBLE);
                gemSuggestTV.setVisibility(View.VISIBLE);
                suggestGemBtn.setVisibility(View.INVISIBLE);
                sendBtn.setVisibility(View.VISIBLE);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String dateString = date.toString();
                userEmail = getUserEmail();
                gemSuggestion = gemSuggestET.getText().toString();
                if(gemSuggestion.isEmpty()){
                    Toast.makeText(GemBrowseActivity.this, "Field cannot be empty!", Toast.LENGTH_LONG).show();
                }else {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        databaseReference.push().setValue(gemSuggestion + " | " + userEmail+" "+dateString);
                        gemSuggestET.setText("");
                        gemSuggestET.setVisibility(View.INVISIBLE);
                        gemSuggestTV.setText(R.string.gem_confirmation);
                        sendBtn.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(GemBrowseActivity.this, "You must be signed in to make a Gem suggestion!\n" +
                                "Redirecting to sign in...", Toast.LENGTH_LONG).show();
                        userIntent = getIntent();
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2500); // Launch login Activity after Toast message has run
                                    startActivity(new Intent(GemBrowseActivity.this, SigninActivity.class));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        thread.start();
                    }
                }
            }
        });

    }
    public String getUserEmail(){
        String email = "";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        return email;
    }
}
