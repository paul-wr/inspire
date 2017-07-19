package com.example.mainaccount.inspire.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mainaccount.inspire.HelpfulService;
import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.adapters.ServiceListAdapter;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends BaseActivity {
    private ListView lvService;
    private ServiceListAdapter adapter;
    private List<HelpfulService> mServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        lvService = (ListView) findViewById(R.id.service_list);
        mServiceList = new ArrayList<>();

        mServiceList.add(new HelpfulService(1, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "0838579446","info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(2, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));
        mServiceList.add(new HelpfulService(3, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "0838579446", "info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(4, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));
        mServiceList.add(new HelpfulService(5, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "0838579446", "info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(6, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));
        mServiceList.add(new HelpfulService(7, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "0838579446", "info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(8, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));
        mServiceList.add(new HelpfulService(9, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "0838579446", "info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(10, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));

        adapter = new ServiceListAdapter(getApplicationContext(), mServiceList);
        lvService.setAdapter(adapter);

        lvService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Clicked! Tag = "+view.getTag(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
