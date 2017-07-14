package com.example.mainaccount.inspire.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.adapters.FavoriteListAdapter;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.ArrayList;
import java.util.Map;

/**
 *  Classname: FavoritesActivity.java
 *  Version 1
 *  Date: 12 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class FavoritesActivity  extends BaseActivity {
    private ListView lvFavorites;
    private FavoriteListAdapter adapter;
    public static ArrayList<String> mGemList;
    public static final String MyFavPREFERENCES = "MyFavPrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyFavPREFERENCES, MODE_PRIVATE);

        lvFavorites = (ListView) findViewById(R.id.favorite_list);
        mGemList = new ArrayList<>();



        Map<String, ?> keys = sharedPreferences.getAll();
        for(Map.Entry <String, ?> entry : keys.entrySet()){
            mGemList.add((String) entry.getValue());
        }

        adapter = new FavoriteListAdapter(getApplicationContext(), mGemList);
        lvFavorites.setAdapter(adapter);


        lvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked! Tag = "+position, Toast.LENGTH_LONG).show();
            }
        });

    }

}