package com.example.mainaccount.inspire;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mainaccount on 12/07/2017.
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



    }

}