package com.example.mainaccount.inspire.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mainaccount.inspire.R;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        ArrayList<String> gemFavorites = new ArrayList<>();

        gemFavorites.add("Favorite 1");
        gemFavorites.add("Favorite 2");
        gemFavorites.add("Favorite 3");
        gemFavorites.add("Favorite 4");
        gemFavorites.add("Favorite 5");
        gemFavorites.add("Favorite 6");
        gemFavorites.add("Favorite 7");
        gemFavorites.add("Favorite 8");
        gemFavorites.add("Favorite 9");
        gemFavorites.add("Favorite 10");

        ListAdapter theAdapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gemFavorites);

        ListView theListView = (ListView) findViewById(R.id.theListView);

    }

}
