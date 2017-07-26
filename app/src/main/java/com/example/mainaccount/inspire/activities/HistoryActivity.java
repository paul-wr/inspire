package com.example.mainaccount.inspire.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.adapters.HistoryListAdapter;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.ArrayList;
import java.util.Map;

public class HistoryActivity extends BaseActivity {
    private ListView lvHistory;
    private HistoryListAdapter adapter;
    public static ArrayList<String> historyList;
    public static final String MyHistoryPREFERENCES = "MyHistoryPrefs";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyHistoryPREFERENCES, MODE_PRIVATE);

        lvHistory = (ListView) findViewById(R.id.history_list);
        historyList = new ArrayList<>();

        Map<String, ?> keys = sharedPreferences.getAll();
        for(Map.Entry <String, ?> entry : keys.entrySet()){
            historyList.add((String) entry.getValue());
        }

        adapter = new HistoryListAdapter(getApplicationContext(), historyList);
        lvHistory.setAdapter(adapter);
        adapter.setIntent(getIntent());



    }
}
