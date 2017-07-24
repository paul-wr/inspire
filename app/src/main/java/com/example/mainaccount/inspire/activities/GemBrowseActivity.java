package com.example.mainaccount.inspire.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.BaseActivity;
import com.example.mainaccount.inspire.model.Gem;

import java.util.ArrayList;

public class GemBrowseActivity extends BaseActivity {
    TextView gemTV;
    Gem gem;
    int musicCount, healthCount, educationCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gem_browse);
        gem = new Gem();
        gem.createList();

        gemTV = (TextView) findViewById(R.id.gemDisplayTV);

        ImageButton browseMusicBtn = (ImageButton) findViewById(R.id.browseMusicBtn);
        ImageButton browseEducationBtn = (ImageButton) findViewById(R.id.browseEducationBtn);
        ImageButton browseHealthBtn = (ImageButton) findViewById(R.id.browseHealthBtn);

        browseMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Gem> musicList;
                musicList = gem.getGemByCategpry("music");
                String s = musicList.get(musicCount).getGem();
                gemTV.setText(s);
                musicCount++;
                if(musicCount == musicList.size()){
                    musicCount = 0;
                }
            }
        });

        browseEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Gem> eduList;
                eduList = gem.getGemByCategpry("education");
                String s = eduList.get(educationCount).getGem();
                gemTV.setText(s);
                educationCount++;
                if(educationCount == eduList.size()){
                    educationCount = 0;
                }

            }
        });

        browseHealthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Gem> healthList;
                healthList = gem.getGemByCategpry("health");
                String s = healthList.get(healthCount).getGem();
                gemTV.setText(s);
                healthCount++;
                if(healthCount == healthList.size()){
                    healthCount = 0;
                }
            }
        });
    }
}
